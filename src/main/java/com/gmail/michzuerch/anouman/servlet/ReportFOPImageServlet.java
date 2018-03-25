package com.gmail.michzuerch.anouman.servlet;

import com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOPImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportFOPImageDeltaspikeFacade;
import org.apache.fop.servlet.ServletContextURIResolver;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.URIResolver;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "ReportFOPImageServlet", urlPatterns = "/reportfopimage/*")
public class ReportFOPImageServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ReportFOPImageServlet.class.getName());
    protected URIResolver uriResolver;

    @Inject
    ReportFOPImageDeltaspikeFacade reportFOPImageDeltaspikeFacade;

    @Override
    public void init() throws ServletException {
        super.init();
        this.uriResolver = new ServletContextURIResolver(getServletContext());
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] parameterValues = request.getParameterValues("id");
        if (parameterValues.length == 0) {
            LOGGER.warning("Fehler, Requestparameter íd nicht vorhanden");
            throw new IOException("Fehler, Requestparameter id nicht vorhanden");
        }
        Long id = Long.valueOf(parameterValues[0]);
        ReportFOPImage reportFOPImage = reportFOPImageDeltaspikeFacade.findBy(id);
        if (reportFOPImage == null) {
            LOGGER.warning("Artikelbild nicht gefunden, id: " + id);
            throw new IOException("Artikelbild nicht gefunden, id: " + id);
        }
        response.setContentType(reportFOPImage.getMimeType());
        response.setContentLength(reportFOPImage.getImage().length);
        response.getOutputStream().write(reportFOPImage.getImage());
        response.getOutputStream().flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }

}
