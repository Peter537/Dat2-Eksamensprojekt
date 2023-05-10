package dat.backend.model.persistence.item;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.Optional;

public class LumberFacade {
    public static Optional<Lumber> createLumber(float length, int lumberType, int amount, ConnectionPool connectionPool) throws DatabaseException, DatabaseException {
        return LumberMapper.createLumber(length, lumberType, amount, connectionPool);
    }

    public static Optional<Lumber> createLumber(Lumber lumber, ConnectionPool connectionPool) throws DatabaseException, DatabaseException {
        return LumberMapper.createLumber(lumber, connectionPool);
    }

    public static Optional<ArrayList<Lumber>> getAllLumber(ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getAllLumber(connectionPool);
    }

    public static Optional<Lumber> getLumberById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberById(id, connectionPool);
    }

    public static Optional<ArrayList<Lumber>> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByType(lumberType, connectionPool);
    }

    public static Optional<ArrayList<Lumber>> getLumberByLength(int length, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByLength(length, connectionPool);
    }



}