package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

import java.util.Optional;

public class LumberFacade {

    /*
     * TODO: Implement this class
     *
     *
     */


    public static Optional<Lumber> createLumber(int id, int length, int lumberType, int amount, ConnectionPool connectionPool) throws DatabaseException, DatabaseException {
        return LumberMapper.createLumber(id, length, lumberType, amount, connectionPool);
    }

    public static Optional<Lumber> getLumberById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberById(id, connectionPool);
    }

    public static Optional<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByType(lumberType, connectionPool);
    }

    public static Optional<Lumber> getLumberByLength(int length, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByLength(length, connectionPool);
    }



}