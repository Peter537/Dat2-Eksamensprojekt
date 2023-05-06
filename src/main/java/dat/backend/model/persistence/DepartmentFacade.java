package dat.backend.model.persistence;

import dat.backend.model.entities.Department;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.DepartmentNotFoundException;

public class DepartmentFacade {

    public static Department getDepartmentById(int departmentId, ConnectionPool connectionPool) throws DatabaseException, DepartmentNotFoundException {
        return DepartmentMapper.getDepartmentById(departmentId, connectionPool);
    }
}