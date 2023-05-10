package dat.backend.model.persistence.item;

import dat.backend.model.entities.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

class RoofMapper {
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
        return createRoof(roof.getSquareMeterPrice(), roof.getType(), connectionPool);
    }

    public static Optional<Roof> createRoof(float squaremeterprice, String type, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO roof (squaremeter_price, type) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, squaremeterprice);
                statement.setString(2, type); // TODO: Check if type exists. Figure out if we want to create a new type if it doesn't exist or just an error.
                statement.executeUpdate();

                int id;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new DatabaseException("Could not create roof");
                }

                Roof roof = new Roof(id, squaremeterprice, type);
                return Optional.of(roof);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create roof");
        }
    }

    public static Optional<ArrayList<Roof>> getRoofByType(String type, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM roof WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                    
                    statement.setString(1, type);
    
                    ArrayList<Roof> rooflist = new ArrayList<>();
    
                    ResultSet resultSet = statement.executeQuery();
    
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        float squaremeterprice = resultSet.getFloat("squaremeter_price");
    
                        Roof roof = new Roof(id, squaremeterprice, type);
                        rooflist.add(roof);
                    }

                    if (rooflist.size() == 0) {
                        throw new DatabaseException("Could not get roof by type");
                    }
    
                    return Optional.of(rooflist);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Could not get roof by type");
        }
    }

    public static Optional<ArrayList<Roof>> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException {

            String query = "SELECT * FROM roof";
            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {

                        ArrayList<Roof> rooflist = new ArrayList<>();

                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            float squaremeterprice = resultSet.getFloat("squaremeter_price");
                            String type = resultSet.getString("type");

                            Roof roof = new Roof(id, squaremeterprice, type);
                            rooflist.add(roof);
                        }

                        return Optional.of(rooflist);
                }
            }
            catch (SQLException e) {
                throw new DatabaseException(e, "Could not get all roof");
            }
    }
}
