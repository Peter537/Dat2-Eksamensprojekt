package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ZipMapper {

    /**
     * Retrieve a Zip object by a zip code
     *
     * @param zipCode        The zip code to search for
     * @param connectionPool Connection pool
     * @return The Zip object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the zip code does not exist
     */
    static Zip getZipByZipCode(int zipCode, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
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

    /**
     * This method creates a Zip object from a ResultSet
     *
     * @param resultSet ResultSet to create Zip object from
     * @return Zip object
     * @throws SQLException      if an error occurs while communicating with the database
     * @throws NotFoundException if the zip code does not exist
     */
    private static Zip createZipFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Zip not found");
        }

        int zipCode = resultSet.getInt("zipcode");
        String city = resultSet.getString("city_name");
        return new Zip(zipCode, city);
    }
}