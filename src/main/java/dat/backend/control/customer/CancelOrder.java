package dat.backend.control.customer;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "cancel-order", value = "/cancel-order")
public class CancelOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            int orderId = Integer.parseInt(request.getParameter("orderId"));
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);

            Customer customer = (Customer) request.getSession().getAttribute("user");


           CarportOrderFacade.cancelOrder(carportOrder, connectionPool);

            request.setAttribute("cancel", "Ordren er nu annulleret");
            request.setAttribute("orderId", orderId);
            request.setAttribute("fromJsp", "customer");
            request.getRequestDispatcher("detailed-order-info").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }

    }
}
