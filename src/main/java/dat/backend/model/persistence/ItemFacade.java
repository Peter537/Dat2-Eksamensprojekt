package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

public class ItemFacade {

    /*
     * TODO: Implement this class
     */



    public static List<Lumber> getLumberByLumberType(LumberType LumberType, ConnectionPool connectionPool) throws DatabaseException {
        return ItemMapper.getLumberByLumberType(LumberType, connectionPool);
    }
}