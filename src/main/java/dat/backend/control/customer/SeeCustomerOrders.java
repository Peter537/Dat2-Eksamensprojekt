package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This servlet's purpose is to forward the customer to the seeCustomerOrders.jsp page and show all the orders the user has previously made.
 */

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-customer-orders", value = "/see-customer-orders")
public class SeeCustomerOrders extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Customer customer = (Customer) request.getSession().getAttribute("user");
        List<CarportOrder> carportOrders = new ArrayList<>();
        try {
            carportOrders.addAll(CarportOrderFacade.getCarportOrdersByCustomer(customer, connectionPool));
        } catch (DatabaseException | ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }

        request.setAttribute("carportOrders", carportOrders);
        request.getRequestDispatcher("WEB-INF/seeCustomerOrders.jsp").forward(request, response);
    }
}