package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.persistence.user.EmployeeFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-employee-orders", urlPatterns = {"/see-employee-orders"})
public class SeeEmployeeOrders extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Employee employee = (Employee) request.getSession().getAttribute("user");

        List<CarportOrder> carportOrders = new ArrayList<>();

        try {

            carportOrders.addAll(CarportOrderFacade.getCarportOrdersByEmployee(employee, connectionPool));
            
        } catch (DatabaseException | NotFoundException | ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }

        request.setAttribute("carportOrders", carportOrders);
        request.getRequestDispatcher("WEB-INF/seeEmployeeOrders.jsp").forward(request, response);
    }
}
