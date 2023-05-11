package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

class LumberMapper {
    static Optional<Lumber> createLumber(float length, int type, int amount, ConnectionPool connectionPool) throws DatabaseException {

        String query = "INSERT INTO lumber (length, type, amount) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, RETURN_GENERATED_KEYS)) {
                statement.setFloat(1, length);
                statement.setInt(2, type);
                statement.setInt(3, amount);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Could not create lumber");
                }

                int id;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new DatabaseException("Could not create lumber");
                }

                return getLumberById(id, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not create lumber");
        }
    }

    static Optional<Lumber> createLumber(Lumber lumber, ConnectionPool connectionPool) throws DatabaseException {
        return createLumber(lumber.getLength(), lumber.getLumberType().getId(), lumber.getAmount(), connectionPool);
    }

    static Optional<ArrayList<Lumber>> getAllLumber(ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                ArrayList<Lumber> lumberlist = new ArrayList<Lumber>();

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int length = resultSet.getInt("length");
                    int amount = resultSet.getInt("amount");

                    Optional<LumberType> lumberTypeOptional = LumbertypeFacade.getLumbertypeById(resultSet.getInt("type"), connectionPool);
                    LumberType lumberType;
                    if (lumberTypeOptional.isPresent()) {
                        lumberType = lumberTypeOptional.get();
                    } else {
                        throw new DatabaseException("Could not get lumber");
                    }

                    int price = calcPrice(length, lumberType.getMeterPrice());

                    Lumber lumber = new Lumber(id, length, lumberType, price, amount);
                    lumberlist.add(lumber);
                }

                return Optional.of(lumberlist);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber");
        }
    }

    static Optional<Lumber> getLumberById(int id, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get lumber by id");
                }

                int length = resultSet.getInt("length");

                Optional<LumberType> lumberTypeOptional = LumbertypeFacade.getLumbertypeById(resultSet.getInt("type"), connectionPool);
                LumberType lumberType;
                if (lumberTypeOptional.isPresent()) {
                    lumberType = lumberTypeOptional.get();
                } else {
                    throw new DatabaseException("Could not get lumber by id");
                }

                int amount = resultSet.getInt("amount");
                int price = calcPrice(length, lumberType.getMeterPrice());

                return Optional.of(new Lumber(id, length, lumberType, price, amount));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by id");
        }
    }

    static Optional<ArrayList<Lumber>> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE type = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ArrayList<Lumber> lumberlist = new ArrayList<>();
                statement.setInt(1, lumberType.getId());

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int lumberid = resultSet.getInt("id");
                    int length = resultSet.getInt("length");
                    int amount = resultSet.getInt("amount");
                    int price = calcPrice(length, lumberType.getMeterPrice());

                    Lumber lumber = new Lumber(lumberid, length, lumberType, price, amount);
                    lumberlist.add(lumber);
                }

                if (lumberlist.size() == 0) {
                    throw new DatabaseException("Could not get lumber by type");
                }

                return Optional.of(lumberlist);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by type");
        }
    }

    static Optional<ArrayList<Lumber>> getLumberByLength(int length, ConnectionPool connectionPool) throws DatabaseException {

        String query = "SELECT * FROM lumber WHERE length = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ArrayList<Lumber> lumberlist = new ArrayList<Lumber>();

                statement.setInt(1, length);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int lumberid = resultSet.getInt("id");
                    int amount = resultSet.getInt("amount");

                    Optional<LumberType> lumberTypeOptional = LumbertypeFacade.getLumbertypeById(resultSet.getInt("type"), connectionPool);
                    LumberType lumberType;
                    if (lumberTypeOptional.isPresent()) {
                        lumberType = lumberTypeOptional.get();
                    } else {
                        throw new DatabaseException("Could not get lumber by length");
                    }
                    int price = calcPrice(length, lumberType.getMeterPrice());

                    Lumber lumber = new Lumber(lumberid, length, lumberType, price, amount);
                    lumberlist.add(lumber);
                }

                if (lumberlist.size() == 0) {
                    throw new DatabaseException("Could not get lumber by length");
                }

                return Optional.of(lumberlist);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get lumber by length");
        }
    }

    protected static int calcPrice(float length, float meter_price) {
        return Math.round(length * (meter_price / 100));
    }
}