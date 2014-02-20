package cartObject;

public class CartItem {
	String title;
	String movie_id;
	int quantity;
	String email;
	
		
	public CartItem(){
		movie_id = "";
		title ="";
		quantity =0;
		email = "";
	}
	public CartItem(String newTitle, String newId, int newQuantity){
		title = newTitle;
		movie_id = newId;
		quantity = newQuantity;
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
