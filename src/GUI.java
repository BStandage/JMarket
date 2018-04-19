import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	
	// A lot of these objects are modified inside of blocks and classes, so they're all global.
	
	public static Random random = new Random();
	
	// Style rules
	private final Color BG = new Color(20, 20, 60);
	private final Color BUTTON = new Color(45, 45, 90);
	private final Font DEFAULT = new Font("Segoe UI", Font.PLAIN, 20);

	// Panels
	private JPanel homeScreen;
	private JPanel createAccount;
	private JPanel loginScreen;
	private JPanel mainScreen;
	
	// JTable column headers
	private String[] columns = {"Item", "Price", "Stock"};
	
	// Currently selected item
	private Item selectedItem;
	
	// Needed components
	private JTextField usernameInput;
	private JTextField loginInput;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	private JTextField emailInput;
	private JButton cancelButton;
	private JLabel pwdLabel;
	private JTextField pwdInput;
	private JTextField pwdInput2;
	private JTextField pwdLoginInput;
	private JLabel lblIAmA;
	private JRadioButton consumerButton;
	private JRadioButton merchantButton;
	private JRadioButton adminButton;
	private JButton purchaseButton;
	private JButton historyButton;
	private JButton addItemButton;
	private JButton editButton;
	
	private JTable table;
	
	private DefaultTableModel model;
	private JTextPane descPane;
	private JButton logOutButton;
	private BufferedImage xs;
	private BufferedImage small;
	private BufferedImage large;
	private BufferedImage logoutImg;
	
	private ActionListener search;
	
	// Currently logged in user
	private User currentUser = null;
	
	// JTable ata
	private Object[][] data;
	
	// Marketplace
	private Marketplace m = new Marketplace();
	
	// Previously selected item - needed for purchase function
	private int prev = 0;

	// Run GUI
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					
					
					
					GUI frame = new GUI();
					
					// setup
					frame.setContentPane(frame.homeScreen);
					frame.setupAccScreen();
					frame.setupHomeScreen();
					frame.setTitle("JMarket");
					frame.setIconImage(new ImageIcon("K:\\Downloads\\jm.ico").getImage());
					frame.setVisible(true);
					
					// Ask user if they want to exit
					frame.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowActivated(WindowEvent e) {
							try {
								UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
					        if (reply == JOptionPane.YES_OPTION) {
					        	frame.m.saveData();
					            System.exit(0);
					        }
					    }
					});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	// Set up window
	public GUI() {
		
		setBounds(100, 100, 1300, 800);
		setupHomeScreen();
		//setupMainScreen();
		
	}
	
	/**
	 * Switch the current panel to the home screen
	 */
	private void loadHomeScreen() {
		this.getContentPane().removeAll();
		getContentPane().add(this.homeScreen);
		this.setContentPane(this.homeScreen);
		this.validate();
		this.repaint();
		this.setVisible(true);
	}
	
	/**
	 * Switch the current panel to the create account screen
	 */
	private void loadAccScreen() {
		this.getContentPane().removeAll();
		getContentPane().add(this.createAccount);
		this.setContentPane(this.createAccount);
		this.validate();
		this.repaint();
		this.setVisible(true);
	}
	
	/**
	 * Switch the current panel to the login screen
	 */
	public void loadLoginScreen() {
		this.getContentPane().removeAll();
		getContentPane().add(this.loginScreen);
		this.setContentPane(this.loginScreen);
		this.validate();
		this.repaint();
		this.setVisible(true);
	}
	
	/**
	 * Switch the current panel to the main screen
	 */
	public void loadMainScreen() {
		this.getContentPane().removeAll();
		getContentPane().add(this.mainScreen);
		this.setContentPane(this.mainScreen);
		this.validate();
		this.repaint();
		this.setVisible(true);
	}
	
	/**
	 * Create a user from data entered on the create account screen
	 */
	public void createUser() {
		
		// collect data
		String username = usernameInput.getText();
		String email = emailInput.getText();
		String password = pwdInput.getText();
		String passwordConfirm = pwdInput2.getText();
		
		// make sure passwords match
		if (!password.equals(passwordConfirm)) {
			JOptionPane.showMessageDialog(this, "Passwords do not match", "Alert", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		String type = "";
		
		JRadioButton[] buttons = {consumerButton, merchantButton, adminButton};
		
		for (JRadioButton r : buttons) {
			if (r.isSelected()) {
				type = r.getText();
			}
		}
		
		// Create a User object and add it to the Marketplace's users
		System.out.println("Added user:");
		if (type.equals(" Consumer")) {
			String idn = "03-" + Integer.toString(random.nextInt(89999) + 10000);
			Buyer b = new Buyer(username, password, email, idn);
			m.addUser(b);
			System.out.println(b);
		} else if (type.equals(" Merchant")) {
			String idn = "05-" + Integer.toString(random.nextInt(89999) + 10000);
			Seller s = new Seller(username, password, email, idn);
			m.addUser(s);
			System.out.println(s);
		} else if (type.equals(" Administrator")) {
			String idn = "07-" + Integer.toString(random.nextInt(89999) + 10000);
			Admin a = new Admin(username, password, email, idn);
			m.addUser(a);
			System.out.println(a);
		}

		// Confirm creation
		JOptionPane.showMessageDialog(this, "User has been added.", "Alert", JOptionPane.PLAIN_MESSAGE);
		
		m.saveData();
	}
	
	/**
	 * Find a Seller given their ID#
	 * @param id - seller'd ID#
	 * @return Seller
	 */
	public User getSeller(String id) {
		// Search through users and return a Seller if the ID matches
		for (User u : m.getUsers()) {
			if (u.getID().equals(id)) {
				return u;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate user credentials and log in
	 */
	public void login() {
		
		// Collect username and password entered
		String username = loginInput.getText();
		String password = pwdLoginInput.getText();
		
		// Alert if username entered does not exist
		if (m.getUser(username) == null) {
			JOptionPane.showMessageDialog(this, "User \"" + username + "\" does not exist", "Alert", JOptionPane.PLAIN_MESSAGE);
		} else {
			// Verify password
			try {
				if (!m.getUser(username).getPasswordHash().equals(m.getUser(username).hashPassword(password))) {
					JOptionPane.showMessageDialog(this, "Incorrect password", "Alert", JOptionPane.PLAIN_MESSAGE);
				} else {
					// Log user in
					currentUser = m.getUser(username);
					System.out.println("Logged in " + currentUser.getUsername());
					System.out.println(currentUser.getType());
				}
			} catch (NullPointerException e) {
				
			}
		}
	}
	
	/**
	 * Log out current user and return to home screen
	 */
	public void logout() {
		// Confirm user wants to log out
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log out", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	setupHomeScreen();
        	loadHomeScreen();
        	currentUser = null;
        }
        else {
        	//
        }
	}
	
	/**
	 *	Listener for switching to account creation screen
	 *	Used for "create account" button on home screen
	 */
	class CreateUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setupAccScreen();
			loadAccScreen();
		}
	}
	
	/**
	 * Listener for returning to home screen
	 */
	
	class HomeScreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setupHomeScreen();
			loadHomeScreen();
		}
	}
	
	/**
	 * Listener for going to login screen
	 */
	class LoginScreenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setupLoginScreen();
			loadLoginScreen();
		}
	}
	
	/**
	 * Clear out the list of items and re-populate it
	 */
	public void loadItems() {
		m.getItems().clear();
		for (User u : m.getUsers()) {
			if (u.getType().equals("Seller")) {
				for (Item i : u.getItems()) {
					m.getItems().add(i);
				}
			}
		}
	}
	
	/**
	 * Listen for when the login page submit button is pressed
	 */
	class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			login();
			loadItems();
			System.out.println(m.getItems());
			model = new DefaultTableModel(data, columns);
			table = new JTable(model);
			table.setModel(model);
			setupMainScreen();
			loadMainScreen();
		}
	}
	
	/**
	 * Listen for list items being selected. Store their indexes for button use and display info
	 */
	class ListSelectListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				
				if (currentUser.getType().equals("Buyer")) {
					purchaseButton.setEnabled(true);
				}
				
				int index = table.getSelectionModel().getMaxSelectionIndex();
				if (index != -1) {
					prev = index;
				}
				
				System.out.println("INDEX: " + index);
				System.out.println("PREV: " + prev);
				
				String item = (String) table.getModel().getValueAt(prev, 0);
				
				selectedItem = itemFromString(item.substring(4));
				
				descPane.setText("Name:\r\n" + selectedItem.getName() + "\r\n\r\nDescription:\r\n" + selectedItem.getDesc()
				+ "\r\n\r\nItem ID:\r\n" + selectedItem.getID() + "\r\n\r\nSold & shipped by:\r\n"
				+ m.getSeller(selectedItem.getSellerID()).getUsername() + "\n"
				+ m.getSeller(selectedItem.getSellerID()).getEmail()
				+ "\nID#" + selectedItem.getSellerID());
				
				
				
				
			}
		}
	}
	
	/**
	 * Listen for add item button to be pressed, then bring up the add item dialog
	 */
	class AddItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AddItemDialog dialog = new AddItemDialog();
			dialog.setModal(true);
			dialog.setVisible(true);
			if (dialog.getName() != null) {
				Item i = new Item(dialog.getName(), dialog.getDesc(), dialog.getQ(), dialog.getPrice(), currentUser.getID());
				currentUser.addItem(i);
				m.addItem(i);
				System.out.println(i);
				model.addRow(itemToArray(i));
				model.fireTableDataChanged();
				table.setModel(model);
			} else {
				return;
			}
			
		}
	}
	
	/**
	 * Listen for Edit item button to be clicked, then bring up the dialog and store the info entered
	 */
	class EditItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EditItemDialog dialog = new EditItemDialog(selectedItem);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (dialog.getName() != null) {
				int currentItemIndex = m.getItems().indexOf(selectedItem);
				selectedItem.setName(dialog.getName());
				selectedItem.setDesc(dialog.getDesc());
				selectedItem.setQuantity(dialog.getQ());
				selectedItem.setPrice(dialog.getPrice());
				
				m.getItems().set(currentItemIndex, selectedItem);
				
				for (int i = 0; i < 3; i++) {
					model.setValueAt(itemToArray(selectedItem)[i], prev, i);
				}
				
				model.fireTableDataChanged();
				table.setModel(model);
			} else {
				return;
			}
		}
	}
	
	/**
	 * Listen for the item search button to be pressed
	 */
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SearchDialog dialog = new SearchDialog(m);
			dialog.setModal(true);
			dialog.setVisible(true);
			
			Item match = m.searchItem(dialog.getParam(), dialog.getValue());
			
			if (match != null) {
				
				for (int i = 0; i < table.getRowCount(); i++) {
					String name = (String) table.getModel().getValueAt(i, 0);
					if (match.equals(itemFromString(name.substring(4)))) {
						table.setRowSelectionInterval(i, i);
						table.scrollRectToVisible(table.getCellRect(i, 0, true));
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "No matches found", "Alert", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	class SearchUserButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SearchUserDialog dialog = new SearchUserDialog(m);
			dialog.setModal(true);
			dialog.setVisible(true);
			
			User match = m.searchUsers(dialog.getParam(), dialog.getValue());
			
			if (match != null) {
				
				UserInfoDialog uid = new UserInfoDialog(match, m);
				
			} else {
				JOptionPane.showMessageDialog(null, "No matches found", "Alert", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	class HistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HistoryDialog hd = new HistoryDialog((Buyer) currentUser);
		}
	}
	
	class SubmitAccListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createUser();
			setupLoginScreen();
			loadLoginScreen();
		}
	}
	
	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			logout();
		}
	}
	
	public void oos() {
		JOptionPane.showMessageDialog(this, "Item is out of stock. Please contact the seller or try again later.", "Stock alert", JOptionPane.PLAIN_MESSAGE);
	}
	
	class PurchaseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			table.requestFocus();
			User u = (Buyer) currentUser;
			if (selectedItem.getQuantity() == 0) {
				oos();
			} else {
				int reply = JOptionPane.showConfirmDialog(null, "Purchase \"" + selectedItem.getName() + "\" for $" + selectedItem.getPrice() + "?", 
						"Confirm purchase", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	u.purchaseItem(selectedItem);
					model.setValueAt(itemToArray(selectedItem)[2], prev, 2);
					model.fireTableDataChanged();
					table.setModel(model);
					table.repaint();
		        } else {
		        	
		        }
				
			}
		}
		
	}
	
	public void setFieldLabelLayout(JLabel l) {
		l.setForeground(Color.WHITE);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	}
	
	public void setLabelLayout(JLabel l) {
		l.setForeground(Color.WHITE);
		l.setFont(DEFAULT);
	}
	
	public void setButtonLayout(JButton b) {
		b.setForeground(new Color(240, 248, 255));
		b.setFont(DEFAULT);
		b.setBackground(BUTTON);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
	}
	
	public void setRadioButtonLayout(JRadioButton r) {
		r.setForeground(Color.WHITE);
		r.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		r.setBackground(new Color(20, 20, 60));
		r.setBorderPainted(false);
		r.setFocusPainted(false);
	}
	
	public void setTextFieldLayout(JTextField t, String s) {
		t.setForeground(Color.WHITE);
		t.setCaretColor(Color.WHITE);
		t.setToolTipText(s);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		t.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
		t.setBackground(BUTTON);
	}
	
	public String[] itemToArray(Item i) {
		String[] result = new String[3];
		result[0] = "    " + i.getName();
		result[1] = "$" + Double.toString(i.getPrice());
		if (Double.toString(i.getPrice()).split("\\.")[1].equals("0")) {
			result[1] += "0";
		}
		result[2] = Integer.toString(i.getQuantity()) + " x     ";
		return result;
	}
	
	public Item itemFromString(String s) {
		if (currentUser.getType().equals("Seller")) {
			for (Item i : currentUser.getItems()) {
				if (i.getName().equals(s)) {
					return i;
				}
			}
		} else if (currentUser.getType().equals("Buyer") || currentUser.getType().equals("Admin")) {
			for (Item i : m.getItems()) {
				if (i.getName().equals(s)) {
					return i;
				}
			}
		}
		return null;
	}
	
	public void setupHomeScreen() {
		
		homeScreen = new JPanel();
		homeScreen.setBackground(BG);
		homeScreen.setBorder(new EmptyBorder(5, 5, 5, 5));
		homeScreen.setLayout(null);

		ActionListener CreateAcc = new CreateUserListener();
		JButton signUpButton = new JButton("Create an account");
		signUpButton.setBounds(413, 477, 208, 47);
		signUpButton.addActionListener(CreateAcc);
		signUpButton.setForeground(new Color(240, 248, 255));
		signUpButton.setFont(DEFAULT);
		signUpButton.setBackground(BUTTON);
		signUpButton.setBorderPainted(false);
		signUpButton.setFocusPainted(false);
		
		
		homeScreen.add(signUpButton);
		
		ActionListener LoginListener = new LoginScreenListener();
		JButton loginButton = new JButton("Log in");
		loginButton.setBounds(679, 477, 208, 47);
		loginButton.addActionListener(LoginListener);
		loginButton.setForeground(new Color(240, 248, 255));
		loginButton.setFont(DEFAULT);
		loginButton.setBackground(BUTTON);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		
		homeScreen.add(loginButton);
		
		try {
			large = ImageIO.read(new File("Jmarket.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel logoLabel = new JLabel(new ImageIcon(large));
		logoLabel.setBounds(359, 217, 581, 202);
		homeScreen.add(logoLabel);
	}
	
	public void setupAccScreen() {
		createAccount = new JPanel();
		createAccount.setBackground(BG);
		createAccount.setBorder(new EmptyBorder(5, 5, 5, 5));
		createAccount.setLayout(null);
		
		ActionListener CreateAccountListener = new SubmitAccListener();
		JButton createAccSubmitButton = new JButton("Submit");
		createAccSubmitButton.setBounds(987, 616, 158, 47);
		createAccSubmitButton.addActionListener(CreateAccountListener);
		setButtonLayout(createAccSubmitButton);
		
		createAccount.add(createAccSubmitButton);
		
		JLabel lblCreateAnAccount = new JLabel("Create an account");
		lblCreateAnAccount.setForeground(new Color(255, 255, 255));
		lblCreateAnAccount.setBackground(new Color(255, 255, 255));
		lblCreateAnAccount.setFont(new Font("Segoe UI", Font.PLAIN, 38));
		lblCreateAnAccount.setBounds(751, 57, 349, 76);
		createAccount.add(lblCreateAnAccount);
		
		try {
			small = ImageIO.read(new File("Jmarket-small.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel logoLabelSmall = new JLabel(new ImageIcon(small));
		logoLabelSmall.setBounds(116, 306, 454, 129);
		createAccount.add(logoLabelSmall);
		
		usernameInput = new JTextField(20);
		usernameInput.setBounds(868, 146, 277, 47);
		setTextFieldLayout(usernameInput, "Username");
		createAccount.add(usernameInput);
		
		usernameLabel = new JLabel("Username");
		setFieldLabelLayout(usernameLabel);
		usernameLabel.setBounds(751, 159, 78, 22);
		createAccount.add(usernameLabel);
		
		emailLabel = new JLabel("Email address");
		emailLabel.setBounds(725, 214, 104, 22);
		setFieldLabelLayout(emailLabel);
		createAccount.add(emailLabel);
		
		emailInput = new JTextField(20);
		emailInput.setBounds(868, 204, 277, 47);
		setTextFieldLayout(emailInput, "Email address");
		createAccount.add(emailInput);
		
		ActionListener homeScreen = new HomeScreenListener();
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(795, 616, 158, 47);
		setButtonLayout(cancelButton);
		cancelButton.addActionListener(homeScreen);
		createAccount.add(cancelButton);
		
		pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(758, 274, 71, 22);
		setFieldLabelLayout(pwdLabel);
		createAccount.add(pwdLabel);
		
		pwdInput = new JPasswordField(20);
		pwdInput.setBounds(868, 262, 277, 47);
		setTextFieldLayout(pwdInput, "Password");
		createAccount.add(pwdInput);
		
		pwdInput2 = new JPasswordField(20);
		pwdInput2.setBounds(868, 320, 277, 47);
		setTextFieldLayout(pwdInput2, "Confirm password");
		createAccount.add(pwdInput2);
		
		JLabel pwdLabel2 = new JLabel("Confirm password");
		pwdLabel2.setBounds(690, 332, 142, 22);
		setFieldLabelLayout(pwdLabel2);
		createAccount.add(pwdLabel2);
		
		lblIAmA = new JLabel("I am a...");
		lblIAmA.setForeground(Color.WHITE);
		lblIAmA.setFont(new Font("Segoe UI", Font.PLAIN, 26));
		lblIAmA.setBackground(Color.WHITE);
		lblIAmA.setBounds(751, 400, 110, 47);
		createAccount.add(lblIAmA);
		
		consumerButton = new JRadioButton(" Consumer");
		consumerButton.setBounds(807, 461, 187, 25);
		setRadioButtonLayout(consumerButton);
		createAccount.add(consumerButton);
		
		merchantButton = new JRadioButton(" Merchant");
		merchantButton.setBounds(807, 501, 187, 25);
		setRadioButtonLayout(merchantButton);
		createAccount.add(merchantButton);
		
		adminButton = new JRadioButton(" Administrator");
		adminButton.setBounds(807, 546, 187, 25);
		setRadioButtonLayout(adminButton);
		createAccount.add(adminButton);
		
		ButtonGroup accTypeButtons = new ButtonGroup();
		accTypeButtons.add(consumerButton);
		accTypeButtons.add(merchantButton);
		accTypeButtons.add(adminButton);
		
	}
	
	public void setupLoginScreen() {
		loginScreen = new JPanel();
		loginScreen.setBackground(BG);
		loginScreen.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginScreen.setLayout(null);
		
		ActionListener Login = new LoginButtonListener();
		JButton createAccSubmitButton = new JButton("Submit");
		createAccSubmitButton.setBounds(957, 430, 158, 47);
		createAccSubmitButton.addActionListener(Login);
		setButtonLayout(createAccSubmitButton);
		
		loginScreen.add(createAccSubmitButton);
		
		JLabel lblCreateAnAccount = new JLabel("Log in");
		lblCreateAnAccount.setForeground(new Color(255, 255, 255));
		lblCreateAnAccount.setBackground(new Color(255, 255, 255));
		lblCreateAnAccount.setFont(new Font("Segoe UI", Font.PLAIN, 38));
		lblCreateAnAccount.setBounds(734, 188, 349, 76);
		loginScreen.add(lblCreateAnAccount);
		
		try {
			small = ImageIO.read(new File("Jmarket-small.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel logoLabelSmall = new JLabel(new ImageIcon(small));
		logoLabelSmall.setBounds(147, 306, 454, 129);
		loginScreen.add(logoLabelSmall);
		
		loginInput = new JTextField(20);
		loginInput.setBounds(851, 277, 277, 47);
		setTextFieldLayout(loginInput, "Username");
		loginScreen.add(loginInput);
		
		usernameLabel = new JLabel("Username");
		setFieldLabelLayout(usernameLabel);
		usernameLabel.setBounds(734, 290, 78, 22);
		loginScreen.add(usernameLabel);
		
		ActionListener homeScreen = new HomeScreenListener();
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(765, 430, 158, 47);
		setButtonLayout(cancelButton);
		cancelButton.addActionListener(homeScreen);
		loginScreen.add(cancelButton);
		
		pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(734, 349, 71, 22);
		setFieldLabelLayout(pwdLabel);
		loginScreen.add(pwdLabel);
		
		pwdLoginInput = new JPasswordField(20);
		pwdLoginInput.setBounds(851, 337, 277, 47);
		setTextFieldLayout(pwdLoginInput, "Password");
		loginScreen.add(pwdLoginInput);

	}
	
	public void setupMainScreen() {
		
		mainScreen = new JPanel();
		mainScreen.setBackground(BG);
		mainScreen.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainScreen.setLayout(null);
		
		try {
			xs = ImageIO.read(new File("Jmarket-xs.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel logoLabelXS = new JLabel(new ImageIcon(xs));
		logoLabelXS.setBounds(54, 47, 200, 57);
		mainScreen.add(logoLabelXS);
		
		
		JButton searchButton = new JButton("Search");
		if (currentUser.getType().equals("Admin")) {
			search = new SearchUserButtonListener();
			searchButton.setText("Search users");
		} else {
			search = new SearchButtonListener();
		}
		searchButton.setBounds(303, 47, 158, 47);
		setButtonLayout(searchButton);
		searchButton.addActionListener(search);
		mainScreen.add(searchButton);
		
		if (currentUser.getType().equals("Buyer")) {
			ActionListener buy = new PurchaseListener();
			purchaseButton = new JButton("Purchase selected");
			purchaseButton.setForeground(new Color(240, 248, 255));
			purchaseButton.setFont(DEFAULT);
			purchaseButton.setFocusPainted(false);
			purchaseButton.setEnabled(false);
			purchaseButton.setBorderPainted(false);
			purchaseButton.setBackground(new Color(45, 45, 90));
			purchaseButton.setBounds(487, 47, 193, 47);
			purchaseButton.addActionListener(buy);
			mainScreen.add(purchaseButton);
			
			ActionListener checkHistory = new HistoryListener();
			historyButton = new JButton("History");
			historyButton.setForeground(new Color(240, 248, 255));
			historyButton.setFont(DEFAULT);
			historyButton.setFocusPainted(false);
			historyButton.setBorderPainted(false);
			historyButton.setBackground(new Color(45, 45, 90));
			historyButton.setBounds(707, 47, 150, 47);
			historyButton.addActionListener(checkHistory);
			mainScreen.add(historyButton);
			
		} else if (currentUser.getType().equals("Seller") || currentUser.getType().equals("Admin")) {
			if (currentUser.getType().equals("Seller")) {
				ActionListener addItem = new AddItemListener();
				addItemButton = new JButton("Add item");
				addItemButton.setForeground(new Color(240, 248, 255));
				addItemButton.setFont(DEFAULT);
				addItemButton.setFocusPainted(false);
				addItemButton.setBorderPainted(false);
				addItemButton.setBackground(new Color(45, 45, 90));
				addItemButton.setBounds(487, 47, 193, 47);
				addItemButton.addActionListener(addItem);
				mainScreen.add(addItemButton);
			}
			
			ActionListener edit = new EditItemListener();
			editButton = new JButton("Edit item");
			editButton.setForeground(new Color(240, 248, 255));
			editButton.setFont(DEFAULT);
			editButton.setFocusPainted(false);
			editButton.setBorderPainted(false);
			editButton.setBackground(new Color(45, 45, 90));
			editButton.setBounds(707, 47, 150, 47);
			editButton.addActionListener(edit);
			mainScreen.add(editButton);
		}
		
		
		if (currentUser.getType().equals("Seller")) {
			data = new Object[currentUser.getItems().size()][3];
			for (int i = 0; i < currentUser.getItems().size(); i++) {
				data[i] = itemToArray(currentUser.getItems().get(i));
			}
		} else {
			data = new Object[m.getItems().size()][3];
			for (int i = 0; i < m.getItems().size(); i++) {
				data[i] = itemToArray(m.getItems().get(i));
			}
		}
		
		model = new DefaultTableModel(data, columns);
		table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		table.setModel(model);
		
		ListSelectListener lsl = new ListSelectListener();
		table.getSelectionModel().addListSelectionListener(lsl);
		
		
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		right.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		table.getColumnModel().getColumn(2).setCellRenderer(right);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setTableHeader(null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setBounds(0, 0, 705, 300);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		table.setForeground(Color.WHITE);
		table.setBackground(BUTTON);
		table.setFont(DEFAULT);
		table.setRowHeight(60);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(53, 180, 705, 519);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getViewport().setBackground(BUTTON);
		mainScreen.add(scroll);
		
		JLabel itemLabel = new JLabel("Item");
		itemLabel.setBounds(70, 137, 66, 27);
		setLabelLayout(itemLabel);
		mainScreen.add(itemLabel);
		
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setForeground(Color.WHITE);
		priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		priceLabel.setBounds(562, 137, 66, 27);
		mainScreen.add(priceLabel);
		
		JLabel lblInStock = new JLabel("In Stock");
		lblInStock.setForeground(Color.WHITE);
		lblInStock.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblInStock.setBounds(660, 137, 98, 27);
		mainScreen.add(lblInStock);
		
		descPane = new JTextPane();
		descPane.setEditable(false);
		descPane.setText("");
		descPane.setBackground(BUTTON);
		descPane.setForeground(Color.WHITE);
		descPane.setFont(DEFAULT);
		descPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		descPane.setBounds(787, 180, 444, 519);
		mainScreen.add(descPane);
		
		JLabel lblSelectedItemInfo = new JLabel("Item information");
		lblSelectedItemInfo.setForeground(Color.WHITE);
		lblSelectedItemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblSelectedItemInfo.setBounds(789, 137, 183, 27);
		mainScreen.add(lblSelectedItemInfo);
		
		try {
			logoutImg = ImageIO.read(new File("logout.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ActionListener Logout = new LogoutListener();
		logOutButton = new JButton(new ImageIcon(logoutImg));
		logOutButton.setToolTipText("Log out");
		logOutButton.setFocusPainted(false);
		logOutButton.setBorderPainted(false);
		logOutButton.setBackground(BG);
		logOutButton.addActionListener(Logout);
		logOutButton.setBounds(1198, 55, 35, 35);
		mainScreen.add(logOutButton);
		
		JLabel usernameAtTop = new JLabel("Welcome, " + currentUser.getUsername());
		usernameAtTop.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameAtTop.setFont(DEFAULT);
		usernameAtTop.setForeground(Color.WHITE);
		usernameAtTop.setBounds(911, 55, 257, 35);
		mainScreen.add(usernameAtTop);
		
	}
}
