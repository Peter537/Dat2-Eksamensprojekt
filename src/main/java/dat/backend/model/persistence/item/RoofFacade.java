package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;

public class RoofFacade {

    public static Roof getRoofById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return RoofMapper.getRoofById(id, connectionPool);
    }

    public static Roof createRoof(float squareMeterPrice, String type, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.createRoof(squareMeterPrice, type, connectionPool);
    }

    public static List<Roof> getRoofByType(String type, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getRoofByType(type, connectionPool);
    }

    public static List<Roof> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getAllRoofs(connectionPool);
    }

    public static void deleteRoof(int id, ConnectionPool connectionPool) throws DatabaseException {
        RoofMapper.deleteRoof(id, connectionPool);
    }
}