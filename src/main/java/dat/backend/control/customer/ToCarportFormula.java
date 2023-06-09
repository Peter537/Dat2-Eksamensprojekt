package dat.backend.control.customer;

import dat.backend.annotation.IgnoreCoverage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet's purpose is to forward the customer to the customerSite.jsp page
 */
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "carport-formula", value = "/carport-formula")
public class ToCarportFormula extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/carportFormula.jsp").forward(request, response);
    }
}