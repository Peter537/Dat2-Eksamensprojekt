package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LumberMapper {

    static Lumber createLumber(int length, int type, int amount, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateLength(length);
        String query = "INSERT INTO lumber (length, type, amount) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, length);
                statement.setInt(2, type);
                statement.setInt(3, amount);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create lumber");
                }

                ResultSet rs = statement.getGeneratedKeys();
                if (!rs.next()) {
                    throw new DatabaseException("Could not create lumber");
                }

                LumberType lumberType = LumberTypeFacade.getLumberTypeById(type, connectionPool);
                int id = rs.getInt(1);
                return new Lumber(id, length, lumberType, amount);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Could not create lumber");
        }
    }

    static List<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        List<Lumber> lumberList = new ArrayList<>();
        String query = "SELECT * FROM lumber WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, lumberType.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int length = resultSet.getInt("length");
                    int amount = resultSet.getInt("amount");
                    lumberList.add(new Lumber(id, length, lumberType, amount));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by type");
        }

        return lumberList;
    }

    static void deleteLumber(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "DELETE FROM lumber WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new NotFoundException("Could not delete lumber");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not delete lumber");
        }
    }

    static void updateLumber(int id, int poleLength, int lumberTypeId, int amount, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateLength(poleLength);
        Validation.validateAmount(amount);
        String query = "UPDATE lumber SET length = ?, type = ?, amount = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, poleLength);
                statement.setInt(2, lumberTypeId);
                statement.setInt(3, amount);
                statement.setInt(4, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update lumber");
        }
    }
}