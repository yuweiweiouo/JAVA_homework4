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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ServerApp extends JFrame implements ActionListener, KeyListener{

	private JPanel contentPane;
	private JTextField inputMessage_textField;
	private JLabel port_label;
	private JTextArea messageBox_TextArea;
	private JTextArea userList_textArea;
	private JButton send_btn;
	Server server;
	ArrayList<String> userList = new ArrayList<String>();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
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
		setBounds(100, 100, 600, 420);
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 150, 267);
		contentPane.add(scrollPane);

		userList_textArea = new JTextArea();
		scrollPane.setViewportView(userList_textArea);
		userList_textArea.setLineWrap(true);
		userList_textArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		userList_textArea.setBackground(new Color(255, 239, 213));
		userList_textArea.setEditable(false);
		userList_textArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(170, 40, 404, 267);
		contentPane.add(scrollPane_1);

		messageBox_TextArea = new JTextArea();
		scrollPane_1.setViewportView(messageBox_TextArea);
		messageBox_TextArea.setEditable(false);
		messageBox_TextArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		messageBox_TextArea.setLineWrap(true);
		messageBox_TextArea.setBackground(new Color(240, 255, 240));
		messageBox_TextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		inputMessage_textField = new JTextField();
		inputMessage_textField.setToolTipText("");
		inputMessage_textField.setBounds(170, 317, 333, 23);
		inputMessage_textField.setColumns(10);
		inputMessage_textField.addKeyListener(this);
		contentPane.add(inputMessage_textField);

		send_btn = new JButton("送出");
		send_btn.setFont(new Font("華康中圓體", Font.PLAIN, 12));
		send_btn.setBackground(new Color(255, 192, 203));
		send_btn.setBounds(513, 317, 61, 23);
		send_btn.addActionListener(this);
		contentPane.add(send_btn);

		port_label = new JLabel("1111");
		port_label.setForeground(new Color(240, 128, 128));
		port_label.setFont(new Font("SetoFont-SP", Font.PLAIN, 18));
		port_label.setBounds(134, 10, 150, 23);
		contentPane.add(port_label);
		
		JLabel lblUserList = new JLabel("User List");
		lblUserList.setForeground(new Color(100, 149, 237));
		lblUserList.setFont(new Font("SetoFont", Font.PLAIN, 18));
		lblUserList.setBounds(10, 40, 126, 23);
		contentPane.add(lblUserList);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10 && e.getSource() == inputMessage_textField){
			send_btn_Click();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){		
		if(e.getSource() == send_btn){
			send_btn_Click();			
		}
	}
	
	
	private void send_btn_Click() {
		String toSendMsg = inputMessage_textField.getText();
		if(toSendMsg != ""){
			server.broadcast(toSendMsg);
			inputMessage_textField.setText("");			
		}
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
