package dat.backend.model.persistence.item;

import dat.backend.model.entities.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoofMapper {
    public static Optional<Roof> getRoofById(int id, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM roof WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setInt(1, id);

                    ResultSet resultSet = statement.executeQuery();

                    if (!resultSet.next()) {
                        throw new DatabaseException("Could not get roof by id");
                    }

                    float squaremeterprice = resultSet.getFloat("squaremeter_price");
                    String type = resultSet.getString("type");

                    Roof roof = new Roof(id, squaremeterprice, type);

                    return Optional.of(roof);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Could not get roof by id");
        }
    }

    public static Optional<Roof> createRoof(Roof roof, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO roof (squaremeter_price, type) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, roof.getSquareMeterPrice());
                statement.setString(2, roof.getType());
                statement.executeUpdate();
                return Optional.of(roof);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create roof");
        }
    }
}
