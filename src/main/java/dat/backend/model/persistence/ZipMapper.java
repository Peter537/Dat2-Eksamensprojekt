package dat.backend.model.persistence;

import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ZipMapper {

    static Zip getZipByZipCode(int zipCode, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM zip WHERE zipcode = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, zipCode);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new SQLException("Could not find zip code");
                }

                String city = resultSet.getString("city_name");
                return new Zip(zipCode, city);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get zip by zip code");
        }
    }
}