package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.services.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class LumberMapper {
    public static Optional<Lumber> createLumber(int id, int length, int type, int amount, ConnectionPool connectionPool) throws DatabaseException {


        String query = "INSERT INTO lumber (length, type, amount, id) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(length));
                statement.setString(2, String.valueOf(type));
                statement.setString(3, String.valueOf(amount));
                statement.setString(4, String.valueOf(id));
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create lumber");
                }

                return Optional.empty(); // TODO: return lumber
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create lumber");
        }
    }

    public static Optional<Lumber> getLumberById(int id, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return Optional.empty(); // TODO: return lumber
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }

    public static Optional<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) {
        return Optional.empty(); // TODO: return lumber
    }

    public static Optional<Lumber> getLumberByLength(int length, ConnectionPool connectionPool) {
        return Optional.empty(); // TODO: return lumber
    }


    /*
     * TODO: Implement this class
     */
}