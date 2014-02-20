
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
  <TITLE>Check out</TITLE>
  <link rel="stylesheet" href="css/demo.css" type="text/css" media="screen" />
</HEAD>

<BODY BGCOLOR="#42a2ce">

<H1 ALIGN="CENTER">Check out</H1>



<%@ page import="java.util.*" %>
<%@ page import="cartObject.CartItem" %>		
<% 	  if(session == null){
			response.sendRedirect("login.html");
		}
        ArrayList<CartItem> finalCart = (ArrayList) session.getAttribute ("cart");
      if(finalCart.isEmpty()){
				out.println("<br /><center><h3>"+"Checkout failed because the cart is empty"+"</h3></center>");
				}  
		
%>
</br>
<div ALIGN="CENTER">
<FORM ACTION="checkout" METHOD="GET">
<table>
						<tr>
                            <td colspan="3"><div style="color:#F60" align="center">Please fill out all of the following customer information.</div></td>
                          </tr>

						<tr>
                            <td width="15">&nbsp;</td>
                            <td width="145"><div align="left">Card Number:</div></td>
                            <td><input name="ccNumber" type="text" size="42" maxlength="100"  /></td>
                          </tr>
						  <tr>
                            <td width="15">&nbsp;</td>
                            <td width="145"><div align="left">Year:</div></td>
                            <td><input name="yearTxt" type="text" size="42" maxlength="100" value="YYYY-MM-DD" /></td>
                          </tr>
						  <tr>
                            <td width="15">&nbsp;</td>
                            <td width="145"><div align="left">First Name:</div></td>
                            <td><input name="fNameTxt" type="text" size="42" maxlength="100" /></td>
                          </tr>
						  <tr>
                            <td width="15">&nbsp;</td>
                            <td width="145"><div align="left">Last name:</div></td>
                            <td><input name="lNameTxt" type="text" size="42" maxlength="100" /></td>
                          </tr>
                          <tr><td></td>
                          <td><INPUT class="button green:hover" TYPE="SUBMIT" VALUE="Enter">
   							<input class="button green:hover" value="Reset Form" name="reset" type="reset" /></td>
                          </tr>
						  


</table> 
</FORM>

</div>

</br></br><div  ALIGN="CENTER"><a href="Home.jsp">Home</a> | <a href="logout">Log Out</a> | <a href="cart?Viewcart=1">My Cart</a></div>
</BODY>
</HTML>
