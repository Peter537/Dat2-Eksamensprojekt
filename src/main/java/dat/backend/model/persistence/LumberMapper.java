package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

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

                return getLumberById(id, connectionPool);
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

                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get lumber by id");
                }

                int length = resultSet.getInt("length");

                Optional<LumberType> lumberTypeOptional = LumbertypeFacade.getLumbertypeByType(resultSet.getString("type"), connectionPool);
                LumberType lumberType;
                if (lumberTypeOptional.isPresent()) {
                    lumberType = lumberTypeOptional.get();
                }
                else {
                    throw new DatabaseException("Could not get lumber by id");
                }

                int amount = resultSet.getInt("amount");

                return Optional.of(new Lumber(id, length, lumberType, amount));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }

    public static Optional<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE type = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement(query))
            {
                statement.setString(1, lumberType.getType());

                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get lumber by type");
                }

                int lumberid = resultSet.getInt("id");
                int length = resultSet.getInt("length");
                int amount = resultSet.getInt("amount");

                return Optional.of(new Lumber(lumberid, length, lumberType, amount));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by type");
        }
    }

    public static Optional<Lumber> getLumberByLength(int length, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE length = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement(query))
            {
                statement.setInt(1, length);

                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get lumber by length");
                }

                int lumberid = resultSet.getInt("id");
                int amount = resultSet.getInt("amount");

                Optional<LumberType> lumberTypeOptional = LumbertypeFacade.getLumbertypeByType(resultSet.getString("type"), connectionPool);
                LumberType lumberType;
                if (lumberTypeOptional.isPresent()) {
                    lumberType = lumberTypeOptional.get();
                }
                else {
                    throw new DatabaseException("Could not get lumber by length");
                }

                return Optional.of(new Lumber(lumberid, length, lumberType, amount));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by length");
        }
    }


    /*
     * TODO: Implement this class
     */
}