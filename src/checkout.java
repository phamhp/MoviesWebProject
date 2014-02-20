/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import cartObject.CartItem;

public class checkout extends HttpServlet
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

     //   out.println("<HTML><HEAD><TITLE>Login</TITLE></HEAD>");
     //   out.println("<BODY><H1>Login</H1>");
      
        Connection dbcon = null;
        Statement statement = null;
        ResultSet rs =null;
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
        	//Class.forName("com.mysql.jdbc.Driver").newInstance();
        	InitialContext contxt = new InitialContext();
    		DataSource source = (DataSource)contxt.lookup("java:comp/env/jdbc/moviedb");
           dbcon = source.getConnection();
              // Declare our statement
          statement = dbcon.createStatement();
        
	      String ccNumber = request.getParameter("ccNumber");
	      String fName = request.getParameter("fNameTxt");
	      String lName = request.getParameter("lNameTxt");
	      String year = request.getParameter("yearTxt");
	     
	      HttpSession session = request.getSession (false);

          // Get the cart
          ArrayList<CartItem> finalCart = (ArrayList) session.getAttribute ("cart");
	      
              String query = "SELECT * from creditcards where id = '" + ccNumber + "' and " +
              		"first_name = '" + fName + "' AND last_name='"+ lName + "' AND expiration='" + year +"'";
            
              // Perform the query
             rs = statement.executeQuery(query);
             
              if (!rs.isBeforeFirst()) 
              {	
           // 	out.println("<HTML><HEAD><TITLE>Search movie search </TITLE></HEAD>");
            //    out.println("<BODY><H1>Thank you</H1>");
         		
            	 response.sendRedirect("http://localhost:8080/ProjectCS122B/Checkout.jsp"); 
              }
              else
              {
            	//  out.println ("<html><body><script>alert(" + "Thank you for purchasing"+");</script></body></html>");
            	//  response.sendRedirect("http://localhost:8080/ProjectCS122B/Home.jsp");
            	  out.println("<HTML><HEAD>" +
                  		"<TITLE>Payment</TITLE>" +
                  		"<script type='text/javascript'></script>"+        		
                  		"</HEAD>");
                  out.println("<BODY BGCOLOR=' #42a2ce'><H3>Thank you </H3></br>");
                  out.println("<div ALIGN='CENTER'>  <a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a></div>");
                  String email = (String)session.getAttribute("email");
                  String query2 = "select id from customers where email ='"+ email +"'";
                  int id=0;
                  rs = statement.executeQuery(query2);	 
                  while(rs.next()){
                	  id = rs.getInt("id");
                  }
                  
                  
                  java.util.Calendar cal = java.util.Calendar.getInstance();
                  java.util.Date utilDate = cal.getTime();
                  java.sql.Date sqlDate = new Date(utilDate.getTime());
                  
                  String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(sqlDate);
                  int j=0;
                  for(int i=0;i<finalCart.size(); ++i){
                	  String query4 = "INSERT INTO sales(customer_id,movie_id,sale_date) VALUE ('"+id+"','"+finalCart.get(i).getMovie_id()+"','"+modifiedDate+"');";   
                    j=	statement.executeUpdate(query4);
                 // if(Integer.parseInt(finalCart.get(i).getMovie_id()) == id){
                	  	finalCart.remove(i);
                	  	session.setAttribute("cart",finalCart);
               //   }
                                                           
                	 
                  }
                  Statement statement2 = dbcon.createStatement();
                  String query6 = "select * from sales inner join movies where movies.id = sales.movie_id order by sales.id DESC;";
                  rs = statement2.executeQuery(query6);
                  
                  out.println("<div ALIGN='CENTER'>");
                  out.println("<TABLE border>");
                  out.println("<tr><td> <div align='left'>Sales ID:</div></td>" +
                          "<td><div align='left'>Purchase Date:</div></td> <td><div align='left'>Movie Purchased:</div></td></tr>");
                 
                  while(rs.next()){
                	  out.println("<tr><td> <div align='left'>"+ rs.getInt("id")+"</div></td>" +
                              "<td>" + rs.getString("sale_date") + "</td>");
                      out.println("<td> <div align='left'>"+ rs.getString("title") +"</div></td></tr>");
                	  
                  }
                
              	  out.println("</TABLE>");
               
                
                  out.println("</BODY>");
                  out.println("</HTML>");
                  
                  if(j!=0){
                      String query5 = "delete from shoppingcart where email ='" + email+ "';";
                      statement2.executeUpdate(query5);
                     
                      }
              }
              
              rs.close();
              statement.close();
              dbcon.close();
              statement = null;
              dbcon = null;
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
                            "<P>SQL error in doPost: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }finally {
                try { if(null!=rs)rs.close();} 
                catch (SQLException e) 
                {e.printStackTrace();}
                try { if(null!=statement)statement.close();}
                catch (SQLException e) 
                {e.printStackTrace();}
                try { if(null!=dbcon)dbcon.close();} 
                catch (SQLException e) 
                {e.printStackTrace();}
            }
        
         out.close();
       
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
        {
    	doGet(request, response);
        }
}