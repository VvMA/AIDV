package aidv.test;

import aidv.classes.*;
import aidv.classes.browser.Identifiers_org;
import aidv.classes.browser.OntologyBrowser;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getParameter("uri");
		if(uri!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			Annotation resource1=new Annotation(uri);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			OntologyBrowser identifiers=new Identifiers_org();
			Annotation a=identifiers.get(resource1);
			String json = ow.writeValueAsString(a);
			out.println(json);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}