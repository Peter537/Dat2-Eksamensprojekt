package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToCustomerOrders", value = "/ToCustomerOrders")
public class ToCustomerOrders extends HttpServlet {
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
        } catch (DatabaseException | NotFoundException | ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }

        request.setAttribute("carportOrders", carportOrders);
        request.getRequestDispatcher("WEB-INF/customerOrders.jsp").forward(request, response);
    }
}
