package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.PartsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This servlet's purpose is to forward the employee to the orderPartsListFrame.jsp page
 * with a partlist-attribute taken from requestscope and re-added to requestscope.
 */

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "order-partslist-frame", value = "/order-partslist-frame")
public class OrderPartsListFrame extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PartsList partsList = (PartsList) request.getSession().getAttribute("partslist");
        request.setAttribute("partslist", partsList);
        request.getRequestDispatcher("WEB-INF/orderPartsListFrame.jsp").forward(request, response);
    }

}