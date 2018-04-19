import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class UserInfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private final Color BG = new Color(20, 20, 60);
	private final Color BUTTON = new Color(45, 45, 90);
	private final Font DEFAULT = new Font("Segoe UI", Font.PLAIN, 20);

	public String name;
	public String desc;
	public int q;
	public double price;
	
	private User user;
	
	private String info;
	private String history;
	
	private Marketplace m;
	
	public void setFieldLabelLayout(JLabel l) {
		l.setForeground(Color.WHITE);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 38));
	}
	
	public void close() {
		this.dispose();
	}
	
	public String fileName() {
		String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
		
		return "PurchaseHistory_" + this.user.getUsername() + "_" + timeStamp + ".txt";
	}
	
	public String fileHeader() {
		
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		return "Purchase log for " + this.user.getUsername() + "\r\nGenerated at: " + timeStamp + "\r\n\r\n";
	}
	
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String fileHeader = fileHeader();
			String fileName = fileName();
			
			int reply = JOptionPane.showConfirmDialog(null, "Save record to \"" + fileName + "\"?", 
					"Confirm", JOptionPane.OK_CANCEL_OPTION);
	        if (reply == JOptionPane.OK_OPTION) {
	        	try {
					PrintWriter writer = new PrintWriter(fileName);
					writer.print(fileHeader);
					writer.print(history);
					writer.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        } else {
	        	
	        }
		}
	}

	/**
	 * Create the dialog.
	 */
	public UserInfoDialog(User u, Marketplace m) {
		
		this.user = u;
		this.m = m;
		
		history = "";
		
		for (Item i : u.getItems()) {
			history += i.getPurchaseTime() + "\r\n" + i.getName() + " - $" + i.getPrice() + "\r\nSold by #" + i.getSellerID() + "\r\n\r\n";
		}
		
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		info = "Username: " + u.getUsername() + "\r\nEmail: " + u.getEmail() + "\r\nID: " + u.getID();
		
		setTitle("info");
		setBounds(100, 100, 539, 469);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(BG);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("User info");
		nameLabel.setBounds(39, 23, 328, 64);
		setFieldLabelLayout(nameLabel);
		contentPanel.add(nameLabel);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(12, 328, 507, 99);
		contentPanel.add(buttonPane);
		buttonPane.setBackground(BG);
		buttonPane.setLayout(null);
		
		ActionListener saveFile = new SaveListener();
		
		JButton saveButton = new JButton("Save purchase history to file");
		saveButton.setBounds(168, 13, 314, 53);
		saveButton.addActionListener(saveFile);
		buttonPane.add(saveButton);
		saveButton.setForeground(new Color(240, 248, 255));
		saveButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		saveButton.setBackground(new Color(45, 45, 90));
		saveButton.setActionCommand("OK");
		
		JTextPane textPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(27, 110, 466, 200);
		textPane.setEditable(false);
		textPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		textPane.setBackground(BUTTON);
		textPane.setForeground(Color.WHITE);
		textPane.setFont(DEFAULT);
		textPane.setText(info);
		contentPanel.add(scrollPane);
		
		
	}
}