package dat.backend.model.persistence;

import dat.backend.model.entities.Position;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.PositionNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class PositionMapper {

    static Position getPositionByPositionName(String positionName, ConnectionPool connectionPool) throws DatabaseException, PositionNotFoundException {
        String query = "SELECT * FROM position WHERE position = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, positionName);
                ResultSet resultSet = statement.executeQuery();
                return createPositionFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get position");
        }
    }

    private static Position createPositionFromResultSet(ResultSet resultSet) throws SQLException, PositionNotFoundException {
        if (!resultSet.next()) {
            throw new PositionNotFoundException("No position found");
        }

        String positionName = resultSet.getString("position");
        return new Position(positionName);
    }
}