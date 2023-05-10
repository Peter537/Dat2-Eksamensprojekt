package dat.backend.model.persistence.order;

import dat.backend.model.entities.OrderStatus;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.OrderStatusNotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class OrderStatusMapper {

    static OrderStatus getOrderStatusByStatus(String status, ConnectionPool connectionPool) throws DatabaseException, OrderStatusNotFoundException {
        String query = "SELECT * FROM orderstatus WHERE status = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, status);
                ResultSet resultSet = statement.executeQuery();
                return createOrderStatusFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get order status by status");
        }
    }

    private static OrderStatus createOrderStatusFromResultSet(ResultSet resultSet) throws SQLException, OrderStatusNotFoundException {
        if (!resultSet.next()) {
            throw new OrderStatusNotFoundException("Order status not found");
        }

        String status = resultSet.getString("status");
        String displayName = resultSet.getString("displayName");
        int sortValue = resultSet.getInt("sortvalue");
        return new OrderStatus(status, displayName, sortValue);
    }
}