package dat.backend.model.persistence;

import dat.backend.model.entities.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.PositionNotFoundException;

public class PositionFacade {

    public static Position getPositionByPositionName(String positionName, ConnectionPool connectionPool) throws DatabaseException, PositionNotFoundException {
        return PositionMapper.getPositionByPositionName(positionName, connectionPool);
    }
}