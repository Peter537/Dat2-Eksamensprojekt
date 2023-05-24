package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class RoofMapper {

    /**
     * This method will retrieve a Roof by an id
     *
     * @param id             The id to search for
     * @param connectionPool The connection pool to use
     * @return The Roof object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static Roof getRoofById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM roof WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotFoundException("Could not get roof by id");
                }

                float squareMeterPrice = resultSet.getFloat("squaremeter_price");
                String type = resultSet.getString("type");
                return new Roof(id, squareMeterPrice, type);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get roof by id");
        }
    }

    /**
     * This method will create a new Roof by the arguments given
     *
     * @param squareMeterPrice The price per square meter
     * @param type             The type of roof
     * @param connectionPool   The connection pool to use
     * @return The Roof object
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static Roof createRoof(float squareMeterPrice, String type, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO roof (squaremeter_price, type) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, squareMeterPrice);
                statement.setString(2, type); // TODO: Check if type exists. Figure out if we want to create a new type if it doesn't exist or just an error.
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (!rs.next()) {
                    throw new DatabaseException("Could not create roof");
                }

                int id = rs.getInt(1);
                return new Roof(id, squareMeterPrice, type);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create roof");
        }
    }

    /**
     * This method will retrieve all Roof objects
     *
     * @param connectionPool The connection pool to use
     * @return A list of Roof objects
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<Roof> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException {
        List<Roof> rooflist = new ArrayList<>();
        String query = "SELECT * FROM roof";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    float squareMeterPrice = resultSet.getFloat("squaremeter_price");
                    String type = resultSet.getString("type");
                    rooflist.add(new Roof(id, squareMeterPrice, type));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get all roof");
        }

        return rooflist;
    }

    /**
     * This method will delete a roof by an id
     *
     * @param id             The id of the roof to delete
     * @param connectionPool The connection pool to use
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static void deleteRoof(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "DELETE FROM roof WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new NotFoundException("Could not delete roof");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not delete roof");
        }
    }

    /**
     * This method will update a roof by an id
     *
     * @param id               The id of the roof to update
     * @param squareMeterPrice The new price per square meter
     * @param roofType         The new type of roof
     * @param connectionPool   The connection pool to use
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static void updateRoof(int id, float squareMeterPrice, String roofType, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "UPDATE roof SET squaremeter_price = ?, type = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, squareMeterPrice);
                statement.setString(2, roofType);
                statement.setInt(3, id);
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new NotFoundException("Could not update roof");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update roof");
        }
    }
}