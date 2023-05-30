package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Department;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DepartmentMapper {

    /**
     * This method will retrieve a Department object by an id
     *
     * @param id             The id to search for
     * @param connectionPool Connection pool
     * @return The Department object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static Department getDepartmentById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
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

    /**
     * This method will create a Department object from a ResultSet
     *
     * @param resultSet      ResultSet to create Department object from
     * @param connectionPool Connection pool
     * @return Department object
     * @throws SQLException      if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    private static Department createDepartmentFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("Department not found");
        }

        int id = resultSet.getInt("id");
        String departmentName = resultSet.getString("name");
        int zipCode = resultSet.getInt("zipcode");
        String address = resultSet.getString("address");
        try {
            Zip zip = ZipFacade.getZipByZipCode(zipCode, connectionPool);
            Address addressObject = new Address(address, zip);
            return new Department(id, departmentName, addressObject);
        } catch (NotFoundException | DatabaseException e) {
            throw new SQLException("Could not get zip");
        }
    }
}