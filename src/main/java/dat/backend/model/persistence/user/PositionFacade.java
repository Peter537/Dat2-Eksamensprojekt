package dat.backend.model.persistence.user;

import dat.backend.model.entities.user.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

public class PositionFacade {

    public static Position getPositionByPositionName(String positionName, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return PositionMapper.getPositionByPositionName(positionName, connectionPool);
    }
}