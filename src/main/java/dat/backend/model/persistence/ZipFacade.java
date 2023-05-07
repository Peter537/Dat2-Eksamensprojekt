package dat.backend.model.persistence;

import dat.backend.model.entities.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ZipNotFoundException;

public class ZipFacade {

    public static Zip getZipByZipCode(int zipCode, ConnectionPool connectionPool) throws DatabaseException, ZipNotFoundException {
        return ZipMapper.getZipByZipCode(zipCode, connectionPool);
    }
}