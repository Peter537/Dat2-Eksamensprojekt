package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.PositionNotFoundException;
import dat.backend.model.persistence.ConnectionPool;

public class PositionFacade {

    public static Position getPositionByPositionName(String positionName, ConnectionPool connectionPool) throws DatabaseException, PositionNotFoundException {
        return PositionMapper.getPositionByPositionName(positionName, connectionPool);
    }
}