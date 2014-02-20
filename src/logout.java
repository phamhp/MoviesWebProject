/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import cartObject.CartItem;


public class logout extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
    	if (request.getSession(false) == null)
    	{
    		response.sendRedirect("http://localhost:8080/Project/Login.html");
    	}
    	
    	
    	  
    	InitialContext contxt = null;
		try {
			contxt = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataSource source = null;
		try {
			source = (DataSource)contxt.lookup("java:comp/env/jdbc/moviedb");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

       
        Connection dbcon = null;
        Statement statement = null;
        ResultSet rs =null;
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
       

        out.println("<HTML><HEAD>" +
        		"<TITLE>Check out</TITLE>" +
        		"<script type='text/javascript'></script>"+        		
        		"</HEAD>");
        out.println("<BODY BGCOLOR=' #42a2ce'><H1>Check out</H1></br>");

        out.println("<div ALIGN='CENTER'>  <a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href=''> Logout </a>| <a href=''> Checkout </a></div>");
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              

               dbcon = source.getConnection();
              // Declare our statement
               statement = dbcon.createStatement();
               HttpSession session = request.getSession (false);
              
               // Get the cart
               ArrayList<CartItem> finalCart = (ArrayList) session.getAttribute ("cart");

               // if the session is new, the cart won't exist.
               if (finalCart != null)
               {
            	   String title="";
            	   String movie_id="";
            	   String email="";
            	   int qty=0;
                   for(int i=0; i<finalCart.size(); ++i){
                	   
                	title=   finalCart.get(i).getTitle();
                	movie_id = finalCart.get(i).getMovie_id();
                	qty = finalCart.get(i).getQuantity();
                	email =(String)session.getAttribute("email");
                	String query = "INSERT INTO shoppingcart(Title,movie_id,email,quantity) VALUE ('"+title+"','"+movie_id+"','"+email+"',"+qty+");";   
                	statement.executeUpdate(query);
                	
                   }
                   session.invalidate();
               }
          response.sendRedirect("http://localhost:8080/ProjectCS122B/login.html");
        
          statement.close();
          dbcon.close();
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

}