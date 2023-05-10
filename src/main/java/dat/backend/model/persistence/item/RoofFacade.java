package dat.backend.model.persistence.item;

import dat.backend.model.entities.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.Optional;

public class RoofFacade {
    public static Optional<Roof> getRoofById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getRoofById(id, connectionPool);
    }
}
