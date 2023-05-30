package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.user.EmployeeFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This servlet's purpose is to let the employee change their infoformation from their profilesite.
 */
@MultipartConfig
@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "change-employee-info", value = "/change-employee-info")
public class ChangeEmployeeInfo extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = (Employee) request.getSession().getAttribute("user");
        this.changeName(employee, request);
        this.changePassword(employee, request);
        this.changePersonPhoneNumber(employee, request);
        this.changeProfilePicture(employee, request);
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
        String oldEmployeePhoneNumber = employee.getPersonalPhoneNumber().orElse("");
        String newPhoneNumber = request.getParameter("newPhoneNumber");
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && !oldEmployeePhoneNumber.equals(newPhoneNumber)) {
            try {
                EmployeeFacade.updatePersonalPhoneNumber(employee, newPhoneNumber, connectionPool);
                request.setAttribute("phoneSuccess", "telefonnummer-ændring succesfuldt");
            } catch (DatabaseException | ValidationException e) {
                request.setAttribute("errormessage", "Telefonnummer kunne ikke opdateres. Telefonnummeret skal være 8 cifre.");
            }
        }
    }

    public void changeProfilePicture(Employee employee, HttpServletRequest request) throws ServletException, IOException {
        //Get the file uploaded by the user
        Part filePart = request.getPart("imageFile");
        if (filePart.getSize() == 0) {
            return;
        }
        FileInputStream fileContent = (FileInputStream) filePart.getInputStream();

        //Update the profile picture
        try {
            EmployeeFacade.updateProfilePicture(employee, fileContent, ApplicationStart.getConnectionPool());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}