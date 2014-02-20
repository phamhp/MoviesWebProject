/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class dbPrivilege extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>Privileges</TITLE></HEAD>");
        out.println("<BODY BGCOLOR=' #42a2ce'><H1>Privileges</H1>");


        try
           {
        	
        	 Class.forName("com.mysql.jdbc.Driver").newInstance();
        		
             // Connect to MySQL as root
            Connection connection = DriverManager.getConnection("jdbc:mysql://","root", "changeme");
           
            // Create and execute an SQL statement to get all the database names
            Statement myDBStm = connection.createStatement();
           
          
            String dbName = request.getParameter("dbName");
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            String cbOption = request.getParameter("cbOption");
            String GrantRevoke = request.getParameter("GrantRevoke");
            
           String query="";
           if(GrantRevoke.equals("Grant")){
        	   if(cbOption.equals("dbCreate")){
        		   query = "Grant create on " + dbName + ".* to '" +user+ "'@'localhost 'identified by '" + pass +"'";
        		   System.out.println(query);
        	   }else if(cbOption.equals("dbDrop")){
        		   query = "Grant drop on " + dbName + ".* to '" +user+ "'@'localhost 'identified by '" + pass +"'";
        		   System.out.println(query);
        	   }
           } else if(GrantRevoke.equals("Revoke")){
        	   if(cbOption.equals("dbCreate")){
        		   query = "Revoke create on " + dbName + ".* from '" +user+ "'@'localhost'";
        		   System.out.println(query);
        	   }else if(cbOption.equals("dbDrop")){
        		   query = "Revoke drop on " + dbName + ".* from '" +user+ "'@'localhost'";
        		   System.out.println(query);
        	   }
           }
            
            
            
         
            
            ResultSet resultDB;
            resultDB= myDBStm.executeQuery(query);
            resultDB= myDBStm.executeQuery("FLUSH PRIVILEGES");
            
            resultDB = myDBStm.executeQuery("show grants for '"  +user + "'@'localhost';" );
            out.println("<table border align = 'Center'>");
        	while(resultDB.next()){
        		
        		out.println("<tr><td>" + resultDB.getString(1)+ "</td></tr>");
        	}
            	
            
          
        	out.println("</table>");

            
          
 

        	
        	
           
              resultDB.close();
              myDBStm.close();
              connection.close();
            }
        catch (SQLException ex) {
              while (ex != null) {
                    out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}

