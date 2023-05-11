package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}