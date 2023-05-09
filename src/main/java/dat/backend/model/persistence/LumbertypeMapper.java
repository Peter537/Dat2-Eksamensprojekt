package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class LumbertypeMapper {
    public static Optional<ArrayList<LumberType>> getLumbertypeByType(String lumberType, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumbertype WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                    
                    ArrayList<LumberType> lumbertypelist = new ArrayList<LumberType>();
                
                    statement.setString(1, lumberType);

                    ResultSet resultSet = statement.executeQuery();

                    if (!resultSet.next()) {
                        throw new DatabaseException("Could not get lumber by id");
                    }
                    
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        float thickness = resultSet.getFloat("thickness");
                        float width = resultSet.getFloat("width");
                        float meterprice = resultSet.getFloat("meter_price");
                        
                        LumberType lumberTypeInstance = new LumberType(id, width, thickness, meterprice, lumberType);
                        lumbertypelist.add(lumberTypeInstance);
                    }
                    
                    return Optional.of(lumbertypelist);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }

    public static Optional<LumberType> getLumbertypeById(int id, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumbertype WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get lumber by id");
                }

                float thickness = resultSet.getFloat("thickness");
                float width = resultSet.getFloat("width");
                float meterprice = resultSet.getFloat("meter_price");
                String type = resultSet.getString("type");

                LumberType lumberType = new LumberType(id, width, thickness, meterprice, type);

                return Optional.of(lumberType);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }
}
