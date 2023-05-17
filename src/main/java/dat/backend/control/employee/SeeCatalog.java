package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.persistence.ConnectionPool;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-catalog", value = "/see-catalog")
public class SeeCatalog extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.getRequestDispatcher("WEB-INF/seeCatalog.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doGet(request, response);
    }
}