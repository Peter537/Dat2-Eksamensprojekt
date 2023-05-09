package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LumbertypeMapper {
    public static Optional<LumberType> getLumbertypeByType(String lumberType, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumbertype WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setString(1, lumberType);

                    ResultSet resultSet = statement.executeQuery();

                    if (!resultSet.next()) {
                        throw new DatabaseException("Could not get lumber by id");
                    }

                    int id = resultSet.getInt("id");
                    float thickness = resultSet.getFloat("thickness");
                    float width = resultSet.getFloat("width");
                    float price = resultSet.getFloat("price");

                    return Optional.of(new LumberType(id, thickness, width, price, lumberType));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }
}
