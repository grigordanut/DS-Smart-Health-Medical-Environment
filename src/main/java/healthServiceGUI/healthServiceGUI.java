package healthServiceGUI;

import java.awt.EventQueue;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientAdministrationService.CalculateRequest;
import patientAdministrationService.CalculateRequest.Room;
import patientAdministrationService.CalculateResponse;
import patientAdministrationService.DisplayRequest;
import patientAdministrationService.DisplayResponse;
import patientAdministrationService.PatientAdministrationServiceGrpc;
import patientAdministrationService.PatientAdministrationServiceGrpc.PatientAdministrationServiceBlockingStub;
import patientAdministrationService.PatientAdministrationServiceGrpc.PatientAdministrationServiceStub;
import patientAdministrationService.RegisterRequest;
import patientAdministrationService.RegisterResponse;
import patientMonitoringService.DeviceRequest;
import patientMonitoringService.DeviceResponse;
import patientMonitoringService.PatientMonitoringServiceGrpc;
import patientMonitoringService.PressureRequest;
import patientMonitoringService.PressureResponse;
import patientMonitoringService.PatientMonitoringServiceGrpc.PatientMonitoringServiceBlockingStub;
import patientMonitoringService.PatientMonitoringServiceGrpc.PatientMonitoringServiceStub;

import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import environmentService.CurrentRequest;
import environmentService.CurrentResponse;
import environmentService.EnvironmentServiceGrpc;
import environmentService.EnvironmentServiceGrpc.EnvironmentServiceBlockingStub;
import environmentService.EnvironmentServiceGrpc.EnvironmentServiceStub;
import environmentService.TempRequest;
import environmentService.TempResponse;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class healthServiceGUI implements ActionListener {
	
	//declare an array list patientList of Register class and set to null
    public static ArrayList <String> patientList = null;
    
    //Patient Administration Service
    private static PatientAdministrationServiceBlockingStub adminBlockingStub;
    private static PatientAdministrationServiceStub adminAsyncStub;     

    //Patient Monitoring Service
	private static PatientMonitoringServiceBlockingStub monitoringBlockingStub;
	private static PatientMonitoringServiceStub monitoringAsyncStub;
		
	//Environment Moonitoring Service
	private static EnvironmentServiceBlockingStub environmentBlockingStub;
	private EnvironmentServiceStub environmentAsyncStub;
	
	private ServiceInfo serviceInfo;

	private JFrame frame;		
	
	//Panel Patient Administration Service
	private JTextField txt_patientName;
	private JTextField txt_patientAge;
	private ButtonGroup btn_Group;	
	JRadioButton rdbtn_male;
	JRadioButton rdbtn_female;
	private JTextArea txtArea_patDetails;
	
	private JSeparator separator1A;
	
	private JTextField txt_patName;
	private JTextField txt_noDays;
	JTextArea textArea_totalPrice;
	
	private JSeparator separator2A;
	
	//Panel Patient Monitoring Service
	JToggleButton tglbtn_deviceOnOff;
	private JTextField txt_showStatus;	
	
	private JSeparator separator1M;	
	
	private JTextField txt_systolic;
	private JTextField txt_diastolic;
	private JTextField txt_bpCategory;
	
	private JSeparator separator2M;		
	
	//Panel Environment Monitoring Service
	JTextArea textArea_showTemp;
	private JTextField txt_setTemp;
	Integer setTemp = null;
	
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
		
		//check if the array list patientList is empty
        if (patientList==null)   
            
        //initalize a new Array List patientList
        patientList = new ArrayList<String>(); 	
        
        //Discover the services based of service_type		
        String service_type = "_medical._tcp.local.";
        discoveyMedicalServices(service_type);
                
        @SuppressWarnings("deprecation")
		String host = serviceInfo.getHostAddress();
        
        //Discovering Environment Monitoring Service		
      	ManagedChannel environmentChannel = ManagedChannelBuilder
      										.forAddress(host, 50055)
      										.usePlaintext()
      										.build();		
      	environmentBlockingStub = EnvironmentServiceGrpc.newBlockingStub(environmentChannel);
      	environmentAsyncStub = EnvironmentServiceGrpc.newStub(environmentChannel);       
        
        //Discovering Patient Administration Service
        ManagedChannel adminChannel = ManagedChannelBuilder
        							.forAddress(host, 50052)
        							.usePlaintext()
        							.build();
        
        adminBlockingStub = PatientAdministrationServiceGrpc.newBlockingStub(adminChannel);
        adminAsyncStub = PatientAdministrationServiceGrpc.newStub(adminChannel);
        
        //Discovering Patient Monitoring Service	
		ManagedChannel monitoringChannel = ManagedChannelBuilder
										.forAddress(host, 50053)
										.usePlaintext()
										.build();
		
		monitoringBlockingStub = PatientMonitoringServiceGrpc.newBlockingStub(monitoringChannel);
		monitoringAsyncStub = PatientMonitoringServiceGrpc.newStub(monitoringChannel);		
		
		initialize();
	}
	
	private void discoveyMedicalServices(String service_type) {		
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			jmdns.addServiceListener(service_type, new ServiceListener(){
				
				@SuppressWarnings("deprecation")
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Patient Administration Service resolved: " + event.getInfo());
					
					serviceInfo = event.getInfo();
					int port = serviceInfo.getPort();
					
					System.out.println("Resolving " + service_type + "with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type: " + event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + serviceInfo.getNiceTextString());
					System.out.println("\t host: " + serviceInfo.getHostAddress());
					System.out.println("--------------------------------------------------\n");
				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Patient Administration Service removed: " +event.getInfo());	
					System.out.println("--------------------------------------------------\n");
				}

				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Patient Administration Service added: " +event.getInfo());	
					System.out.println("--------------------------------------------------\n");
				}				
			});
			
			//Wait a bit
			Thread.sleep(2000);
			
			jmdns.close();
		}
		catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}catch (IOException e) {
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
		frame.getContentPane().setBackground(new Color(234, 234, 234));
		frame.setFont(new Font("Dialog", Font.BOLD, 20));
		frame.setBounds(100, 100, 635, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 50, 605, 495);
		frame.getContentPane().add(tabbedPane);
		
		//Main Label Application Title
		JLabel lbl_appTitle = new JLabel("Smart Medical Environment");
		lbl_appTitle.setForeground(Color.WHITE);
		lbl_appTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_appTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_appTitle.setBounds(150, 5, 300, 35);
		frame.getContentPane().add(lbl_appTitle);
		
		/////////////////////////////////////////////
		/// Patient Administration Service        ///
		/////////////////////////////////////////////		
		
		//Panel Administration Service
		JPanel panel_administration = new JPanel();
		panel_administration.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_administration.setFont(new Font("Tahoma", Font.BOLD, 18));
		tabbedPane.addTab("Patient Administration Service", null, panel_administration, "Accomodation Service");
		panel_administration.setLayout(null);		
		
		/////////////////////////
		/// Register Patients ///
		/////////////////////////
		
		//Main Label Patient Registering Service
		JLabel lbl_administrationService = new JLabel("Patient Registering Service");
		lbl_administrationService.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_administrationService.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_administrationService.setBounds(170, 10, 280, 25);
		panel_administration.add(lbl_administrationService);
		
		//Label patient name		
		JLabel lbl_patientName = new JLabel("Patient Name");
		lbl_patientName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_patientName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_patientName.setBounds(20, 45, 110, 25);
		panel_administration.add(lbl_patientName);
		
		//Text Field patient Name	
		txt_patientName = new JTextField();
		txt_patientName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtArea_patDetails.setText("");
			}
		});
		txt_patientName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_patientName.setBounds(20, 70, 200, 25);
		panel_administration.add(txt_patientName);
		txt_patientName.setColumns(10);
		
		//Label patient age
		JLabel lbl_patientAge = new JLabel("Age");
		lbl_patientAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_patientAge.setBounds(245, 45, 35, 25);
		panel_administration.add(lbl_patientAge);
		
		//Text Field patient age
		txt_patientAge = new JTextField();		
		txt_patientAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_patientAge.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtArea_patDetails.setText("");
			}
		});
		
		txt_patientAge.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
	            if((key>=KeyEvent.VK_0 && key<=KeyEvent.VK_9)||(key>=KeyEvent.VK_NUMPAD0&&key<=KeyEvent.VK_NUMPAD9)||key==KeyEvent.VK_BACK_SPACE){
	            	txt_patientAge.setEditable(true);
	            }
	            
	            else{	                
	                //show message box                 
	                JOptionPane.showMessageDialog(null, "Enter numbers only, letters or any special characters are not allowed"); 
	                txt_patientAge.setEditable(true);
	                txt_patientAge.setText("");
	                txt_patientAge.requestFocus();
	            }
			}
		});
		txt_patientAge.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_patientAge.setBounds(240, 70, 40, 25);
		panel_administration.add(txt_patientAge);
		txt_patientAge.setColumns(10);		
		
		//Radio Button male
		rdbtn_male = new JRadioButton("Male");
		rdbtn_male.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_male.setBounds(300, 50, 90, 25);
		panel_administration.add(rdbtn_male);
		
		//Radio Button female
		rdbtn_female = new JRadioButton("Female");
		rdbtn_female.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtn_female.setBounds(300, 75, 90, 25);
		panel_administration.add(rdbtn_female);
		
		btn_Group = new ButtonGroup();	
		
		btn_Group.add(rdbtn_male);
		btn_Group.add(rdbtn_female);
		
		JButton btn_register = new JButton("Submit");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String patName = "";
				String patAge = "";
				String gender = "";
				
				if (txt_patientName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Patient Name?");	
					txt_patientName.requestFocus();
				}
				
				else if (txt_patientAge.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Patient Age?");
					txt_patientAge.requestFocus();
				}
				
				else if(btn_Group.getSelection()==null){
					JOptionPane.showMessageDialog(null, "Please select Patient gender?");
				}   
				
				else {		
				
					patName = txt_patientName.getText();
					patAge = txt_patientAge.getText();				
					
					if(rdbtn_male.isSelected()){
		                gender = "Male";
		            }
		            else if (rdbtn_female.isSelected()){
		                gender= "Female";
		            } 					
					
					txt_patientName.setText("");
					txt_patientAge.setText("");
					btn_Group.clearSelection();					
					
					StreamObserver<RegisterResponse> responseObserver = new StreamObserver<RegisterResponse>() {

						@Override
						public void onNext(RegisterResponse value) {
							System.out.println("Server responded; Patient registered with, \n" +value.getResult() + "\n");
							txtArea_patDetails.append(" " + value.getResult());
							patientList.add(value.getResult());												
						}

						@Override
						public void onError(Throwable t) {
							t.printStackTrace();							
						}

						@Override
						public void onCompleted() {
							System.out.println("Patient registration completed.");
							System.out.println("-------------------------------\n");							
						}						
					};
					
					StreamObserver<RegisterRequest> requestObserver = adminAsyncStub.registerPatient(responseObserver);
					requestObserver.onNext(RegisterRequest.newBuilder()
														.setName(patName)
														.setAge(patAge)
														.setGender(gender)
														.build());
					
					//Mark the end of requests
					requestObserver.onCompleted();						
				}				
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_register.setBounds(425, 55, 140, 50);
		panel_administration.add(btn_register);
		
		//////////////////////////
		/// Show Patients list ///
		//////////////////////////
		
		//Button show Patients list
		JButton btn_patList = new JButton("Patient List");
		btn_patList.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_patList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtArea_patDetails.setText(" ");	
				
				//check if Array List is empty
		        if (patientList.isEmpty()){
		            JOptionPane.showMessageDialog(null,"Nothing to display please press SAVE button first!!");
		            txt_patientName.requestFocus();
		        }  
		        
		        else{ 		        
		        	
		        	System.out.println("Server responded; Patient list is displayed:\n");
		        		
		        	DisplayRequest request = DisplayRequest.newBuilder().setPatList(patientList.toString()).build();		        	
		            	
		            StreamObserver<DisplayResponse> responseObserver = new StreamObserver<DisplayResponse>() {

						@Override
						public void onNext(DisplayResponse value) {
							
						}

						@Override
						public void onError(Throwable t) {
								t.printStackTrace();				
						}

						@Override
						public void onCompleted() {
							System.out.println("The display of the patient list has been completed.");
							System.out.println("---------------------------------------------------\n");
						}			
					};
											
					for(int i = 0; i<patientList.size(); i++) {	
						
						txtArea_patDetails.append(patientList.get(i) + "\n ");
						System.out.println(patientList.get(i) + "\n");						
		            }	 
					
					adminAsyncStub.displayPatients(request, responseObserver);	 				
		        }    
			}
		});
		btn_patList.setBounds(425, 130, 140, 50);
		panel_administration.add(btn_patList);
		
		//Text Area show Patient Details
		txtArea_patDetails = new JTextArea();
		txtArea_patDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtArea_patDetails.setBounds(20, 105, 385, 75);
		txtArea_patDetails.setEditable(false); // set textArea non-editable
		panel_administration.add(txtArea_patDetails);	
		
		//Add Scroll to Text Area
		JScrollPane scroll = new JScrollPane(txtArea_patDetails);
		scroll.setSize(385, 75);
		scroll.setLocation(20, 105);
		panel_administration.add(scroll);
		
		//Services separator 1 Administration	
		separator1A = new JSeparator();
		separator1A.setBackground(Color.BLACK);
		separator1A.setBounds(5, 200, 590, 1);
		panel_administration.add(separator1A);
		
		/////////////////////////////////////////////
		/// Calculate Patient Accommodation price ///
		/////////////////////////////////////////////
		
		//Main Label Calculate Patient Accommodation price
		JLabel lbl_calculatePrice = new JLabel("Calculate Accomodation Price");
		lbl_calculatePrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_calculatePrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_calculatePrice.setBounds(170, 210, 280, 25);
		panel_administration.add(lbl_calculatePrice);
		
		//Label patient name
		JLabel lbl_patName = new JLabel("Patient Name");
		lbl_patName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_patName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_patName.setBounds(20, 250, 110, 25);
		panel_administration.add(lbl_patName);
		
		//Text Field patient name
		txt_patName = new JTextField();
		txt_patName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				textArea_totalPrice.setText("");	
				
			}
		});
		txt_patName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_patName.setBounds(20, 275, 190, 25);
		panel_administration.add(txt_patName);
		txt_patName.setColumns(10);
		
		//Label number of days
		JLabel lbl_noDays = new JLabel("No. Days");
		lbl_noDays.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_noDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_noDays.setBounds(215, 245, 70, 25);
		panel_administration.add(lbl_noDays);
		
		//Text Field number of days of accommodation
		txt_noDays = new JTextField();		
		txt_noDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_noDays.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_noDays.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				textArea_totalPrice.setText(null);
				
			}
		});
		
		txt_noDays.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				int key = e.getKeyCode();
	            if((key>=KeyEvent.VK_0 && key<=KeyEvent.VK_9)||(key>=KeyEvent.VK_NUMPAD0&&key<=KeyEvent.VK_NUMPAD9)||key==KeyEvent.VK_BACK_SPACE){
	            	txt_noDays.setEditable(true);
	            }
	            
	            else{	                
	                //show message box                 
	                JOptionPane.showMessageDialog(null, "Enter numbers only, letters or any special characters are not allowed"); 
	                txt_noDays.setEditable(true);
	                txt_noDays.setText("");
	                txt_noDays.requestFocus();
	            }				
			}
		});
		txt_noDays.setBounds(225, 275, 50, 25);
		panel_administration.add(txt_noDays);
		txt_noDays.setColumns(10);
		
		//Label priece per day of accommodation
		JLabel lbl_piceDay = new JLabel("Room Type");
		lbl_piceDay.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_piceDay.setBounds(300, 245, 95, 25);
		lbl_piceDay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_administration.add(lbl_piceDay);
		
		//Combobox type of accommodation
		JComboBox comboBox_price = new JComboBox();
		comboBox_price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox_price.setModel(new DefaultComboBoxModel(new String[] {"PUBLIC", "SEMIPRIVATE","PRIVATE"}));
		comboBox_price.setBounds(290, 275, 135, 25);
		panel_administration.add(comboBox_price);
		
		//Button calculate the price of the accommodation
		JButton btn_totalPrice = new JButton("Total Price");
		btn_totalPrice.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_totalPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textArea_totalPrice.setText(" ");
				
				String patientName = " ";
				int numberDays = (int)0.00;
				
				if (txt_patName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter Patient Name?");
					txt_patName.requestFocus();
				}
				
				else if (txt_noDays.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the Number of Days?");
					txt_noDays.requestFocus();
				}
				
				else {
					patientName = (txt_patName.getText());
					numberDays = Integer.parseInt(txt_noDays.getText());
					
					int index = comboBox_price.getSelectedIndex();
					Room room = Room.forNumber(index);
					
					CalculateRequest request = CalculateRequest.newBuilder()
													.setPatName(patientName)
													.setNumberDays(numberDays)
													.setRoom(room)
													.build();
									
					CalculateResponse response = adminBlockingStub.calculatePrice(request);
					
					textArea_totalPrice.append(response.getMessage());
					System.out.println("Server responded; Patient's accommodation price calculated,\n" 
																				+ response.getMessage());
					
					System.out.println("Calculation of the patient's accommodation price completed.");
					System.out.println("-----------------------------------------------------------\n");						
				}				
			}
		});
		btn_totalPrice.setBounds(440, 250, 135, 50);
		panel_administration.add(btn_totalPrice);
		
		////Text Area show the pacient accommodation price 
		textArea_totalPrice = new JTextArea();
		textArea_totalPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_totalPrice.setEditable(false);
		textArea_totalPrice.setBounds(20, 315, 555, 50);
		panel_administration.add(textArea_totalPrice);
		
		//Services separator 2 Administration	
		separator2A = new JSeparator();
		separator2A.setBackground(Color.BLACK);
		separator2A.setBounds(5, 385, 590, 1);
		panel_administration.add(separator2A);		
		
		/////////////////////////////////////////////
		/// Patient Monitoring Service            ///
		/////////////////////////////////////////////

		//Panel Patient Monitoring Service
		JPanel panel_monitoring = new JPanel();
		panel_monitoring.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_monitoring.setFont(new Font("Tahoma", Font.BOLD, 18));
		tabbedPane.addTab("Patient Monitoring Service", null, panel_monitoring, "Monitoring Service");
		panel_monitoring.setLayout(null);			

		/////////////////////////////////////////
		/// Turn the Monitoring Device On/Off ///
		/////////////////////////////////////////

		//Main Label Patient Monitoring Service
		JLabel lbl_monitoringService = new JLabel("Patient Monitoring Device");
		lbl_monitoringService.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_monitoringService.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_monitoringService.setBounds(170, 10, 280, 25);
		panel_monitoring.add(lbl_monitoringService);		

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
		panel_monitoring.add(textA_device);

		//Label Change Status
		JLabel lbl_changeStatus = new JLabel("Turn the Device:");
		lbl_changeStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_changeStatus.setBounds(330, 60, 120, 25);
		panel_monitoring.add(lbl_changeStatus);

		//Toggle button turn monitoring device On/Off
		tglbtn_deviceOnOff = new JToggleButton("On");
		tglbtn_deviceOnOff.setFont(new Font("Tahoma", Font.BOLD, 18));
		tglbtn_deviceOnOff.setBounds(460, 50, 120, 40);
		panel_monitoring.add(tglbtn_deviceOnOff);
		tglbtn_deviceOnOff.addActionListener(this);

		//Label info device status	
		JLabel lbl_infoStatus = new JLabel("Device Status:");
		lbl_infoStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_infoStatus.setBounds(330, 125, 120, 25);
		panel_monitoring.add(lbl_infoStatus);

		//Text field show device status
		txt_showStatus = new JTextField();
		txt_showStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txt_showStatus.setBackground(Color.WHITE);
		txt_showStatus.setEditable(false);
		txt_showStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		txt_showStatus.setBounds(460, 115, 120, 40);
		panel_monitoring.add(txt_showStatus);
		txt_showStatus.setColumns(10);		
	
		//Services separator 1 Monitoring		
		separator1M = new JSeparator();
		separator1M.setBackground(Color.BLACK);
		separator1M.setBounds(5, 180, 590, 1);
		panel_monitoring.add(separator1M);		
	
		//////////////////////////////
		/// Blood Pressure Results ///
		//////////////////////////////
	
		//Blood Pressure Text Area
		JTextArea textA_bloodPressure = new JTextArea();
		textA_bloodPressure.setEditable(false);
		textA_bloodPressure.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textA_bloodPressure.setBounds(15, 230, 315, 125);
		textA_bloodPressure.setText("  Measure your blood pressure to: "
			+ "\r\n -helps health team to diagnose any "
			+ "\r\n health problems early. "
			+ "\r\n Your health care team can take steps: "
			+ "\r\n -to control your blood pressure"
			+ "\r\n -if it is too low or too high.");
		panel_monitoring.add(textA_bloodPressure);
	
		//Label Blood Pressure Results		
		JLabel lbl_bpResults = new JLabel("Blood Pressure Resuts");
		lbl_bpResults.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bpResults.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_bpResults.setBounds(380, 230, 190, 25);
		panel_monitoring.add(lbl_bpResults);
	
		//Label systolic results
		JLabel lbl_systolic = new JLabel("Systolic:");
		lbl_systolic.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_systolic.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_systolic.setBounds(355, 260, 75, 25);
		panel_monitoring.add(lbl_systolic);		
	
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
		txt_systolic.setBounds(430, 260, 90, 25);
		panel_monitoring.add(txt_systolic);
		txt_systolic.setColumns(10);		
	
		//Label diastolic results
		JLabel lblNewLabel_2 = new JLabel("Diastolic");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(355, 290, 75, 25);
		panel_monitoring.add(lblNewLabel_2);
	
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
		txt_diastolic.setBounds(430, 290, 90, 25);
		panel_monitoring.add(txt_diastolic);
		txt_diastolic.setColumns(10);
	
		JButton btn_submit = new JButton("Submit");
		btn_submit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
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
	
					StreamObserver<PressureResponse> responseObserver = new StreamObserver<PressureResponse>() {
	
						@Override
						public void onNext(PressureResponse value) {
							txt_bpCategory.setText(value.getResult());	
							System.out.println("Server responded; The result of blood checking is: " + value.getResult());
						}
	
						@Override
						public void onError(Throwable t) {
							t.printStackTrace();	
						}
	
						@Override
						public void onCompleted() {							
							System.out.println("Blood Pressure check completed.");
							System.out.println("-------------------------------");
						}					
					};
	
					StreamObserver<PressureRequest> requestObserver = monitoringAsyncStub.bloodPressure(responseObserver);
					requestObserver.onNext(PressureRequest.newBuilder().setSystolic(systolic).setDiastolic(diastolic).build());				
				
					//Mark the end requests
					requestObserver.onCompleted();				
				}
			}
		});
		btn_submit.setBounds(415, 325, 125, 30);
		panel_monitoring.add(btn_submit);
	
		//Label blood pressure category
		JLabel lbl_bpCategory = new JLabel("Blood Pressure Category:");
		lbl_bpCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_bpCategory.setBounds(15, 360, 180, 30);
		panel_monitoring.add(lbl_bpCategory);
	
		//Text Field blood pressure category
		txt_bpCategory = new JTextField();
		txt_bpCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_bpCategory.setBounds(200, 365, 380, 25);
		panel_monitoring.add(txt_bpCategory);
		txt_bpCategory.setColumns(10);	
	
		//Services separator 2		
		separator2M = new JSeparator();
		separator2M.setBackground(Color.BLACK);
		separator2M.setBounds(5, 400, 590, 1);
		panel_monitoring.add(separator2M);
	
		JLabel lblNewLabel = new JLabel("mmHg");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(525, 260, 60, 25);
		panel_monitoring.add(lblNewLabel);
	
		JLabel lblNewLabel_1 = new JLabel("mmHg");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(525, 290, 60, 25);
		panel_monitoring.add(lblNewLabel_1);		
		
		JLabel lblNewLabel_3 = new JLabel("Blood Pressure Monitoring");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(170, 190, 280, 25);
		panel_monitoring.add(lblNewLabel_3);
		
		
		/////////////////////////////////
		/// Smart Medical Environment ///
		/////////////////////////////////		
		
		//Pane Environment Service
		JPanel panel_environment = new JPanel();
		panel_environment.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_environment.setFont(new Font("Tahoma", Font.BOLD, 18));
		tabbedPane.addTab("Environment Monitoring Service", null, panel_environment, null);
		panel_environment.setLayout(null);		
		
		//Main Lebel Environment service
		JLabel lblNewLabel_4 = new JLabel("Environt Monitoring Service");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setBounds(170, 30, 280, 40);
		panel_environment.add(lblNewLabel_4);
		
		//Button show current temperature
		JButton btn_shoeTemp = new JButton("Show Current Temp");
		btn_shoeTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_showTemp.setText(null);				
				
				if (setTemp == null) {	
					
					int curentTemp = (int)20;
					
					CurrentRequest request = CurrentRequest.newBuilder().setCurrent(curentTemp).build();
					
					CurrentResponse response = environmentBlockingStub.getCurrentRoomTemp(request);									
					textArea_showTemp.append(" " + response.getCurrentNew());	
					System.out.println(response.getCurrentNew());	
					System.out.println("Displaying the current temperature has been completed.");
					System.out.println("------------------------------------------------------\n");
				}
				
				else {	        	
	        	
					CurrentRequest request = CurrentRequest.newBuilder().setCurrent(setTemp).build();
				
					CurrentResponse response = environmentBlockingStub.getCurrentRoomTemp(request);										
					textArea_showTemp.append(" " + response.getCurrentNew());	
					System.out.println(response.getCurrentNew());	
					System.out.println("Displaying the current temperature has been completed.");
					System.out.println("------------------------------------------------------\n");
				}    		
			}
		});
		btn_shoeTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_shoeTemp.setBounds(35, 100, 220, 40);
		panel_environment.add(btn_shoeTemp);
		
		//Text area shoew the temperature vales
		textArea_showTemp = new JTextArea();
		textArea_showTemp.setEditable(false);
		textArea_showTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_showTemp.setBounds(265, 100, 300, 40);
		panel_environment.add(textArea_showTemp);
		
		//Button show the set temperature
		JButton btn_setTemp = new JButton("Set New Temp");
		btn_setTemp.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_setTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int minTemp = (int) 16;
				int maxTemp = (int) 24;
				
				textArea_showTemp.setText(" ");				
				
				if (txt_setTemp.getText().isEmpty()){
					
					JOptionPane.showMessageDialog(null, "Plase enter the new temperature");
					txt_setTemp.requestFocus();
				}
				
				else {
					setTemp = Integer.parseInt(txt_setTemp.getText().toString());
					
					if (setTemp < minTemp) {
						TempRequest request = TempRequest.newBuilder().setTemp(setTemp).build();
						
						TempResponse response = environmentBlockingStub.setRoomTemp(request);
						JOptionPane.showMessageDialog(null, response.getTempNew());
						System.out.println(response.getTempNew());	
						
						System.out.println("Changing the environment temperature has been completed.");
						System.out.println("--------------------------------------------------------\n");						
					}
					
					else if (setTemp > maxTemp) {
						TempRequest request = TempRequest.newBuilder().setTemp(setTemp).build();
						
						TempResponse response = environmentBlockingStub.setRoomTemp(request);
						JOptionPane.showMessageDialog(null, response.getTempNew());
						System.out.println(response.getTempNew());	
						
						System.out.println("Changing the environment temperature has been completed.");
						System.out.println("--------------------------------------------------------\n");						
					}
					
					else {
						TempRequest request = TempRequest.newBuilder().setTemp(setTemp).build();
						
						TempResponse response = environmentBlockingStub.setRoomTemp(request);
						textArea_showTemp.append(response.getTempNew());
						System.out.println(response.getTempNew());	
						
						System.out.println("Changing the environment temperature has been completed.");
						System.out.println("--------------------------------------------------------\n");						
					}					
				}				
			}
		});
		btn_setTemp.setBounds(35, 180, 220, 40);
		panel_environment.add(btn_setTemp);
		
		//Text field enter new temperature
		txt_setTemp = new JTextField();
		txt_setTemp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea_showTemp.setText(" ");
			}
		});
		txt_setTemp.setHorizontalAlignment(SwingConstants.RIGHT);
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
		txt_setTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_setTemp.setBounds(265, 180, 100, 40);
		panel_environment.add(txt_setTemp);
		txt_setTemp.setColumns(10);		
	}

	//Toggle Button On/Off
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String statusOn = "On";
		String statusOff = "Off";
		
		if (tglbtn_deviceOnOff.isSelected()) {
			tglbtn_deviceOnOff.setText("Off");
			DeviceRequest request = DeviceRequest.newBuilder().setText(statusOn).build();
			DeviceResponse response = monitoringBlockingStub.monitoringDeviceOnOff(request);			
			txt_showStatus.setText(response.getValue());
			System.out.println("The Monitoring Device has been turned: " + response.getValue());
			System.out.println("Changing the status of the monitoring device has been completed.");
			System.out.println("----------------------------------------------------------------");
		}
	
		else {
			tglbtn_deviceOnOff.setText("On");
			DeviceRequest request = DeviceRequest.newBuilder().setText(statusOff).build();
			DeviceResponse response = monitoringBlockingStub.monitoringDeviceOnOff(request);			
			txt_showStatus.setText(response.getValue());
			System.out.println("The Monitoring Device has been turned: " + response.getValue());
			System.out.println("Changing the status of the monitoring device has been completed.");
			System.out.println("----------------------------------------------------------------");						
		}		
	}
}
