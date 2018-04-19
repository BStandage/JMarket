import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Marketplace {

	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Item> items = new ArrayList<Item>();

	private final String DATA_FILE = "data.bin";

	public Marketplace() {
		
		loadData();
		
	}

	/**
	 * Add a User to the Marketplace database
	 * 
	 * @param u:
	 *            User object to be added
	 */
	public void addUser(User u) {
		this.users.add(u);
	}

	/**
	 * Delete a User from the database
	 * 
	 * @param u:
	 *            User object to be deleted
	 */
	public void deleteUser(User u) {
		this.users.remove(u);
	}
	
	public User getUser(String name) {
		for (User u : this.users) {
			if (name.equals(u.getUsername())) {
				return u;
			}
		}
		
		return null;
	}
	
	public Seller getSeller(String id) {
		for (User u : this.users) {
			if (u.getType().equals("Seller")) {
				if (u.getID().equals(id)) {
					return (Seller) u;
				}
			}
		}
		
		return null;
	}
	
	public Item searchItem(String param, String value) {
		
		if (param.equals("Name")) {
			for (Item i : this.items) {
				if (i.getName().equals(value)) {
					return i;
				}
			}
		} else if (param.equals("ID#")) {
			for (Item i : this.items) {
				if (i.getID().equals(value)) {
					return i;
				}
			}
		}
		
		return null;
		
	}
	
	public User searchUsers(String param, String value) {
		
		if (param.equals("Name")) {
			for (User u : this.users) {
				if (u.getUsername().equals(value)) {
					return u;
				}
			}
		} else if (param.equals("ID#")) {
			for (User u : this.users) {
				if (u.getID().equals(value)) {
					return u;
				}
			}
		}
		
		return null;
		
	}
	

	/**
	 * Checks if the password matches the saved password
	 * 
	 * @param s:
	 *            Password to check
	 */
	public boolean checkPassword(String s) {
		return false;
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

	/**
	 * @return An ArrayList<User> containing all users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * @return An ArrayList<Item> contianing all items
	 */
	public ArrayList<Item> getItems() {
		return this.items;
	}

	/**
	 * Load data from file
	 */
	public void loadData() {
		try {
			FileInputStream fis = new FileInputStream(DATA_FILE);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (ArrayList<User>) ois.readObject();
			fis.close();
			ois.close();
		} catch (IOException e) {
			System.out.println("IO");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveData() {
		try {
			PrintWriter writer = new PrintWriter(DATA_FILE);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find it");
		}
		try {
			FileOutputStream fos = new FileOutputStream(DATA_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			fos.close();
			oos.close();
		} catch (IOException e) {
			System.out.println("IO");
		}
	}

}
