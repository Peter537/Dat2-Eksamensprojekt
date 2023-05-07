package dat.backend.model.persistence;

import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class ZipMapper {

    static Optional<Zip> getZipByZipCode(int zipCode, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM zip WHERE zipcode = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, zipCode);
                ResultSet resultSet = statement.executeQuery();
                return createZipFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get zip by zip code");
        }
    }

    private static Optional<Zip> createZipFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }

        int zipCode = resultSet.getInt("zipcode");
        String city = resultSet.getString("city_name");
        return Optional.of(new Zip(zipCode, city));
    }
}