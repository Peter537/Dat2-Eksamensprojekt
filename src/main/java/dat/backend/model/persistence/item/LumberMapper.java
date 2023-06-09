package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LumberMapper {

    /**
     * This method will create a lumber in the database
     *
     * @param length         Length of lumber
     * @param type           Type of lumber
     * @param amount         Amount of lumber
     * @param connectionPool Connection pool to use
     * @return Lumber
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static Lumber createLumber(int length, int type, int amount, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will retrieve all Lumber by a LumberType
     *
     * @param lumberType     Lumber type
     * @param connectionPool Connection pool to use
     * @return List of lumber
     * @throws DatabaseException if an error occurs while communicating with the database
     */
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

    /**
     * This method will delete a lumber
     *
     * @param id             Lumber id
     * @param connectionPool Connection pool to use
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the lumber does not exist
     */
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

    /**
     * This method will update a Lumber by id
     *
     * @param id             Lumber id
     * @param poleLength     Pole length
     * @param lumberTypeId   Lumber type id
     * @param amount         Amount of lumber
     * @param connectionPool Connection pool to use
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateLumber(int id, int poleLength, int lumberTypeId, int amount, ConnectionPool connectionPool) throws DatabaseException {
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