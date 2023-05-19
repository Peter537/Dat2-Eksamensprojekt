package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToPartslistFrame", value = "/ToPartslistFrame")
public class ToPartslistFrame extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("partslist", request.getSession().getAttribute("partslist"));
        request.getSession().removeAttribute("partslist");
        request.getRequestDispatcher("WEB-INF/Frames/partslistFrame.jsp").forward(request, response);
    }
}