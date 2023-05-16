package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.order.CarportOrderFacade;
import dat.backend.model.persistence.user.EmployeeFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EmployeeClaimOrder", value = "/EmployeeClaimOrder")
public class EmployeeClaimOrder extends HttpServlet {

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

        Employee employee = (Employee) request.getSession().getAttribute("user");
        try {
            CarportOrder carport = CarportOrderFacade.getCarportOrderById(Integer.parseInt(request.getParameter("orderId")), connectionPool);

            CarportOrderFacade.claim(carport, employee, connectionPool);

            request.getRequestDispatcher("WEB-INF/seeAllOrders.jsp").forward(request, response);


        } catch (DatabaseException | NotFoundException | ValidationException e) {
            e.printStackTrace();
        }



    }
}
