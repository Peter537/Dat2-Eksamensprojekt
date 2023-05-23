package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "to-generate-custom-partlist", value = "/to-generate-custom-partlist")
public class ToGenerateCustomPartlist extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/generateCustomPartlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(Integer.parseInt(request.getParameter("orderId")), connectionPool);

            request.setAttribute("carportOrder", carportOrder);
            request.getRequestDispatcher("WEB-INF/generateCustomPartlist.jsp").forward(request, response);
        } catch (DatabaseException | NotFoundException e) {
            e.printStackTrace();
        }


    }
}
