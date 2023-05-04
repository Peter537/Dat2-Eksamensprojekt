package dat.backend.model.persistence;

import dat.backend.model.entities.Address;
import dat.backend.model.entities.Department;
import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class DepartmentMapper {

    static Optional<Department> getDepartmentById(int id, ConnectionPool connectionPool) throws DatabaseException {
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

    private static Optional<Department> createDepartmentFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        if (!resultSet.next()) {
            return Optional.empty();
        }

        int id = resultSet.getInt("id");
        String departmentName = resultSet.getString("name");
        int zipCode = resultSet.getInt("zipcode");
        String address = resultSet.getString("address");
        Optional<Zip> zip = ZipMapper.getZipByZipCode(zipCode, connectionPool);
        if (!zip.isPresent()) {
            throw new DatabaseException("Could not get zip");
        }

        Address addressObject = new Address(address, zip.get());
        return Optional.of(new Department(id, departmentName, addressObject));
    }
}