package dat.backend.model.persistence;

import dat.backend.model.entities.Address;
import dat.backend.model.entities.Department;
import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.DepartmentNotFoundException;
import dat.backend.model.exceptions.ZipNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DepartmentMapper {

    static Department getDepartmentById(int id, ConnectionPool connectionPool) throws DatabaseException, DepartmentNotFoundException {
        String query = "SELECT * FROM department WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createDepartmentFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get department");
        }
    }

    private static Department createDepartmentFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, DatabaseException, DepartmentNotFoundException {
        if (!resultSet.next()) {
            throw new DepartmentNotFoundException("Department not found");
        }

        int id = resultSet.getInt("id");
        String departmentName = resultSet.getString("name");
        int zipCode = resultSet.getInt("zipcode");
        String address = resultSet.getString("address");
        try {
            Zip zip = ZipFacade.getZipByZipCode(zipCode, connectionPool);
            Address addressObject = new Address(address, zip);
            return new Department(id, departmentName, addressObject);
        } catch (ZipNotFoundException e) {
            throw new DatabaseException("Could not get zip");
        }
    }
}