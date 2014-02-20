/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class addUser extends HttpServlet
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
           
           
            String uname = request.getParameter("unameTxt");
            String password = request.getParameter("passTxt");
          
            
            
            ResultSet resultDB;
           
            String dbName;
            	
            String findUser = "Select user,host from mysql.user where user = '" + uname+"'";
            
         
            resultDB = myDBStm.executeQuery(findUser);
           
            if(resultDB.next()){
            	 resultDB = myDBStm.executeQuery("Select user,host from mysql.user");
                 out.println("<div align = 'Center'>");
                 out.println("<table border>");
                 
                 out.println("<tr><td>User</th><th>Host</th></tr>");
             
                 while (resultDB.next())
                 {
                                    
                 	String user = resultDB.getString("user");
                 	String host = resultDB.getString("host");
                 	out.println("<tr>");
                 	out.println("<td><a href='addTo?user="+user+"&password=" + password + "'>"+ user +"</a></td>");
                 	out.println("<td>"+ host +"</td>");
                 	out.println("</tr>");
                     
                 
                 }
                 
                 out.println("<table>");
                 out.println("</div>"); 
            	 
            }else{
            	  String addUser = "create user " + "'" + uname +"'" + "@'localhost'"  + "IDENTIFIED BY '" + password + "';";  
                
                  int resultCreate= myDBStm.executeUpdate(addUser);
                  
                  resultDB = myDBStm.executeQuery("Select user,host from mysql.user");
                  out.println("<div align = 'Center'>");
                  out.println("<table border>");
                  
                  out.println("<tr><td>User</th><th>Host</th></tr>");
              
                  while (resultDB.next())
                  {
                                     
                  	String user = resultDB.getString("user");
                  	String host = resultDB.getString("host");
                  	out.println("<tr>");
                  	out.println("<td><a href='addTo?user="+user+"&password=" + password + "'>"+ user +"</a></td>");
                  	out.println("<td>"+ host +"</td>");
                  	out.println("</tr>");
                      
                  
                  }
                  
                  out.println("<table>");
                  out.println("</div>"); 
            }
       
        	
        	
           
              resultDB.close();
              myDBStm.close();
              connection.close();
            }
           
        catch (SQLException ex) {
              while (ex != null) {
            	  
            	  
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                    response.sendRedirect("http://localhost:8080/ProjectCS122B/addUser.html");
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

