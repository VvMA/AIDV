package aidv;

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

/**
 * Servlet implementation class Hello
 */
@WebServlet("/validate")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String annotationURL = request.getParameter("annotation");
		if(annotationURL!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			out.println(Validator.getAnnotation(annotationURL));
		}
		String biomodelURL = request.getParameter("biomodel");
		if(biomodelURL!=null) {
			PrintWriter out = response.getWriter();
		    response.setContentType("text/plain");
			out.println(Validator.getBiomodel(biomodelURL));
		}
	}

 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */  
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	// Test if Data contains Data/FileData
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }         
        // set max file size for uploaded content
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
        // if file is uploaded extract it
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            Iterator<FileItem> iter = formItems.iterator();
             
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
               
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);              
                    item.write(storeFile);
                    File file = storeFile;
                   
		            PrintWriter out = response.getWriter();
		 		    response.setContentType("text/plain");
		 		   // After file extraction get the json representation of biomodel and print to response
		 		    String json=Validator.getBiomodel(file);
		 			out.println(json);
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Error: " + ex.getMessage());
        }
    }
}