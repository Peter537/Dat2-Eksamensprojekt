package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

public class ZipFacade {

    public static Zip getZipByZipCode(int zipCode, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return ZipMapper.getZipByZipCode(zipCode, connectionPool);
    }
}