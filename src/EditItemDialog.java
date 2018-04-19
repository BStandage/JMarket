import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class EditItemDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private JTextField descField;
	private JTextField qField;
	private JTextField priceField;
	
	private final Color BG = new Color(20, 20, 60);
	private final Color BUTTON = new Color(45, 45, 90);
	private final Font DEFAULT = new Font("Segoe UI", Font.PLAIN, 20);

	public String name;
	public String desc;
	public int q;
	public double price;

	/**
	 * Launch the application.
	 */

	public void setButtonLayout(JButton b) {
		b.setForeground(new Color(240, 248, 255));
		b.setFont(DEFAULT);
		b.setBackground(BUTTON);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
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
		l.setFont(new Font("Segoe UI", Font.PLAIN, 17));
	}
	
	public void close() {
		this.dispose();
	}

	class OKButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			name = nameField.getText();
			desc = descField.getText();
			q = Integer.parseInt(qField.getText());
			price = Double.parseDouble(priceField.getText());
			
			close();
			
		}
	}
	
	class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			close();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditItemDialog(Item i) {
		setTitle("Edit item");
		setBounds(100, 100, 465, 397);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(BG);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		
		nameField = new JTextField(i.getName());
		nameField.setBounds(192, 31, 208, 41);
		setTextFieldLayout(nameField, "Name");
		contentPanel.add(nameField);

		nameField.setColumns(10);

		descField = new JTextField(i.getDesc());
		descField.setColumns(10);
		descField.setBounds(192, 85, 208, 41);
		setTextFieldLayout(descField, "Description");
		contentPanel.add(descField);

		qField = new JTextField(Integer.toString(i.getQuantity()));
		qField.setHorizontalAlignment(SwingConstants.RIGHT);
		qField.setColumns(10);
		qField.setBounds(192, 140, 208, 41);
		setTextFieldLayout(qField, "Quantity");
		contentPanel.add(qField);

		priceField = new JTextField(Double.toString(i.getPrice()));
		priceField.setHorizontalAlignment(SwingConstants.RIGHT);
		priceField.setColumns(10);
		priceField.setBounds(192, 192, 208, 41);
		setTextFieldLayout(priceField, "Price");
		contentPanel.add(priceField);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(51, 34, 98, 41);
		setFieldLabelLayout(nameLabel);
		contentPanel.add(nameLabel);
		
		JLabel descLabel = new JLabel("Description:");
		descLabel.setBounds(47, 85, 119, 41);
		setFieldLabelLayout(descLabel);
		contentPanel.add(descLabel);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(51, 141, 119, 39);
		setFieldLabelLayout(lblQuantity);
		contentPanel.add(lblQuantity);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(51, 192, 119, 41);
		setFieldLabelLayout(lblPrice);
		contentPanel.add(lblPrice);
		
		JLabel dollarLabel = new JLabel("$");
		dollarLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dollarLabel.setBounds(61, 192, 119, 41);
		setFieldLabelLayout(dollarLabel);
		contentPanel.add(dollarLabel);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 246, 507, 99);
		contentPanel.add(buttonPane);
		buttonPane.setBackground(BG);
		JButton okButton = new JButton("OK");
		okButton.setBounds(103, 21, 118, 53);
		setButtonLayout(okButton);
		okButton.setActionCommand("OK");
		ActionListener OK = new OKButtonListener();
		okButton.addActionListener(OK);
		buttonPane.setLayout(null);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		ActionListener Cancel = new CancelButtonListener();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(230, 21, 111, 53);
		setButtonLayout(cancelButton);
		cancelButton.addActionListener(Cancel);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public int getQ() {
		return q;
	}

	public double getPrice() {
		return price;
	}
}
