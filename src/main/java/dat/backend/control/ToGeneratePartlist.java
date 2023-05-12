package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "to-generate-partlist", value = "/to-generate-partlist")
public class ToGeneratePartlist extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
    }
}