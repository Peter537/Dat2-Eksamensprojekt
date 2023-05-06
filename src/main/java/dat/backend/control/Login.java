package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Customer;
import dat.backend.model.exceptions.CustomerNotFoundException;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CustomerFacade;

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
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("customer", null); // invalidating user object in session scope
        String username = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            try {
                Customer customer = CustomerFacade.login(username, password, connectionPool);
                session.setAttribute("customer", customer);
                request.getRequestDispatcher("WEB-INF/profileSite.jsp").forward(request, response);
            } catch (CustomerNotFoundException e) {
                request.setAttribute("errormessage", "Wrong username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}