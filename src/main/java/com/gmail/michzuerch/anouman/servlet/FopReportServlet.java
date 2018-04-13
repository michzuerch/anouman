package com.gmail.michzuerch.anouman.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(name = "FopReportServlet", urlPatterns = "/fop/*")
public class FopReportServlet extends HttpServlet {
    //    protected URIResolver uriResolver;
//    private TransformerFactory tFactory = TransformerFactory.newInstance();
//    private FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
//    private Fop fop;

    @Override
    public void init() throws ServletException {
        super.init();
//        this.uriResolver = new ServletContextURIResolver(getServletContext());
//        try {
//            fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
//            Source xsltSrc = new StreamSource(new File("foo-xml2fo.xsl"));
//            Transformer transformer = tFactory.newTransformer(xsltSrc);
//            Result res = new SAXResult(fop.getDefaultHandler());
//            Source src = new StreamSource(new File("foo.xml"));
//            transformer.transform(src, res);
//
//        } catch (FOPException fex) {
//
//        } catch (TransformerConfigurationException e) {
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }

    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(out.size());
        response.getOutputStream().write(out.toByteArray());
        response.getOutputStream().flush();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }
}
