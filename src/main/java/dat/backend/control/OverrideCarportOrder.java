package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "OverrideCarportOrder", value = "/OverrideCarportOrder")
public class OverrideCarportOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fromJsp = request.getParameter("fromJsp");

        int orderId = (int) Float.parseFloat(request.getParameter("orderId"));
        int length = (int) Float.parseFloat(request.getParameter("length"));
        int width = (int) Float.parseFloat(request.getParameter("width"));
        int height = (int) Float.parseFloat(request.getParameter("minHeight"));
        Optional<Float> price = Optional.of(Float.parseFloat(request.getParameter("price")));

        try {
            CarportOrder order = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);

            CarportOrderFacade.updateLength(order, length, connectionPool);
            CarportOrderFacade.updateWidth(order, width, connectionPool);
            CarportOrderFacade.updateMinHeight(order, height, connectionPool);
            CarportOrderFacade.updatePrice(order, price, connectionPool);

            request.setAttribute("carportOrder", order);
            request.setAttribute("orderId", orderId);
            request.setAttribute("fromJsp", fromJsp);
            request.getRequestDispatcher("detailed-order-info").forward(request, response);

        } catch (DatabaseException | NotFoundException | ValidationException e) {
            e.printStackTrace();
        }
    }
}
