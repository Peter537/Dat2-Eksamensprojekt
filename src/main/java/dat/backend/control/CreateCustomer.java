package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.CustomerAlreadyExistsException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CustomerFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "create-customer", value = "/create-customer")
public class CreateCustomer extends HttpServlet {

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
        HttpSession session = request.getSession();
        session.setAttribute("user", null); // invalidating user object in session scope
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            if (!password.equals(confirmPassword)) {
                request.setAttribute("errormessage", "Passwords do not match");
                request.getRequestDispatcher("createCustomer.jsp").forward(request, response);
                return;
            }

            try {
                Customer customer = CustomerFacade.createCustomer(email, password, name, connectionPool);
                session.setAttribute("user", customer);
                request.getRequestDispatcher("WEB-INF/profileSite.jsp").forward(request, response);
            } catch (ValidationException | CustomerAlreadyExistsException e) {
                request.setAttribute("errormessage", "User could not be created");
                request.getRequestDispatcher("createCustomer.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

