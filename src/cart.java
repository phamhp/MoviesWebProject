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


public class cart extends HttpServlet
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
    		response.sendRedirect("http://localhost:8080/ProjectCS122B/Login.html");
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

        out.println("<div ALIGN='CENTER'> <a href='javascript:history.back()'>" + "Previous " + "</a>|<a href='http://localhost:8080/ProjectCS122B/Home.jsp'> Home </a>|<a href='logout'> Logout </a>| <a href='http://localhost:8080/ProjectCS122B/Checkout.jsp'> Checkout </a></div>");
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
               if (finalCart == null)
               {
                  finalCart = new ArrayList<CartItem> ();
                  session.setAttribute ("cart", finalCart);
               }

          
          String movieid = request.getParameter("movieid");
          String qty = request.getParameter("qty");
          String update = request.getParameter("update");
          String remove = request.getParameter("delete");
          
       // Perform the query
          String viewCart = request.getParameter("Viewcart"); 
          
          double total = 0;
          double PRICE = 20.00;
          
          if(viewCart!= null){
        	  String email = (String)session.getAttribute("email"); 
        	  if(finalCart.isEmpty()){
        	
        	  
        	 
        	  
              String query2 = "select * from shoppingcart where email='" +email+"';";
              rs = statement.executeQuery(query2);
              String dbTitle="";
              String dbMovieId = "";
              int dbQty;
             
              rs = statement.executeQuery(query2);
              while(rs.next()){
            	CartItem cart = new CartItem();
           	   dbTitle = rs.getString("Title");
           	   dbMovieId = rs.getString("movie_id");
           	   dbQty = rs.getInt("quantity");
           	   
           	   cart.setMovie_id(dbMovieId);
           	   cart.setTitle(dbTitle);
           	   cart.setQuantity(dbQty);
           	   cart.setEmail(email);
    
           	   finalCart.add(cart);
           	  
              	}
              }
              
            
              for(int i=0;i<finalCart.size(); ++i){
            	  	total += finalCart.get(i).getQuantity() * PRICE;
            	  
              }
              
             out.println("<div ALIGN='CENTER'>");
             out.println("<TABLE border>");
             out.println("<tr><td> Title </td><td>Price</td><td>Quantiy</td></tr>");
              for(int i =0 ;i < finalCart.size(); ++i){
              out.println("<tr>" + 
                          "<td><a href='getMovie?movieId="+finalCart.get(i).getMovie_id()+"'>" + finalCart.get(i).getTitle() + "</a></td>" );
              out.println( "<td>" + PRICE+ "$"+ "</td>"); 
                       //   "<td><input type='text' name='quantityTxt' value='" + finalCart.get(i).getQuantity()+"'>"   + "</td>" +
                 out.println("<td><form action='cart' method='get' name='cartOperation' id='cartOperation'> ");
                out.println( "<input name='movieid' type='hidden' value='"+finalCart.get(i).getMovie_id()+"'/>");
              out.println( "<input name='qty' type='text' size='7' maxlength='7' value='"+ finalCart.get(i).getQuantity()+"'/>");
             
             out.println(" &nbsp;&nbsp;&nbsp;&nbsp");
             out.println( "<input value='Update' name='update' type='submit' />");
              out.print("&nbsp");
             out.println( "<input value='Remove' name='delete' type='submit' />");
            out.println("</form> </td></tr>");
            
            
                       
               
            //              "<td><a href='cart?quantity=abc'>" + "Update cart" + "</a></td>"+ 	 
             
            }
              String query3 = "delete from shoppingcart where email ='" + email+ "';";
              statement.executeUpdate(query3);
              
          }else{
        	  String query = "select * from movies where id = '" + movieid + "'";
              rs = statement.executeQuery(query);
      //    cart.setEmail(email);
          String title = null;
          while(rs.next()){
         	 title = rs.getString("title");
         	 
          }
      
       
       
     

          
          
         // ArrayList<CartItem> finalCart = new ArrayList<CartItem>();
          
         
         // remeber to create for loop to check the id and add the quantity by 1; 
      
          boolean found =false;
          for(int i=0;i<finalCart.size();++i){
        	  if(finalCart.get(i).getMovie_id().equals(movieid)){
        		  if(update==null && ( remove == null)){
        		  finalCart.get(i).setQuantity(finalCart.get(i).getQuantity() + 1);
        		  }else if(update != null && (remove == null)){
        			  finalCart.get(i).setQuantity(Integer.parseInt(qty));
        		  }else if(remove.equals("Remove")){
        			  finalCart.remove(i);
        			  
        		  }
        		found = true;
        	  }
        	  
          }
          if(found == false){
        	  CartItem cart = new CartItem();
        	  cart.setMovie_id(movieid);
        	  cart.setTitle(title);
        	  cart.setQuantity(Integer.parseInt(qty));
              finalCart.add(cart);
          }
          for(int i=0;i<finalCart.size(); ++i){
      	  	total += finalCart.get(i).getQuantity() * PRICE;
      	  
        }
       out.println("<div ALIGN='CENTER'>");
       out.println("<TABLE border>");
       out.println("<tr><td> Title </td><td>Price</td><td>Quantiy</td></tr>");
        for(int i =0 ;i < finalCart.size(); ++i){
        out.println("<tr>" + 
                    "<td><a href='getMovie?movieId="+finalCart.get(i).getMovie_id()+"'>" + finalCart.get(i).getTitle() + "</a></td>" );
        out.println( "<td>" + PRICE+ "$"+ "</td>"); 
                 //   "<td><input type='text' name='quantityTxt' value='" + finalCart.get(i).getQuantity()+"'>"   + "</td>" +
           out.println("<td><form action='cart' method='get' name='cartOperation' id='cartOperation'> ");
          out.println( "<input name='movieid' type='hidden' value='"+finalCart.get(i).getMovie_id()+"'/>");
        out.println( "<input name='qty' type='text' size='7' maxlength='7' value='"+ finalCart.get(i).getQuantity()+"'/>");
       
       out.println(" &nbsp;&nbsp;&nbsp;&nbsp");
       out.println( "<input value='Update' name='update' type='submit' />");
        out.print("&nbsp");
       out.println( "<input value='Remove' name='delete' type='submit' />");
      out.println("</form> </td></tr>");
      
      
                 
         
      //              "<td><a href='cart?quantity=abc'>" + "Update cart" + "</a></td>"+
                   
       	 
       
        
        }
          
      } // else 
        
        
         
          out.println("<tr><td>&nbsp;</td><td colspan='2'><div align='right'>Grand Total: $"+ total+ "</div></td> </tr>");
     
          
          out.println("</TABLE>");
          out.println("</div>");
         
          out.println("</BODY>");
          out.println("</HTML>");
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