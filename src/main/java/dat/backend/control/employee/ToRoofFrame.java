package dat.backend.control.employee;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.RoofFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToRoofFrame", value = "/ToRoofFrame")
public class ToRoofFrame extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Roof> roofs = RoofFacade.getAllRoofs(connectionPool);

            request.setAttribute("roofs", roofs);
            request.getRequestDispatcher("/WEB-INF/Frames/roofFrame.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }
}