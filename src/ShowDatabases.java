
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ShowDatabases extends HttpServlet
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

        out.println("<HTML><HEAD><TITLE>Databases</TITLE></HEAD>");
       


        try
           {
        	
        	 Class.forName("com.mysql.jdbc.Driver").newInstance();
        		
             // Connect to MySQL as root
            Connection connection = DriverManager.getConnection("jdbc:mysql://","root", "changeme");
           
            // Create and execute an SQL statement to get all the database names
            Statement myDBStm = connection.createStatement();
           
            
     
            
           
            String cbOption = request.getParameter("cbOption");
           
            String GrantRevoke = request.getParameter("GrantRevoke");
       //     if(dbPrivilege!= null && clPrivilege == null && tbPrivilege ==null){
            if(cbOption != null){
           if(cbOption.toLowerCase().contains("db")){
        	   out.println("<BODY BGCOLOR='#42a2ce'><H1>Databases</H1>");
                ResultSet resultDB = myDBStm.executeQuery("show databases");
                HttpSession session = request.getSession(false);
            	String user = (String)session.getAttribute("sysUser");
            	String pass = (String)session.getAttribute("sysPass");
               
                String dbName;
                	
                out.println("<div align = 'center'><table border>");
                out.println("<tr><th> Database name</th></tr>");
                while (resultDB.next())
                {
                        dbName = resultDB.getString("Database");
                       
                        //only get information inside the "moviedb" database
                        if (!dbName.equals("mysql") && !dbName.equals("information_schema") && !dbName.equals("performance_schema"))
                        {
                                //first, we need to switch to this database;
                                Statement mySWStm = connection.createStatement();
                                mySWStm.execute("use " + dbName);
                                //Create and execute an SQL statement to get all the table names in moviedb
                                Statement myTBStm = connection.createStatement();
                                ResultSet resultTB = myTBStm.executeQuery("show tables");
                                //myStm.close();                       
                               
                              
                                out.println("<tr><td><a href='dbPrivilege?dbName="+dbName +"&user="+user +"&pass="+pass +"&cbOption="+cbOption +"&GrantRevoke="+ GrantRevoke+"'>" + dbName+"</td></tr>");
                                
                              
                        }
                }
                
             
                out.println("</table></div>");
                resultDB.close();
            }
          //  else if(tbPrivilege != null && clPrivilege == null){
            	else if(cbOption.toLowerCase().contains("tb")){	
            		   HttpSession session = request.getSession(false);
                   	String user = (String)session.getAttribute("sysUser");
                   	String pass = (String)session.getAttribute("sysPass");
            	ResultSet resultDB = myDBStm.executeQuery("show databases");
                 
                 String dbName;
                 	

                 while (resultDB.next())
                 {
                         dbName = resultDB.getString("Database");
                         
                         //only get information inside the "moviedb" database
                         if (!dbName.equals("mysql") && !dbName.equals("information_schema") && !dbName.equals("performance_schema"))
                         {
                                 //first, we need to switch to this database;
                                 Statement mySWStm = connection.createStatement();
                                 mySWStm.execute("use " + dbName);
                                 //Create and execute an SQL statement to get all the table names in moviedb
                                 Statement myTBStm = connection.createStatement();
                                 ResultSet resultTB = myTBStm.executeQuery("show tables");
                                 //myStm.close();                       
                                 String tblName;
                                

                                 out.println("<H3>Database: "  + dbName+ "</H3>");
                                 out.println("<table border align = 'center'>");
                                 out.println("<tr><th> Table name</th></tr>");
                                 while (resultTB.next())
                                 {	
                                	
                                     tblName = resultTB.getString(1);
                                     	
                                      out.println("<tr><td><b><a href='tbPrivilege?dbName="+dbName +"&tbName="+tblName +"&user="+user +"&pass="+pass +"&cbOption="+cbOption +"&GrantRevoke="+ GrantRevoke+"'>" + tblName + "</a></b></td></tr>");
                                    
                                  
                                        
                                  
                                       
                                         
                                 }  
                                 out.println("</table>");
                               //  mySWStm.close();
                                // myColStm.close();
                         }
          	
                        
                }
            	
            }
            
       //     else if (clPrivilege != null){
         
            	else if(cbOption.toLowerCase().contains("column")){
            		  HttpSession session = request.getSession(false);
                   	String user = (String)session.getAttribute("sysUser");
                   	String pass = (String)session.getAttribute("sysPass");
            	ResultSet resultDB = myDBStm.executeQuery("show databases");
                   
                   String dbName;
                   	

                   while (resultDB.next())
                   {
                           dbName = resultDB.getString("Database");
                           
                           //only get information inside the "moviedb" database
                           if (!dbName.equals("mysql") && !dbName.equals("information_schema") && !dbName.equals("performance_schema"))
                           {
                                   //first, we need to switch to this database;
                                   Statement mySWStm = connection.createStatement();
                                   mySWStm.execute("use " + dbName);
                                   //Create and execute an SQL statement to get all the table names in moviedb
                                   Statement myTBStm = connection.createStatement();
                                   ResultSet resultTB = myTBStm.executeQuery("show tables");
                                   //myStm.close();                       
                                   String tblName;
                                   ResultSet ColData;
                                   Statement myColStm = null;
                                   out.println("<div>");
                                   out.println("<H3>Database: "  + dbName+  "</H3>");
                                  
                                   while (resultTB.next())
                                   {	out.println("</br>");
                                	   out.println("<table border align = 'center'>");
                                       tblName = resultTB.getString(1);
                                       	
                                        out.println("<tr><b>Table name: " + tblName + "</b></tr>");
                                        out.println("<tr><td>Field Name </td><td>Field Name </td> <td>Null Allowed? </td> </tr> ");
                                    
                                          
                                           myColStm = connection.createStatement();
                                           // Create and execute an SQL statement to get all the column names for this table
                                           ColData = myColStm.executeQuery("describe "+tblName);
                                           //myStm.close();
                                           while (ColData.next())
                                           {		out.println("</tr> <td><a href='clPrivilege?dbName="+dbName +"&tbName="+tblName +"&clName="+ColData.getString(1) +"&user="+user +"&pass="+pass +"&cbOption="+cbOption +"&GrantRevoke="+ GrantRevoke+"'>" +ColData.getString(1) +"</a></td>" );
                                       		out.println("<td>"+ ColData.getString(2) +"</td>");
                                       		out.println("<td>"+ ColData.getString(3) +"</td>");
                                       		out.println("</tr>");
                                                
                                           }
                                           out.println("</table>");
                                           
                                   }  
                                 //  mySWStm.close();
                                  // myColStm.close();
                           }
            	
                          
                  }
            	}else{
            			HttpSession session = request.getSession(false);
                     	String user = (String)session.getAttribute("sysUser");
                     	String pass = (String)session.getAttribute("sysPass");
                     	ResultSet resultDB = myDBStm.executeQuery("show databases");
                     
                     String dbName;
                     	

                     while (resultDB.next())
                     {
                             dbName = resultDB.getString("Database");
                             
                             //only get information inside the "moviedb" database
                             if (!dbName.equals("mysql") && !dbName.equals("information_schema") && !dbName.equals("performance_schema"))
                             {
                                     //first, we need to switch to this database;
                                     Statement mySWStm = connection.createStatement();
                                     mySWStm.execute("use " + dbName);
                                     //Create and execute an SQL statement to get all the table names in moviedb
                                     Statement myTBStm = connection.createStatement();
                                     ResultSet resultTB = myTBStm.executeQuery("show procedure status");
                                     //myStm.close();                       
                                 
                                     String procedure;
                                     out.println("<H3>Database: "  + dbName+ "</H3>");
                                     out.println("<table border align = 'center'>");
                                     out.println("<tr><th> Table name</th></tr>");
                                     while (resultTB.next())
                                     {	
                                    	
                                        String owner = resultTB.getString("db");
                                        procedure = resultTB.getString("Name"); 
                                         if(owner.equals(dbName)){
                                          out.println("<tr><td><b><a href='pcPrivilege?dbName="+dbName +"&pcName="+procedure +"&user="+user +"&pass="+pass +"&cbOption="+cbOption +"&GrantRevoke="+ GrantRevoke+"'>" + procedure + "</a></b></td></tr>");
                                         } else {
                                        	 out.println("<tr><td><b> No procedure </b></td></tr>");                                   	
                                         }
                                      
                                            
                                      
                                           
                                             
                                     }  
                                     out.println("</table>");
                                   //  mySWStm.close();
                                    // myColStm.close();
                             }
              	
                            
                    }
            		
            	}
            }else{
            	 out.println("<BODY BGCOLOR='#42a2ce'><H1>Privileges for user </H1>");
            	 if(GrantRevoke.equals("GrantAll")){
            	HttpSession session = request.getSession(false);
            	String user = (String)session.getAttribute("sysUser");
            	String pass = (String)session.getAttribute("sysPass");
            	

            	ResultSet resultDB = myDBStm.executeQuery("grant all privileges on *.* to '" +  user + "'@'localhost' identified by '"+ pass+ "';");
            	resultDB = myDBStm.executeQuery("FLUSH PRIVILEGES");
            	resultDB = myDBStm.executeQuery("show grants for '"  +user + "'@'localhost';" );
            	  out.println("<table border align = 'Center'>");
              	while(resultDB.next()){
              		
              		out.println("<tr><td>" + resultDB.getString(1)+ "</td></tr>");
              	}
                  	
                  
                
              	out.println("</table>");
            }else if(GrantRevoke.equals("RevokeAll")){
            	HttpSession session = request.getSession(false);
            	String user = (String)session.getAttribute("sysUser");
            	String pass = (String)session.getAttribute("sysPass");
            	
            	ResultSet resultDB = myDBStm.executeQuery("revoke all privileges on *.* from '" +  user + "'@'localhost';");
            	resultDB = myDBStm.executeQuery("FLUSH PRIVILEGES");
            	resultDB = myDBStm.executeQuery("show grants for '"  +user + "'@'localhost';" );
            	  out.println("<table border align = 'Center'>");
              	while(resultDB.next()){
              		
              		out.println("<tr><td>" + resultDB.getString(1)+ "</td></tr>");
              	}
                  	
                  
                
              	out.println("</table>");
            	}
           
           }
            
  
             
              myDBStm.close();
              connection.close();
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
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
