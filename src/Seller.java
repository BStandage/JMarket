import java.util.ArrayList;

public class Seller extends User {

	private ArrayList<Item> items = new ArrayList<>();

	/**
	 * Creates a Seller Object
	 * 
	 * @param username
	 * @param passwordHash
	 * @param email
	 */
	public Seller(String username, String passwordHash, String email, String id) {

		super(username, passwordHash, email, id, "Seller");

	}

	/**
	 * Update item attributes
	 * 
	 * @param s:
	 *            Content to replace current item information
	 */
	public void updateItemInfo(Item i, String name, String desc, int quantity, double price, String purchaseTime,
			String status) {

		if (this.getID().equals(i.getID())) {

			for (Item item : items) {

				if (item.equals(i)) {

					item.setName(name);
					item.setDesc(desc);
					item.setQuantity(quantity);
					item.setPrice(price);
					item.setPurchaseTime(purchaseTime);
					item.setStatus(status);

				}
			}
		}
	}

	/**
	 * Add an Item object to the database
	 * 
	 * @param i:
	 *            Item object to be added
	 */
	public void addItem(Item i) {

		this.items.add(i);

	}

	/**
	 * Delete an Item object from the databse
	 * 
	 * @param i:
	 *            Item to be deleted
	 */
	public void deleteItem(Item i) {

		this.items.remove(i);

	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

	@Override
	void purchaseItem(Item i) {
		//
	}

}