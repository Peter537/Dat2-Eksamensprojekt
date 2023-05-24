package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "generate-custom-partslist", value = "/generate-custom-partslist")
public class GenerateCustomPartsList extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/generateCustomPartsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String edit = request.getParameter("edit");
            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(Integer.parseInt(request.getParameter("orderId")), connectionPool);
            request.setAttribute("carportOrder", carportOrder);
            request.setAttribute("edit", edit);
            request.getRequestDispatcher("WEB-INF/generateCustomPartsList.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}