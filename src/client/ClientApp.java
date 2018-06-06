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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import server.Server;
import server.ServerApp;
import javax.swing.JScrollPane;

public class ClientApp extends JFrame implements ActionListener, KeyListener {
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
	String name;
	ArrayList<String> serverList = new ArrayList<String>();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

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
		
		// before starting, choose a name for yourself first! 
		while (true) {
			String inpName = JOptionPane.showInputDialog(this, "取個名字吧!(◔ д◔)~");
			if (inpName == null){
				System.exit(1);
			}else if (!inpName.isEmpty()){
				name = inpName;
				break;
			}
				
		}

		init();
		renderGUI();

		client = new Client(this);
	}

	void init() {
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 150, 234);
		contentPane.add(scrollPane);

		serverList_textArea = new JTextArea();
		scrollPane.setViewportView(serverList_textArea);
		serverList_textArea.setLineWrap(true);
		serverList_textArea.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		serverList_textArea.setBackground(new Color(255, 239, 213));
		serverList_textArea.setEditable(false);
		serverList_textArea
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

		username_label = new JLabel(name);
		username_label.setForeground(new Color(240, 128, 128));
		username_label.setFont(new Font("華康中圓體", Font.PLAIN, 18));
		username_label.setBounds(106, 10, 129, 23);
		contentPane.add(username_label);

		lblServerListport = new JLabel("ServerList (port)");
		lblServerListport.setForeground(new Color(100, 149, 237));
		lblServerListport.setFont(new Font("SetoFont", Font.PLAIN, 18));
		lblServerListport.setBounds(10, 40, 161, 23);
		contentPane.add(lblServerListport);

		serverPort_textField = new JTextField();
		serverPort_textField.setBounds(10, 317, 82, 23);
		serverPort_textField.setColumns(10);
		serverPort_textField.addKeyListener(this);
		contentPane.add(serverPort_textField);

		addServer_btn = new JButton("加入");
		addServer_btn.setFont(new Font("華康中圓體", Font.PLAIN, 12));
		addServer_btn.setBackground(new Color(255, 192, 203));
		addServer_btn.setBounds(99, 317, 61, 23);
		addServer_btn.addActionListener(this);
		contentPane.add(addServer_btn);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			if (e.getSource() == inputMessage_textField) {
				send_btn_Click();
			} else if (e.getSource() == serverPort_textField) {
				addServer_btn_Click();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send_btn) {
			send_btn_Click();
		} else if (e.getSource() == addServer_btn) {
			addServer_btn_Click();
		}
	}

	private void addServer_btn_Click() {
		try {
			int toConnectPort = Integer.parseInt(serverPort_textField.getText());
			client.connectToServer(toConnectPort);
			serverPort_textField.setText("");
		} catch (NumberFormatException ne) {
			System.out.println("請輸入數字");
			showMessage("請輸入數字!爸托! (◔ д◔)");
		}

	}

	private void send_btn_Click() {
		try {
			String toSendMsg = inputMessage_textField.getText();
			if (toSendMsg != "") {
				client.broadcast(toSendMsg);
				inputMessage_textField.setText("");
				addToMessageBox(getUsername() + ": " + toSendMsg);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	public void addToMessageBox(String msg) {
		messageBox_TextArea.append(msg + "\n");
	}

	public String getUsername() {
		return username_label.getText();
	}

	public void setUsername(String inpName) {
		username_label.setText(inpName);
	}

	public void addServertoDisplay(String inpPort) {
		serverList.add(inpPort);
		serverList_textArea.append(inpPort + "\n");
	}

	public void removeServerFromDisplay(String inpPort) {
		serverList.remove(inpPort);

		serverList_textArea.setText("");
		for (String u : serverList) {
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
