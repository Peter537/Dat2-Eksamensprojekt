package dat.backend.model.persistence;

import dat.backend.model.entities.Lumber;
import dat.backend.model.entities.LumberType;
import dat.backend.model.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.Optional;

public class LumbertypeFacade {
    public static Optional<LumberType> getLumbertypeById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return LumbertypeMapper.getLumbertypeById(id, connectionPool);
    }
    public static Optional<ArrayList<LumberType>> getLumbertypeByType(String lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return LumbertypeMapper.getLumbertypeByType(lumberType, connectionPool);
    }
}
