package aidv.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aidv.Validator;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getParameter("uri");
		if(uri!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			out.println(Validator.getAnnotation(uri));
		}
		String biomodel = request.getParameter("biomodel");
		if(biomodel!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();				
			String json = ow.writeValueAsString(Validator.getBiomodel(null));
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
