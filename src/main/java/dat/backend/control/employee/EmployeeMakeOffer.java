package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "employee-make-offer", value = "/employee-make-offer")
public class EmployeeMakeOffer extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        float priceOffer = Float.parseFloat(request.getParameter("priceOffer"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String fromJsp = request.getParameter("fromJsp");
        PartsList partsList = (PartsList) request.getSession().getAttribute("partslist");

        try {
            CarportOrder carport = CarportOrderFacade.getCarportOrderById(orderId, connectionPool);

            CarportOrderFacade.makeOffer(carport, priceOffer, connectionPool);
            CarportOrderFacade.updatePartsListPDF(orderId, partsList, connectionPool);

            request.setAttribute("orderId", orderId);
            request.setAttribute("fromJsp", fromJsp);
            request.getRequestDispatcher("detailed-order-info").forward(request, response);
        } catch (DatabaseException | NotFoundException | ValidationException e) {
            e.printStackTrace();
        }


    }
}