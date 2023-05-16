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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@IgnoreCoverage(reason = "Servlet class should not be tested")
@WebServlet(name = "see-catalog", value = "/see-catalog")
public class SeeCatalog extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            List<LumberType> lumberTypePoles = LumberTypeFacade.getLumberTypeByType("POLE", connectionPool);
            List<Lumber> lumberPoles = new ArrayList<>();
            for (LumberType type : lumberTypePoles) {
                lumberPoles.addAll(LumberFacade.getLumberByType(type, connectionPool));
            }

            List<LumberType> lumberTypeRafters = LumberTypeFacade.getLumberTypeByType("RAFTER", connectionPool);
            List<Lumber> lumberRafters = new ArrayList<>();
            for (LumberType type : lumberTypeRafters) {
                lumberRafters.addAll(LumberFacade.getLumberByType(type, connectionPool));
            }

            request.setAttribute("poles", lumberPoles);
            request.setAttribute("rafters", lumberRafters);
            request.getRequestDispatcher("WEB-INF/seeCatalog.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doGet(request, response);
    }
}