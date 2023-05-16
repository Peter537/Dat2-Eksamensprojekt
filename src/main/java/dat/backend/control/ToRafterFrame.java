package dat.backend.control;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumberTypeFacade;
import dat.backend.model.persistence.item.RoofFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "ToRafterFrame", value = "/ToRafterFrame")
public class ToRafterFrame extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<LumberType> lumberTypeRafters = LumberTypeFacade.getLumberTypeByType("RAFTER", connectionPool);
            List<Lumber> lumberRafters = new ArrayList<>();
            for (LumberType type : lumberTypeRafters) {
                lumberRafters.addAll(LumberFacade.getLumberByType(type, connectionPool));
            }

            request.setAttribute("rafters", lumberRafters);
            request.getRequestDispatcher("/WEB-INF/Frames/rafterFrame.jsp").forward(request, response);
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