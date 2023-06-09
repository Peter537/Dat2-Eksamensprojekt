package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.EmployeeFacade;
import dat.backend.model.services.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles the login of a user.
 * It checks whether the user is a customer or an employee.
 * If the user is a customer, the user is redirected to the customer site.
 * If the user is an employee, the user is redirected to the employee site.
 */
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute("user", null); // invalidating user object in session scope
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            try {
                if (Validation.isValidCustomerEmail(email)) {
                    Customer customer = CustomerFacade.login(email, password, connectionPool);
                    request.getSession().setAttribute("user", customer);
                    request.getSession().setAttribute("myhome", "customer-site");
                    request.getSession().setAttribute("lateststatus", CarportOrderFacade.getLatestOrderStatusFromCustomer(customer, connectionPool));
                    request.getRequestDispatcher("WEB-INF/customerSite.jsp").forward(request, response);
                } else {
                    Employee employee = EmployeeFacade.login(email, password, connectionPool);
                    request.getSession().setAttribute("user", employee);
                    request.getSession().setAttribute("myhome", "employee-site");
                    request.getSession().setAttribute("news", CarportOrderFacade.getCarportOrdersAsNews(connectionPool));
                    request.getRequestDispatcher("WEB-INF/employeeSite.jsp").forward(request, response);
                }
            } catch (NotFoundException | ValidationException e) {
                request.setAttribute("errormessage", "Wrong username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}