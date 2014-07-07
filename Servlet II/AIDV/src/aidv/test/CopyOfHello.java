package aidv.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import aidv.Validator;
import aidv.classes.Biomodel;
import aidv.tools.ParserJ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/validate")
public class CopyOfHello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyOfHello() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String annotationURL = request.getParameter("annotaion");
		if(annotationURL!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			out.println(Validator.getAnnotation(annotationURL));
		}
		String biomodeURL = request.getParameter("biomodel");
		if(biomodeURL!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			out.println(Validator.getBiomodel(biomodeURL));
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
 
   
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
         
       
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
         
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        
        String uploadPath = getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
         
        try {
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
             
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
               
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                     
                    // saves the file on disk
                    item.write(storeFile);
                    
                    File f = storeFile;
             ParserJ p1 = new ParserJ(f);
             Biomodel b1 = p1.getBiomodel();
             ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
             String json = ow.writeValueAsString(b1);
             System.out.println(json);
                }
            }
            request.setAttribute("message", "Uploaded");
        } catch (Exception ex) {
            request.setAttribute("message", "Error: " + ex.getMessage());
        }
    }
}