package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet's purpose is to redirect the employee to the employee site.
 */
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "employee-site", value = "/employee-site")
public class ToEmployeeSite extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/employeeSite.jsp").forward(request, response);
    }
}