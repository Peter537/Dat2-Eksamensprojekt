package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Department;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

public class DepartmentFacade {

    public static Department getDepartmentById(int departmentId, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return DepartmentMapper.getDepartmentById(departmentId, connectionPool);
    }
}