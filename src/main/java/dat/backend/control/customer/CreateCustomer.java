package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.exceptions.AlreadyExistsException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.services.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "create-customer", value = "/create-customer")
public class CreateCustomer extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", null); // invalidating user object in session scope
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        try {
            if (!Validation.isValidCustomerEmail(email)) {
                request.setAttribute("errormessage", "Invalid email");
                request.getRequestDispatcher("createCustomer.jsp").forward(request, response);
                return;
            }

            if (!password.equals(confirmPassword)) {
                request.setAttribute("errormessage", "Passwords do not match");
                request.getRequestDispatcher("createCustomer.jsp").forward(request, response);
                return;
            }

            try {
                Customer customer = CustomerFacade.create(email, password, name, connectionPool);
                session.setAttribute("user", customer);
                request.getRequestDispatcher("ToProfileSite").forward(request, response);
            } catch (ValidationException | AlreadyExistsException e) {
                request.setAttribute("errormessage", "User could not be created");
                request.getRequestDispatcher("createCustomer.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}