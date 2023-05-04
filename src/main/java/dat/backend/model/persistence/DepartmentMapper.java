package dat.backend.model.persistence;

import dat.backend.model.entities.Address;
import dat.backend.model.entities.Department;
import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DepartmentMapper {

    static Department getDepartmentById(int id, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT * FROM department WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    throw new DatabaseException("Could not get department");
                }

                String departmentName = resultSet.getString("name");
                int zipCode = resultSet.getInt("zipcode");
                String address = resultSet.getString("address");
                Zip zip = ZipMapper.getZipByZipCode(zipCode, connectionPool);
                Address addressObject = new Address(address, zip);
                return new Department(id, departmentName, addressObject);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get department");
        }
    }
}