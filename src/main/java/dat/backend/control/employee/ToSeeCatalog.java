package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet's purpose is to redirect the employee to the catalog page.
 */

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-catalog", value = "/see-catalog")
public class ToSeeCatalog extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.getRequestDispatcher("WEB-INF/seeCatalog.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doGet(request, response);
    }
}