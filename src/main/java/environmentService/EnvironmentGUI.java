package environmentService;

import java.awt.EventQueue;

import javax.swing.JFrame;

import environmentService.EnvironmentServiceGrpc.EnvironmentServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EnvironmentGUI {

	private JFrame frame;
	
	private static EnvironmentServiceBlockingStub envBlockingStub;	
	JTextArea textArea_showTemp;
	private JTextField txt_setTemp;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvironmentGUI window = new EnvironmentGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnvironmentGUI() {
		
		ManagedChannel envChannel = ManagedChannelBuilder
								.forAddress("localhost", 50054)
								.usePlaintext()
								.build();
		
		envBlockingStub = EnvironmentServiceGrpc.newBlockingStub(envChannel);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.setBounds(100, 100, 696, 591);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_environmentService = new JLabel("New label");
		lbl_environmentService.setBounds(194, 28, 242, 39);
		frame.getContentPane().add(lbl_environmentService);
		
		JButton btn_showTemp = new JButton("Show Current Temp");
		btn_showTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_showTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getCurrentRoomTemp();
			}
		});
		btn_showTemp.setBounds(36, 128, 230, 39);
		frame.getContentPane().add(btn_showTemp);
		
		textArea_showTemp = new JTextArea();
		textArea_showTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_showTemp.setBounds(302, 128, 335, 39);
		frame.getContentPane().add(textArea_showTemp);
		
		JButton btn_setTemp = new JButton("Set New Temp");
		btn_setTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRoomTemp();
			}
		});
		btn_setTemp.setBounds(36, 192, 230, 39);
		frame.getContentPane().add(btn_setTemp);
		
		txt_setTemp = new JTextField();
		txt_setTemp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea_showTemp.setText(null);
			}
		});
		txt_setTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_setTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_setTemp.setBounds(314, 195, 146, 39);
		frame.getContentPane().add(txt_setTemp);
		txt_setTemp.setColumns(10);
		
		
	}
	
	public void getCurrentRoomTemp() throws io.grpc.StatusRuntimeException{
		CurrentResponse response = envBlockingStub.getCurrentRoomTemp(null);
		System.out.println(response.getValue());
		try {
			textArea_showTemp.append(" The current temperratutre is " + response.getValue() + " oC");
		}catch(StatusRuntimeException e){
			System.out.println(e.getStatus());
		}
	}
	
	public void setRoomTemp() throws io.grpc.StatusRuntimeException {
		
		int temp = (int) 0;
		
		if (txt_setTemp.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Plase enter the new temperature");
			txt_setTemp.requestFocus();
		}
		
		else {
			temp = Integer.parseInt(txt_setTemp.getText().toString());
			TempRequest request = TempRequest.newBuilder().setValue(temp).build();
			
			TempResponse response = envBlockingStub.setRoomTemp(request);
			//textArea_showTemp.append(" ");
			textArea_showTemp.append("The temperatore was setted to " +response.getResult());
			System.out.println("The temperatore was setted to " +response.getResult());		
					
		}		
	}
}
