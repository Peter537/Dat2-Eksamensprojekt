package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.EmployeeFacade;
import dat.backend.model.services.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "change-employee-phonenumber", value = "/change-employee-phonenumber")
public class ChangeEmployeePhoneNumber extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        String newPersonalPhoneNumber = request.getParameter("newPersonalPhoneNumber");
        if (newPersonalPhoneNumber != null) {
            if (employee.getPersonalPhoneNumber().isPresent()) {
                if (employee.getPersonalPhoneNumber().get().equals(newPersonalPhoneNumber)) {
                    return;
                }
            }

            try {
                Validation.validatePhoneNumber(newPersonalPhoneNumber);
                EmployeeFacade.updatePersonalPhoneNumber(employee, newPersonalPhoneNumber, connectionPool);
                request.getRequestDispatcher("WEB-INF/employeeOverview.jsp").forward(request, response);
            } catch (ValidationException e) {
                request.setAttribute("errormessage", "'" + newPersonalPhoneNumber + "' er ikke et dansk telefonnummer");
                request.getRequestDispatcher("WEB-INF/employeeOverview.jsp").forward(request, response);
            } catch (DatabaseException e) {
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
                return;
            }
        }

        String newWorkPhoneNumber = request.getParameter("newWorkPhoneNumber");
        if (newWorkPhoneNumber != null) {
            if (employee.getWorkPhoneNumber().isPresent()) {
                if (employee.getWorkPhoneNumber().get().equals(newWorkPhoneNumber)) {
                    return;
                }
            }

            try {
                Validation.validatePhoneNumber(newWorkPhoneNumber);
                EmployeeFacade.updateWorkPhoneNumber(employee, newWorkPhoneNumber, connectionPool);
                request.getRequestDispatcher("WEB-INF/employeeOverview.jsp").forward(request, response);
            } catch (ValidationException e) {
                request.setAttribute("errormessage", "'" + newWorkPhoneNumber + "' er ikke et dansk telefonnummer");
                request.getRequestDispatcher("WEB-INF/employeeOverview.jsp").forward(request, response);
            } catch (DatabaseException e) {
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
                return;
            }
        }
    }
}