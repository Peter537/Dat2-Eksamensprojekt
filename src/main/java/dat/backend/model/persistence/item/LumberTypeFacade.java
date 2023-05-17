package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;

public class LumberTypeFacade {

    public static LumberType getLumberTypeById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return LumberTypeMapper.getLumberTypeById(id, connectionPool);
    }

    public static List<LumberType> getLumberTypeByType(String lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumberTypeMapper.getLumberTypeByType(lumberType, connectionPool);
    }

    public static LumberType createLumberType(float poleThickness, float poleWidth, float poleMeterPrice, String pole, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        return LumberTypeMapper.createLumberType(poleThickness, poleWidth, poleMeterPrice, pole, connectionPool);
    }
}