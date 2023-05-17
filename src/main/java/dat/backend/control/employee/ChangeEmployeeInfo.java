package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.EmployeeFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "change-employee-info", value = "/change-employee-info")
public class ChangeEmployeeInfo extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = (Employee) request.getSession().getAttribute("user");
        changeName(employee, request);
        changePassword(employee, request);
        changePersonPhoneNumber(employee, request);
        request.getRequestDispatcher("WEB-INF/employeeSite.jsp").forward(request, response);
    }

    public void changeName(Employee employee, HttpServletRequest request) {
        String name = request.getParameter("name");
        if (name != null && !name.isEmpty()) {
            try {
                EmployeeFacade.updateName(employee, name, connectionPool);
                request.setAttribute("nameSuccess", "navn-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "navnet kunne til dette");
            }
        }
    }

    public void changePassword(Employee employee, HttpServletRequest request) {
        String password = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password != null && !password.isEmpty() && password.equals(confirmPassword)) {
            try {
                EmployeeFacade.updatePassword(employee, password, connectionPool);
                request.setAttribute("passwordSuccess", "password-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "password kunne ikke opdateres, prøv igen.");
            }
        }
    }

    public void changePersonPhoneNumber(Employee employee, HttpServletRequest request) {
        String oldEmployeePhone = "";
        if (employee.getPersonalPhoneNumber().isPresent()) {
            oldEmployeePhone = employee.getPersonalPhoneNumber().get();
        }

        String phone = request.getParameter("newPhoneNumber");
        if (phone != null && !phone.isEmpty() && !oldEmployeePhone.equals(phone)) {
            try {
                EmployeeFacade.updatePersonalPhoneNumber(employee, phone, connectionPool);
                request.setAttribute("phoneSuccess", "telefonnummer-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "Telefonnummer kunne ikke opdateres. Telefonnummeret skal være 8 cifre.");
            }
        }
    }
}