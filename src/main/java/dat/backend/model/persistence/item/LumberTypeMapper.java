package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LumberTypeMapper {

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

    static LumberType createLumberType(float poleThickness, float poleWidth, float poleMeterPrice, String pole, ConnectionPool connectionPool) throws DatabaseException {
        try {
            return getLumberType(poleThickness, poleWidth, poleMeterPrice, connectionPool);
        } catch (DatabaseException | NotFoundException e) {
            // Do nothing
        }

        String query = "INSERT INTO lumbertype (thickness, width, meter_price, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, poleThickness);
                statement.setFloat(2, poleWidth);
                statement.setFloat(3, poleMeterPrice);
                statement.setString(4, pole);
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
}