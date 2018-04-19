import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class SearchUserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField searchBox;
	
	private final Color BG = new Color(20, 20, 60);
	private final Color BUTTON = new Color(45, 45, 90);
	private final Font DEFAULT = new Font("Segoe UI", Font.PLAIN, 20);
	
	private JRadioButton nameRadioButton = new JRadioButton();
	private JRadioButton idRadioButton = new JRadioButton();
	private ButtonGroup searchButtons;
	
	private String param;
	private String value;
	
	private Marketplace m;

	/**
	 * Launch the application.
	 */

	public void setButtonLayout(JButton b) {
	}
	
	public void setTextFieldLayout(JTextField t, String s) {
		t.setForeground(Color.WHITE);
		t.setCaretColor(Color.WHITE);
		t.setToolTipText(s);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		t.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
		t.setBackground(BUTTON);
	}
	
	public void setFieldLabelLayout(JLabel l) {
		l.setForeground(Color.WHITE);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 38));
	}
	
	public void close() {
		this.dispose();
	}
	
	class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			close();
		}
	}
	
	

	/**
	 * Create the dialog.
	 */
	public SearchUserDialog(Marketplace m) {
		this.m = m;
		setTitle("Search users");
		setBounds(100, 100, 572, 353);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(BG);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		searchBox = new JTextField();
		searchBox.setBounds(39, 99, 473, 41);
		setTextFieldLayout(searchBox, "Name");
		contentPanel.add(searchBox);

		searchBox.setColumns(10);
		
		JLabel nameLabel = new JLabel("Search users");
		nameLabel.setBounds(39, 23, 238, 64);
		setFieldLabelLayout(nameLabel);
		contentPanel.add(nameLabel);
		
		JLabel lblBy = new JLabel("By...");
		lblBy.setForeground(Color.WHITE);
		lblBy.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblBy.setBounds(39, 149, 52, 50);
		contentPanel.add(lblBy);
		
		nameRadioButton = new JRadioButton(" Name");
		nameRadioButton.setBackground(BG);
		nameRadioButton.setForeground(Color.WHITE);
		nameRadioButton.setFont(DEFAULT);
		nameRadioButton.setBounds(49, 208, 127, 25);
		nameRadioButton.setBorderPainted(false);
		nameRadioButton.setFocusPainted(false);
		contentPanel.add(nameRadioButton);
		
		idRadioButton = new JRadioButton(" ID#");
		idRadioButton.setBackground(BG);
		idRadioButton.setForeground(Color.WHITE);
		idRadioButton.setFont(DEFAULT);
		idRadioButton.setBounds(49, 241, 127, 25);
		idRadioButton.setBorderPainted(false);
		idRadioButton.setFocusPainted(false);
		contentPanel.add(idRadioButton);
		
		searchButtons = new ButtonGroup();
		searchButtons.add(nameRadioButton);
		searchButtons.add(idRadioButton);
		
		ActionListener search = new SearchButtonListener();
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(271, 216, 118, 53);
		contentPanel.add(searchButton);
		searchButton.setForeground(new Color(240, 248, 255));
		searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		searchButton.setFocusPainted(false);
		searchButton.setBorderPainted(false);
		searchButton.setBackground(new Color(45, 45, 90));
		searchButton.addActionListener(search);
		searchButton.setActionCommand("OK");
		
		ActionListener cancel = new CancelButtonListener();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(401, 216, 111, 53);
		contentPanel.add(cancelButton);
		cancelButton.setForeground(new Color(240, 248, 255));
		cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		cancelButton.setFocusPainted(false);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground(new Color(45, 45, 90));
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(cancel);
		
		
	}
	
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			JRadioButton[] buttons = {nameRadioButton, idRadioButton};
			System.out.println(nameRadioButton.isSelected());
			
			for (JRadioButton r : buttons) {
				if (r.isSelected()) {
					param = r.getText().substring(1);
				}
			}
			
			value = searchBox.getText();
			
			close();
			
		}
	}
	
	public String getParam() {
		return this.param;
	}
	
	public String getValue() {
		return this.value;
	}
}