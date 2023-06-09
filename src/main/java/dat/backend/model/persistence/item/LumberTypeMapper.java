package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LumberTypeMapper {

    /**
     * Retrieve a LumberType by id
     *
     * @param id             The id to search for
     * @param connectionPool Connection pool to use
     * @return lumber type
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static LumberType getLumberTypeById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM lumbertype WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotFoundException("Could not get lumber by id");
                }

                float thickness = resultSet.getFloat("thickness");
                float width = resultSet.getFloat("width");
                float meterPrice = resultSet.getFloat("meter_price");
                String type = resultSet.getString("type");
                return new LumberType(id, width, thickness, meterPrice, type);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }

    /**
     * Retrieve all LumberTypes by a type, fx if a type is different thickness * width, it is still the same type
     *
     * @param lumberType     The type to search for
     * @param connectionPool Connection pool to use
     * @return List of lumber types
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<LumberType> getLumberTypeByType(String lumberType, ConnectionPool connectionPool) throws DatabaseException {
        List<LumberType> lumberTypelist = new ArrayList<>();
        String query = "SELECT * FROM lumbertype WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, lumberType);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    float thickness = resultSet.getFloat("thickness");
                    float width = resultSet.getFloat("width");
                    float meterPrice = resultSet.getFloat("meter_price");
                    lumberTypelist.add(new LumberType(id, width, thickness, meterPrice, lumberType));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }

        return lumberTypelist;
    }

    /**
     * This method will create a new lumber type in the database
     *
     * @param poleThickness  The thickness of the lumber type
     * @param poleWidth      The width of the lumber type
     * @param poleMeterPrice The meter price of the lumber type
     * @param type           The type of the lumber type
     * @param connectionPool Connection pool to use
     * @return The lumber type
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static LumberType createLumberType(float poleThickness, float poleWidth, float poleMeterPrice, String type, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO lumbertype (thickness, width, meter_price, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, poleThickness);
                statement.setFloat(2, poleWidth);
                statement.setFloat(3, poleMeterPrice);
                statement.setString(4, type);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create lumber type");
                }

                ResultSet rs = statement.getGeneratedKeys();
                if (!rs.next()) {
                    throw new DatabaseException("Could not create lumber type");
                }

                int id = rs.getInt(1);
                return getLumberTypeById(id, connectionPool);
            }
        } catch (SQLException | DatabaseException | NotFoundException e) {
            throw new DatabaseException(e, "Could not create lumber type");
        }
    }

    /**
     * This method will get lumber type by thickness, width and meter price
     *
     * @param poleThickness  The thickness of the lumber type
     * @param poleWidth      The width of the lumber type
     * @param poleMeterPrice The meter price of the lumber type
     * @param connectionPool Connection pool to use
     * @return The lumber type
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the lumber type does not exist
     */
    static LumberType getLumberType(float poleThickness, float poleWidth, float poleMeterPrice, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM lumbertype WHERE thickness = ? AND width = ? AND meter_price = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, poleThickness);
                statement.setFloat(2, poleWidth);
                statement.setFloat(3, poleMeterPrice);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotFoundException("Could not get lumber by id");
                }

                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                return new LumberType(id, poleWidth, poleThickness, poleMeterPrice, type);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }
}