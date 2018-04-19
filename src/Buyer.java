import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Buyer extends User {

	private ArrayList<Item> items = new ArrayList<>();
	

	/**
	 * creates a Buyer object
	 * 
	 * @param username
	 * @param passwordHash
	 * @param email
	 */
	public Buyer(String username, String passwordHash, String email, String id) {
		super(username, passwordHash, email, id, "Buyer");
	}

	/**
	 * Purchase an Item object from the database
	 * 
	 * @param i:
	 *            Item to be purchased
	 */
	public void purchaseItem(Item i) {
		i.setQuantity(i.getQuantity() - 1);
		Item j = i.clone();
		this.items.add(j);
		
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());
		
		j.setPurchaseTime(timeStamp);
		System.out.println(Arrays.toString(this.items.toArray()));
	}

	@Override
	void addItem(Item i) {
		
	}

	@Override
	ArrayList<Item> getItems() {
		return this.items;
	}
}