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
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class EnvironmentGUI {
	
	private Integer setTemp = null;

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
		
		JLabel lbl_environmentService = new JLabel("Enviromental Service");
		lbl_environmentService.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_environmentService.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_environmentService.setBounds(200, 30, 240, 40);
		frame.getContentPane().add(lbl_environmentService);
		
		JButton btn_showTemp = new JButton("Show Current Temp");
		btn_showTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_showTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_showTemp.setText(null);				
				
				if (setTemp == null) {	
					
					int curentTemp = (int)20;
					
					CurrentRequest request = CurrentRequest.newBuilder().setCurrent(curentTemp).build();
					
					CurrentResponse response = envBlockingStub.getCurrentRoomTemp(request);
					System.out.println(response.getCurrentNew());					
					textArea_showTemp.append(" " + response.getCurrentNew());						
				}
				
				else {	
					
					//textArea_showTemp.setText(null);		        	
	        	
					CurrentRequest request = CurrentRequest.newBuilder().setCurrent(setTemp).build();
				
					CurrentResponse response = envBlockingStub.getCurrentRoomTemp(request);										
					textArea_showTemp.append(" " + response.getCurrentNew());	
					System.out.println(response.getCurrentNew());
				}		        	        
			}
		});
		btn_showTemp.setBounds(30, 120, 230, 40);
		frame.getContentPane().add(btn_showTemp);
		
		textArea_showTemp = new JTextArea();
		textArea_showTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_showTemp.setBounds(280, 120, 350, 40);
		frame.getContentPane().add(textArea_showTemp);
		
		JButton btn_setTemp = new JButton("Set New Temp");
		btn_setTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textArea_showTemp.setText(" ");				
				
				if (txt_setTemp.getText().isEmpty()){
					
					JOptionPane.showMessageDialog(null, "Plase enter the new temperature");
					txt_setTemp.requestFocus();
				}
				
				else {
					
					setTemp = Integer.parseInt(txt_setTemp.getText().toString());
					TempRequest request = TempRequest.newBuilder().setTemp(setTemp).build();
					
					System.out.println("Request received to change the environment temperarure to: " + request.getTemp() + " C!");
					
					TempResponse response = envBlockingStub.setRoomTemp(request);
					textArea_showTemp.append(response.getTempNew());
					System.out.println(response.getTempNew());	
					
					System.out.println("Changing the environment temperature completed.");
					System.out.println("------------------------------------------------");
					
				}	
			}
		});
		btn_setTemp.setBounds(30, 200, 230, 39);
		frame.getContentPane().add(btn_setTemp);
		
		txt_setTemp = new JTextField();
		txt_setTemp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
				
	            if((key>=KeyEvent.VK_0 && key<=KeyEvent.VK_9)||(key>=KeyEvent.VK_NUMPAD0&&key<=KeyEvent.VK_NUMPAD9)||key==KeyEvent.VK_BACK_SPACE){
	            	txt_setTemp.setEditable(true);
	            }
	            
	            else{
	            	
	                //show message box                 
	                JOptionPane.showMessageDialog(null, "Enter numbers only, letters or any special characters are not allowed"); 
	                txt_setTemp.setEditable(true);
	                txt_setTemp.setText("");
	                txt_setTemp.requestFocus();
	            }
			}
		});
		txt_setTemp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea_showTemp.setText(null);
			}
		});
		txt_setTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_setTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_setTemp.setBounds(280, 200, 100, 40);
		frame.getContentPane().add(txt_setTemp);
		txt_setTemp.setColumns(10);
		
		
	}
}
