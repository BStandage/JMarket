import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Item implements Serializable {

	private Random random = new Random();
	private String id;
	private String sellerID;
	private String name;
	private String desc;
	private int quantity;
	private double price;
	private String purchaseTime;
	private String status;

	/**
	 * Constructor for an Item object. the id attribute will be assigned
	 * randomly; the purchaseTime attribute will not be set until this item has
	 * been purchased; and the status attribute will be set to "in stock"
	 * automatically.
	 * 
	 * @param sellerID:
	 *            ID# of the Seller who is selling this item
	 * @param name:
	 *            Name of this item
	 * @param desc:
	 *            Description of this item
	 * @param quantity:
	 *            Initial quantity in stock
	 * @param price:
	 *            Initial price
	 */
	public Item(String name, String desc, int quantity, double price, String sellerID) {
		
		this.id = "01-" + Integer.toString(random.nextInt(89999) + 10000);
		this.name = name;
		this.desc = desc;
		this.quantity = quantity;
		this.price = price;
		this.sellerID = sellerID;
		
	}

	/**
	 * @return Name of the item
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Give item a new name
	 * 
	 * @param s:
	 *            Name to be set
	 */
	public void setName(String s) {
		this.name = s;
	}
	
	
	/**
	 * @return Item ID#
	 */
	public String getID() {
		return this.id;
	}
	
	public String getSellerID() {
		return this.sellerID;
	}
	
	public void setSellerID(String s) {
		this.sellerID = s;
	}

	/**
	 * @return Description of the item
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * Set a new description
	 * 
	 * @param s:
	 *            New description to be set
	 */
	public void setDesc(String s) {
		this.desc = s;
	}

	/**
	 * @return Amount of item in stock
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Change the quantity of the item
	 * 
	 * @param q:
	 *            new quantity
	 */
	public void setQuantity(int q) {
		this.quantity = q;
	}

	/**
	 * Give the item a new price
	 * 
	 * @param price:
	 *            new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return Price of this item
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * @return The time at which this item was purchased
	 */
	public String getPurchaseTime() {
		return this.purchaseTime;
	}

	/**
	 * Log when this item was purchased
	 * 
	 * @param s:
	 *            Purchase time of the item
	 */
	public void setPurchaseTime(String s) {
		this.purchaseTime = s;
	}

	/**
	 * @return Availability status of the item
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Change the status of the item
	 * 
	 * @param s:
	 *            new status
	 */
	public void setStatus(String s) {
		this.status = s;
	}
	
	/**
	 * Create a shallow copy of an item
	 */
	public Item clone() {
		Item newItem = new Item(this.getName(), this.getDesc(), this.getQuantity(), this.getPrice(), this.getSellerID());
		newItem.id = this.getID();
		newItem.sellerID = this.getSellerID();
		return newItem;
	}
	
	public String toString() {
		return "Name: " + this.getName() + "\nDesc: " + this.getDesc() + "\nQ: " + this.getQuantity() + "\nPrice: " + this.getPrice()
		+ "\nID: " + this.getID() + "\nSellerID: " + this.getSellerID();
	}
}
