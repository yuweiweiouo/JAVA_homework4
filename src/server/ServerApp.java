package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JButton;

public class ServerApp extends JFrame {

	private JPanel contentPane;
	private JTextField inputMessage_textField;
	private JLabel port_label;
	private JTextArea messageBox_TextArea;
	private JTextArea userList_textArea;
	Server server;
	ArrayList<String> userList = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ServerApp frame = new ServerApp();
		frame.setVisible(true);		
	}

	/**
	 * Create the frame.
	 */
	public ServerApp() {		
		init();
		renderGUI();
		
		server = new Server(this);
	}
	
	void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		this.setTitle("Server");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	/**
	 * add all elements to the frame
	 */
	void renderGUI() {
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

		JLabel portNumber_label = new JLabel("Port number : ");
		portNumber_label.setForeground(new Color(100, 149, 237));
		portNumber_label.setFont(new Font("SetoFont-SP", Font.PLAIN, 18));
		portNumber_label.setBounds(10, 10, 126, 23);
		contentPane.add(portNumber_label);

		userList_textArea = new JTextArea();
		userList_textArea.setLineWrap(true);
		userList_textArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		userList_textArea.setBounds(10, 40, 150, 300);
		userList_textArea.setBackground(new Color(255, 239, 213));
		userList_textArea.setEditable(false);
		userList_textArea.setEnabled(false);
		userList_textArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentPane.add(userList_textArea);

		messageBox_TextArea = new JTextArea();
		messageBox_TextArea.setEnabled(false);
		messageBox_TextArea.setEditable(false);
		messageBox_TextArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		messageBox_TextArea.setLineWrap(true);
		messageBox_TextArea.setBackground(new Color(240, 255, 240));
		messageBox_TextArea.setBounds(170, 40, 400, 267);
		messageBox_TextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentPane.add(messageBox_TextArea);

		inputMessage_textField = new JTextField();
		inputMessage_textField.setToolTipText("");
		inputMessage_textField.setBounds(170, 317, 333, 23);
		inputMessage_textField.setColumns(10);
		contentPane.add(inputMessage_textField);

		JButton send_btn = new JButton("送出");
		send_btn.setBounds(513, 317, 61, 23);
		contentPane.add(send_btn);

		port_label = new JLabel("1111");
		port_label.setForeground(new Color(240, 128, 128));
		port_label.setFont(new Font("SetoFont-SP", Font.PLAIN, 18));
		port_label.setBounds(134, 10, 150, 23);
		contentPane.add(port_label);
	}

	public void setPortDisplayed(int inpPort) {
		port_label.setText(Integer.toString(inpPort));
	}
	
	public void addToMessageBox(String msg){
		messageBox_TextArea.append(msg + "\n");
	}
	
	public void addUsertoDisplay(String username){
		userList.add(username);
		userList_textArea.append(username + "\n");
	}
	
	public void removeUserFromDisplay(String username){
		userList.remove(username);
		
		userList_textArea.setText("");
		for(String u: userList){
			userList_textArea.append(u + "\n");
		}
	}
}
