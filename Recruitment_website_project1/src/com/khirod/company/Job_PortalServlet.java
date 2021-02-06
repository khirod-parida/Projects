package com.khirod.company;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadParameters;
import oracle.jdbc.driver.OracleDriver;

public class Job_PortalServlet extends HttpServlet {
	
	private static final String COMPANY_ENTRY="INSERT INTO COMPANY VALUES(?,?,?,?,?,?,?)";
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		
		String resumeLocation="G:/Upload/resume/";
		
		String photoLocation="G:/Upload/photo/";
		
		res.setContentType("text/html");
		
		PrintWriter pw=res.getWriter();
		
	String name=null;
	
	String email=null;
	
	String location=null;
	
	String add=null;
	
	String skill=null;
	
	String resumeFilePath=null;
	
	String photoFilePath=null;
	
	int result=0;
	

		
		pw.println("<body background='office1.jpg' style='background-repeat:no-repeat;background-size:100%'></body>");
		
		try {
			MultipartFormDataRequest md=new MultipartFormDataRequest(req);
			
			name=req.getParameter("name");
			
		    email=req.getParameter("email");
			
		    location=req.getParameter("loc");
			
		    add=req.getParameter("add");
			
			skill=req.getParameter("skill");
			
			UploadBean up=new  UploadBean();
			
			up.setFolderstore(resumeLocation);
			
			up.store(md,"resume");
			
			up.setFolderstore(photoLocation);
			
			up.store(md,"photo");
			
			Vector vector=up.getHistory();
			
		    resumeFilePath=resumeLocation+((UploadParameters)vector.get(0)).getFilename();
			
		    photoFilePath=photoLocation+((UploadParameters)vector.get(1)).getFilename();
			
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	    
	     
	    oracle.jdbc.driver.OracleDriver d =new oracle.jdbc.driver.OracleDriver();
	    
	    
	     
	   
		
	   try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","MANAGER","SYSTEM")){
			try(PreparedStatement ps=con.prepareStatement( COMPANY_ENTRY)) {
				
				ps.setString(1,name);
				
				ps.setString(2, email);
				
				ps.setString(3, location);
				
				ps.setString(4, add);
				
				ps.setString(5, skill);
				
				ps.setString(6, resumeFilePath);
				
				ps.setString(7, photoFilePath);
				
				result=ps.executeUpdate();
				
				if(result==1)
					pw.println("<h1 style='color:red;text-align:center'>Entry is successful</h1>");
				else
					pw.println("<h1 style='color:red;text-align:center'>Entry is not successful</h1>");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1 style='color:red;text-align:center'>Problem in Entry...</h1>");
		}
				
			pw.println("<br><a href='front.html'>Home</a>");
			pw.close();
			
		}

		
		
	
public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException ,IOException {
	doPost(req,res);
}	

}
