package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;

public class LumberFacade {

    public static Lumber getLumberById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return LumberMapper.getLumberById(id, connectionPool);
    }

    public static Lumber createLumber(float length, int lumberType, int amount, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.createLumber(length, lumberType, amount, connectionPool);
    }

    public static List<Lumber> getAllLumber(ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getAllLumber(connectionPool);
    }

    public static List<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByType(lumberType, connectionPool);
    }

    public static List<Lumber> getLumberByLength(int length, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByLength(length, connectionPool);
    }

    public static void deleteLumber(int id, ConnectionPool connectionPool) throws DatabaseException {
        LumberMapper.deleteLumber(id, connectionPool);
    }

    public static void updateLumber(int id, float poleLength, int lumberTypeId, int amount, ConnectionPool connectionPool) throws DatabaseException {
        LumberMapper.updateLumber(id, poleLength, lumberTypeId, amount, connectionPool);
    }
}