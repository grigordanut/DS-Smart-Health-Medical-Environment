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

public class EnvironmentGUI {
	
	//declare an array list patientList of Register class and set to null
    public static ArrayList <String> tempList = null;

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
		
        //initalize a new Array List patientList
        tempList = new ArrayList<String>(); 	
		
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
				textArea_showTemp.setText(" ");
				
		        if (tempList.isEmpty()){
		        	
		        	String curentTemp = "20";
					
					CurrentRequest request = CurrentRequest.newBuilder().setCurrent(curentTemp).build();
					
					CurrentResponse response = envBlockingStub.getCurrentRoomTemp(request);
					System.out.println(response.getCurrentNew());					
					textArea_showTemp.append(response.getCurrentNew());			        	
		        }
		        
		        else {
		        	for (int i = 0; i < tempList.size(); i++) {
		        		textArea_showTemp.append(tempList.get(-1));
		        	}
		        }
			}
		});
		btn_showTemp.setBounds(36, 128, 230, 39);
		frame.getContentPane().add(btn_showTemp);
		
		textArea_showTemp = new JTextArea();
		textArea_showTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_showTemp.setBounds(302, 128, 370, 39);
		frame.getContentPane().add(textArea_showTemp);
		
		JButton btn_setTemp = new JButton("Set New Temp");
		btn_setTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textArea_showTemp.setText(" ");
				int temp = (int)0;
				
				if (txt_setTemp.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Plase enter the new temperature");
					txt_setTemp.requestFocus();
				}
				
				else {
					temp = Integer.parseInt(txt_setTemp.getText().toString());
					TempRequest request = TempRequest.newBuilder().setTemp(String.valueOf(temp)).build();
					
					TempResponse response = envBlockingStub.setRoomTemp(request);
					tempList.add(response.getTempNew() + "\n");
					textArea_showTemp.append(response.getTempNew());
					System.out.println(response.getTempNew());		
							
				}	
			}
		});
		btn_setTemp.setBounds(36, 192, 230, 39);
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
		txt_setTemp.setBounds(314, 195, 146, 39);
		frame.getContentPane().add(txt_setTemp);
		txt_setTemp.setColumns(10);
		
		
	}
	
//	public void getCurrentRoomTemp() throws io.grpc.StatusRuntimeException{
//		CurrentResponse response = envBlockingStub.getCurrentRoomTemp(null);
//		System.out.println(response.getOldTemp());
//		try {
//			textArea_showTemp.append(" The current temperratutre is " + response.getOldTemp() + " oC");
//		}catch(StatusRuntimeException e){
//			System.out.println(e.getStatus());
//		}
//	}	

}
