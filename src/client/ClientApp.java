package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import server.Server;
import server.ServerApp;

public class ClientApp extends JFrame implements ActionListener, KeyListener{
	private JPanel contentPane;
	private JTextField inputMessage_textField;
	private JLabel username_label;
	private JTextArea messageBox_TextArea;
	private JButton send_btn;
	private JTextArea serverList_textArea;
	private JLabel lblServerListport;
	private JTextField serverPort_textField;
	private JButton addServer_btn;
	Client client;
	ArrayList<String> serverList = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ClientApp frame = new ClientApp();
		frame.setVisible(true);		
	}


	/**
	 * Create the frame.
	 */
	public ClientApp() {		
		init();
		renderGUI();
		
		client = new Client(this);
	}
	
	void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 420);
		this.setTitle("Client");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	void renderGUI() {
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

		JLabel label = new JLabel("Username : ");
		label.setForeground(new Color(100, 149, 237));
		label.setFont(new Font("SetoFont-SP", Font.PLAIN, 18));
		label.setBounds(10, 10, 99, 23);
		contentPane.add(label);

		serverList_textArea = new JTextArea();
		serverList_textArea.setLineWrap(true);
		serverList_textArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		serverList_textArea.setBounds(10, 73, 150, 234);
		serverList_textArea.setBackground(new Color(255, 239, 213));
		serverList_textArea.setEditable(false);
		serverList_textArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentPane.add(serverList_textArea);

		messageBox_TextArea = new JTextArea();
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
		inputMessage_textField.addKeyListener(this);
		contentPane.add(inputMessage_textField);

		send_btn = new JButton("送出");
		send_btn.setBounds(513, 317, 61, 23);
		send_btn.addActionListener(this);
		contentPane.add(send_btn);

		username_label = new JLabel("魚尾尾");
		username_label.setForeground(new Color(240, 128, 128));
		username_label.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		username_label.setBounds(106, 10, 150, 23);
		contentPane.add(username_label);
		
		lblServerListport = new JLabel("Server List (port)");
		lblServerListport.setForeground(new Color(100, 149, 237));
		lblServerListport.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblServerListport.setBounds(10, 40, 150, 23);
		contentPane.add(lblServerListport);
		
		serverPort_textField = new JTextField();
		serverPort_textField.setBounds(10, 317, 76, 23);
		contentPane.add(serverPort_textField);
		serverPort_textField.setColumns(10);
		
		addServer_btn = new JButton("加入");
		addServer_btn.setBounds(99, 317, 61, 23);
		addServer_btn.addActionListener(this);
		contentPane.add(addServer_btn);
	}
	
	public void actionPerformed(ActionEvent e){		
		if(e.getSource() == send_btn){
			send_btn_Click();			
		}else if(e.getSource() == addServer_btn){
			addServer_btn_Click();
		}
	}
	
	private void addServer_btn_Click() {
		try{
			int toConnectPort = Integer.parseInt(serverPort_textField.getText());
			client.connectToServer(toConnectPort);
			serverPort_textField.setText("");
		}catch(NumberFormatException ne){
			System.out.println("請輸入數字");
		}
		
	}


	@Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10 && e.getSource() == inputMessage_textField){
        	send_btn_Click();
        }
    }


	
	private void send_btn_Click() {
		String toSendMsg = inputMessage_textField.getText();
		client.broadcast(toSendMsg);
		inputMessage_textField.setText("");
	}


	public void setPortDisplayed(int inpPort) {
		username_label.setText(Integer.toString(inpPort));
	}
	
	public void addToMessageBox(String msg){
		messageBox_TextArea.append(msg + "\n");
	}
	
	public String getUsername(){
		return username_label.getText();
	}
	
	public void setUsername(String inpName){
		username_label.setText(inpName);
	}
	
	public void addServertoDisplay(String inpPort){
		serverList.add(inpPort);
		serverList_textArea.append(inpPort + "\n");
	}
	
	public void removeServerFromDisplay(String inpPort){
		serverList.remove(inpPort);
		
		serverList_textArea.setText("");
		for(String u: serverList){
			serverList_textArea.append(u + "\n");
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
