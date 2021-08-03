package healthtServiceMonitoring;

import java.awt.EventQueue;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientMonitoring.DeviceRequest;
import patientMonitoring.DeviceResponse;
import patientMonitoring.BloodPressureTableGUI;
import patientMonitoring.PatientMonitoringServiceGrpc;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceBlockingStub;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceStub;
import patientMonitoring.PressureRequest;
import patientMonitoring.PressureResponse;

import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class healthServiceGUI implements ActionListener {
	
	private static PatientMonitoringServiceBlockingStub blockingStub;
	private static PatientMonitoringServiceStub asyncStub;
	
	private ServiceInfo serviceInfo;

	private JFrame frame;	
	
	JToggleButton tglbtn_deviceOnOff;
	private JTextField txt_showStatus;	
	
	private JSeparator separator1;
	private JSeparator separator2;
	
	private JTextField txt_systolic;
	private JTextField txt_diastolic;
	private JTextField txt_bpCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					healthServiceGUI window = new healthServiceGUI();
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
	public healthServiceGUI() {
		
		discoveryPatientMonitoringService();
		
		@SuppressWarnings("deprecation")
		String host = serviceInfo.getHostAddress();
		int port = serviceInfo.getPort();
		
		ManagedChannel channel = ManagedChannelBuilder.
						forAddress(host, port)
						.usePlaintext()
						.build();
		
		//stubs -- generate from proto
		blockingStub = PatientMonitoringServiceGrpc.newBlockingStub(channel);
		asyncStub = PatientMonitoringServiceGrpc.newStub(channel);		
		
		initialize();
	}
	
	public void discoveryPatientMonitoringService() {		
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			String service_type = "_patientMonitoring._tcp.local.";
			
			jmdns.addServiceListener(service_type, new ServiceListener(){
				
				@SuppressWarnings("deprecation")
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Paetient Monitoring Service resolved: " + event.getInfo());
					
					serviceInfo = event.getInfo();
					int port = serviceInfo.getPort();
					
					System.out.println("Resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type: " + event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + serviceInfo.getNiceTextString());
					System.out.println("\t host: " + serviceInfo.getHostAddress());					
					
				}

				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Paetient Monitoring Service removed: " + event.getInfo());
					
				}

				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Paetient Monitoring Service added: " + event.getInfo());
					
				}				
			});
			
			Thread.sleep(2000);
			
		} catch (UnknownHostException e) {			
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.getContentPane().setBackground(new Color(234, 234, 234));
		frame.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.setTitle("Smart Health Environment");
		frame.setBounds(100, 100, 615, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		////////////////////////////////////////
		/// Pacient Monitoring Device //////////
		////////////////////////////////////////
		
		//Main Label (Service Name)
		JLabel lbl_serviceName = new JLabel("Patient Monitoring Service");
		lbl_serviceName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_serviceName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_serviceName.setBounds(170, 10, 280, 25);
		frame.getContentPane().add(lbl_serviceName);
		
		//Monitoring Device Text Area		
		JTextArea textA_device = new JTextArea();
		textA_device.setEditable(false);
		textA_device.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textA_device.setBounds(15, 45, 295, 125);
		textA_device.setText("  Patient Monitorising Device "
				+ "\r\n The Device can be turned On/Off "
				+ "\r\n The Device is used to monitoring: "
				+ "\r\n -the heart pulse "
				+ "\r\n -the respiratory rate"
				+ "\r\n -and moore differnt body activity.");
		frame.getContentPane().add(textA_device);
		
		//Lalel Change Status
		JLabel lbl_changeStatus = new JLabel("Turn the Device:");
		lbl_changeStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_changeStatus.setBounds(330, 60, 120, 25);
		frame.getContentPane().add(lbl_changeStatus);
		
		//Toggle button turn monitoring device On/Off
		tglbtn_deviceOnOff = new JToggleButton("On");
		tglbtn_deviceOnOff.setFont(new Font("Tahoma", Font.BOLD, 18));
		tglbtn_deviceOnOff.setBounds(460, 50, 120, 40);
		frame.getContentPane().add(tglbtn_deviceOnOff);
		tglbtn_deviceOnOff.addActionListener(this);
		
		//Label info device status	
		JLabel lbl_infoStatus = new JLabel("Device Status:");
		lbl_infoStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_infoStatus.setBounds(330, 125, 120, 25);
		frame.getContentPane().add(lbl_infoStatus);
		
		//Text field show device status
		txt_showStatus = new JTextField();
		txt_showStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txt_showStatus.setBackground(Color.WHITE);
		txt_showStatus.setEditable(false);
		txt_showStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		txt_showStatus.setBounds(460, 115, 120, 40);
		frame.getContentPane().add(txt_showStatus);
		txt_showStatus.setColumns(10);		
		
		//Services separator 1		
		separator1 = new JSeparator();
		separator1.setBackground(Color.BLACK);
		separator1.setBounds(5, 180, 590, 1);
		frame.getContentPane().add(separator1);
		
		//////////////////////////////
		/// Blood Pressure Results ///
		//////////////////////////////
				
		//Blood Pressuse Text Area
		JTextArea textA_bloodPressure = new JTextArea();
		textA_bloodPressure.setEditable(false);
		textA_bloodPressure.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textA_bloodPressure.setBounds(15, 190, 315, 125);
		textA_bloodPressure.setText("  Measure your blood pressure to: "
				+ "\r\n -helps health team to diagnose any "
				+ "\r\n health problems early. "
				+ "\r\n Your health care team can take steps: "
				+ "\r\n -to control your blood pressure"
				+ "\r\n -if it is too low or too high.");
		frame.getContentPane().add(textA_bloodPressure);
		
		//Label Blood Pressure Results		
		JLabel lbl_bpResults = new JLabel("Blood Pressure Resuts");
		lbl_bpResults.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bpResults.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_bpResults.setBounds(380, 190, 190, 25);
		frame.getContentPane().add(lbl_bpResults);
		
		//Label systolic results
		JLabel lbl_systolic = new JLabel("Systolic:");
		lbl_systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_systolic.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_systolic.setBounds(355, 220, 75, 25);
		frame.getContentPane().add(lbl_systolic);
				
		//Text Field systolic results
		txt_systolic = new JTextField();
		txt_systolic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				txt_bpCategory.setText("");
				txt_systolic.setText("");
			}
		});
		
		
		txt_systolic.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
	            if((key>=KeyEvent.VK_0 && key<=KeyEvent.VK_9)||(key>=KeyEvent.VK_NUMPAD0&&key<=KeyEvent.VK_NUMPAD9)||key==KeyEvent.VK_BACK_SPACE){
	            	txt_systolic.setEditable(true);
	            }
	            
	            else{	                
	                //show message box                 
	                JOptionPane.showMessageDialog(null, "Enter numbers only, letters or any special characters are not allowed"); 
	                txt_systolic.setEditable(true);
	                txt_systolic.setText("");
	                txt_systolic.requestFocus();
	            }
			}
		});
		txt_systolic.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_systolic.setFont(new Font("Tahoma", Font.BOLD, 16));
		txt_systolic.setBounds(430, 220, 90, 25);
		frame.getContentPane().add(txt_systolic);
		txt_systolic.setColumns(10);		
		
		//Label diastolic results
		JLabel lblNewLabel_2 = new JLabel("Diastolic");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(355, 250, 75, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		//Text Field diastolic results
		txt_diastolic = new JTextField();
		txt_diastolic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				txt_bpCategory.setText("");
				txt_diastolic.setText("");
			}
		});
		txt_diastolic.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
	            if((key>=KeyEvent.VK_0 && key<=KeyEvent.VK_9)||(key>=KeyEvent.VK_NUMPAD0&&key<=KeyEvent.VK_NUMPAD9)||key==KeyEvent.VK_BACK_SPACE){
	            	txt_diastolic.setEditable(true);
	            }
	            
	            else{	                
	                //show message box                 
	                JOptionPane.showMessageDialog(null, "Enter numbers only, letters or any special characters are not allowed"); 
	                txt_diastolic.setEditable(true);
	                txt_diastolic.setText("");
	                txt_diastolic.requestFocus();
	            }
			}
		});
		txt_diastolic.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_diastolic.setFont(new Font("Tahoma", Font.BOLD, 16));
		txt_diastolic.setBounds(430, 250, 90, 25);
		frame.getContentPane().add(txt_diastolic);
		txt_diastolic.setColumns(10);
		
		JButton btn_submit = new JButton("Submit");
		btn_submit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				StreamObserver<PressureResponse> responseObserver = new StreamObserver<PressureResponse>() {

					@Override
					public void onNext(PressureResponse value) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onError(Throwable t) {
						// TODO Auto-generated method stub
						
					}

					@SuppressWarnings("static-access")
					@Override
					public void onCompleted() {
						
						float systolic = (float) 0.00;						
						float diastolic = (float) 0.00;
						
						if (txt_systolic.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Pleae enter Systolic value.");
							txt_systolic.requestFocus();
						}
						
						else if (txt_diastolic.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Pleae enter Diastolic value.");
							txt_diastolic.requestFocus();
						}
						
						else {						
							systolic = Float.parseFloat(txt_systolic.getText());
							diastolic = Float.parseFloat(txt_diastolic.getText());						
						
							String result = "";
							
							//The Systolic 						
							if (((float)systolic >= 0 && systolic < 70) && ((float)diastolic >= 30)){
								JOptionPane.showMessageDialog(null, "The Systolic figures are too low!");										
								System.out.println("The Systolic figures are too low!");
							}
							
							//The Diastolic figures too low				
							else if (((float)systolic > 70) && ((float)diastolic >= 0 && diastolic < 30)){							
								JOptionPane.showMessageDialog(null, "The Diastolic figures are too low!");												
								System.out.println("The Diastolic figures are too low!");
							}	
							
							//Both figures Systolic and Diastolic are too low				
							else if (((float)systolic > 0 && systolic < 70) && ((float)diastolic > 0 && diastolic < 30)){					
								JOptionPane.showMessageDialog(null,"Both figures Systolic and Diastolic are too low!");						
								System.out.println("Both figures Systolic and Diastolic are too low!");
							}
							
							//Low Blood Pressure				
							else if (((float)systolic >= 70 && systolic < 90) || ((float)diastolic >= 30 && diastolic < 60)){					
								result = ("Low Blood Pressure!");	
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);	
								
								BloodPressureTableGUI transfergui = new BloodPressureTableGUI ();
							    transfergui.main(null);
								
								
							}
							
							//Normal Blood Pressure
							else if (((float) systolic >= 90 && systolic < 120) && ((float) diastolic >= 60 && diastolic < 80 )) { 
								result = ("Normal Blood Pressure!");	
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);	
							}
							
							//Prehypertesion (High Normale)
							else if (((float) systolic >= 120 && systolic < 140) || ((float) diastolic >= 80 && diastolic < 90 )) {
								result = ("Prehypertesion (High Normale)!");					
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);	
							}
							
							//Hypertesion Stage 1
							else if (((float) systolic >= 140 && systolic < 160) || ((float) diastolic >= 90 && diastolic < 100 )) {
								result = ("Hypertesion Stage 1!");					
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);	
							}	
							
							//Hypertision Stage 2
							else if (((float) systolic >= 160 && systolic < 180) || ((float) diastolic >= 100 && diastolic < 110 )) {
								result = ("Hypertision Stage 2!");					
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);	
							}
							
							//Hypertensive Crisis (Medical Emergency)
							else if (((float) systolic >= 180 && systolic < 200) || ((float) diastolic >= 110 && diastolic < 120 )) {
								result = ("Hypertensive Crisis (Medical Emergency)!");	
								txt_bpCategory.setText("  " + result);							
								System.out.println(result);									
							}	
							
							//Wrong figures of Bllod Pressure has been entered
							else {
								JOptionPane.showMessageDialog(null, "Enter a valid Blood Pressure figures"); 
							}						
						}					
					}					
				};
				
				StreamObserver<PressureRequest> requestObserver = asyncStub.bloodPressure(responseObserver);
				requestObserver.onCompleted();				
			}
		});
		btn_submit.setBounds(415, 285, 125, 30);
		frame.getContentPane().add(btn_submit);
		
		//Label blood pressure category
		JLabel lbl_bpCategory = new JLabel("Blood Pressure Category:");
		lbl_bpCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_bpCategory.setBounds(15, 320, 180, 30);
		frame.getContentPane().add(lbl_bpCategory);
		
		//Text Field blood pressure category
		txt_bpCategory = new JTextField();
		txt_bpCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_bpCategory.setBounds(200, 325, 375, 25);
		frame.getContentPane().add(txt_bpCategory);
		txt_bpCategory.setColumns(10);	
		
		//Services separator 2		
		separator2 = new JSeparator();
		separator2.setBackground(Color.BLACK);
		separator2.setBounds(5, 360, 590, 1);
		frame.getContentPane().add(separator2);
		
		JLabel lblNewLabel = new JLabel("mmHg");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(525, 220, 60, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("mmHg");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(525, 250, 60, 25);
		frame.getContentPane().add(lblNewLabel_1);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (tglbtn_deviceOnOff.isSelected()) {
			tglbtn_deviceOnOff.setText("Off");
			monitoringDeviceOnOff(false);
		}
		
		else {
			tglbtn_deviceOnOff.setText("On");
				monitoringDeviceOnOff(true);			
		}		
	}
	
	//GRPC Unary procedure call
	//TURN ON/OFF Monitoring Device		
	public void monitoringDeviceOnOff(boolean monitoringDeviceOnOff) {
		
		DeviceRequest request = DeviceRequest.newBuilder().setDeviceStatus(monitoringDeviceOnOff).build();
		DeviceResponse response = blockingStub.monitoringDeviceOnOff(request);
	
		Boolean status = response.getDeviceStatus();
		
		if (status) {
			txt_showStatus.setText("On");
			System.out.println("The device has been turned: On");
		}
		
		else {
			txt_showStatus.setText("Off");
			System.out.println("The device has been turned: Off");
		}
		
	}
}
