package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.*;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.EmployeeFacade;
import dat.backend.model.services.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute("user", null); // invalidating user object in session scope
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            try {
                if (Validation.isCustomerEmail(email)) {
                    Customer customer = CustomerFacade.login(email, password, connectionPool);
                    request.getSession().setAttribute("user", customer);
                    request.getRequestDispatcher("WEB-INF/profileSite.jsp").forward(request, response);
                } else {
                    Employee employee = EmployeeFacade.login(email, password, connectionPool);
                    request.getSession().setAttribute("user", employee);
                    request.getRequestDispatcher("WEB-INF/employeeOverview.jsp").forward(request, response);
                }
            } catch (NotFoundException e) {
                request.setAttribute("errormessage", "Wrong username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (DatabaseException | ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}