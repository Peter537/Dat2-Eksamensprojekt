package dat.backend.model.persistence.item;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.Validation;

import java.util.List;

public class LumberFacade {

    public static Lumber createLumber(int length, int lumberType, int amount, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateLength(length);
        return LumberMapper.createLumber(length, lumberType, amount, connectionPool);
    }

    public static List<Lumber> getLumberByType(LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumberMapper.getLumberByType(lumberType, connectionPool);
    }

    public static void deleteLumber(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        LumberMapper.deleteLumber(id, connectionPool);
    }

    public static void updateLumber(int id, int poleLength, int lumberTypeId, int amount, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateLength(poleLength);
        Validation.validateAmount(amount);
        LumberMapper.updateLumber(id, poleLength, lumberTypeId, amount, connectionPool);
    }
}