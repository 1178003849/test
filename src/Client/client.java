package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class client {
	String IP = "172.20.10.7";
	int port = 7777;
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw = null;
	boolean canWaiter = true;
	ChatRoom window = new ChatRoom();
	Waiter waiter;// ����һ���߳�
	public String username;

	public client(String name) {
		username = name;
		while (username.length() != 6) {
			username += " ";
		}
		// window.txtMsg.append("vicvuewf");
		window.frame.setVisible(true);//
		try {
			// �ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�
			socket = new Socket(IP, port);
			System.out.println("Socket=" + socket);////////

			/* ����һ���ͻ���UI */

			// ͬ������ԭ��һ��
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

			waiter = new Waiter();// �����߳�
			waiter.start();// �����߳�
			pw.println(username + "����������");
			pw.flush();
			window.button_1.addActionListener(new ActionListener() {// ���津���¼�,������Ϣ
				public void actionPerformed(ActionEvent e) {
					System.out.println(window.textField_2.getText());
					// window.txtMsg.append(window.textField_2.getText());
					sendMsg(window.textField_2.getText());
					window.textField_2.setText("");
					window.textField_2.requestFocus();// �����ı���
				}
			});
			window.btnNewButton_1.addActionListener(new ActionListener() {// �˳�
				public void actionPerformed(ActionEvent e) {
					window.frame.dispose();
					sendexit("�˳�������");

				}
			});
			window.btnNewButton.addActionListener(new ActionListener() {// ��ʾ�û���
				public void actionPerformed(ActionEvent e) {
					window.textField.setText(username);
				}
			});
//			pw.println("END");
//			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(String str) {// ������Ϣ
		try {
			SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");// ���ʵʱʱ��
			String time = formater.format(new Date());
			//pw.println(username + ":" + time + "  " + str);// �������������Ϣ
			// pw.println(time+" "+str);//�������������Ϣ
			pw.flush();
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(null, "������Ϣʧ�ܣ�");
		}
	}

	private void sendexit(String str) {// �˳�
		try {
			pw.println(username + str);// �������������Ϣ
//			canWaiter=false;
			pw.flush();
//			pw.close();
//			br.close();
//			socket.close();
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(null, "������Ϣʧ�ܣ�");
		}
	}

	private class Waiter extends Thread {
		public void run() {
			// window.frame.setVisible(true);
			while (canWaiter) {
				String msg;
				try {
					msg = br.readLine();
					if (msg.equals("exit"))// ����Application�Ͽ����ӻ�Ӧ
					{
						break;
					}
					// �����������ϵ�����
					if (!msg.equals(null))
						window.txtMsg.append(msg + "\n");
					System.out.println(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// txtMsg.append(time+" "+name1+"��"+msg+"\n");
			}
		}
	}

	public static void main(String[] args) {

		// new client("df");

	}

}
