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
import javax.swing.SwingConstants;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import patientMonitoring.DeviceRequest;
import patientMonitoring.DeviceResponse;
import patientMonitoring.PatientMonitoringServiceGrpc;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceBlockingStub;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceStub;

import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JTextField;

public class healthServiceGUI implements ActionListener {
	
	private static PatientMonitoringServiceBlockingStub blockingStub;
	private static PatientMonitoringServiceStub asyncStub;
	
	private ServiceInfo serviceInfo;

	private JFrame frmSmartHealthEnvironment;
	
	JToggleButton tglbtn_deviceOnOff;
	private JTextField txt_showStatus;	
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					healthServiceGUI window = new healthServiceGUI();
					window.frmSmartHealthEnvironment.setVisible(true);
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
		frmSmartHealthEnvironment = new JFrame();
		frmSmartHealthEnvironment.getContentPane().setBackground(Color.CYAN);
		frmSmartHealthEnvironment.setType(Type.UTILITY);
		frmSmartHealthEnvironment.setFont(new Font("Dialog", Font.BOLD, 20));
		frmSmartHealthEnvironment.setTitle("Smart Health Environment");
		frmSmartHealthEnvironment.setBounds(100, 100, 614, 483);
		frmSmartHealthEnvironment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmartHealthEnvironment.getContentPane().setLayout(null);
		
		
		////////////////////////////////////////
		/// Pacient Monitoring Change Status ///
		////////////////////////////////////////
		
		//Main Label (Service Name)
		JLabel lbl_serviceName = new JLabel("Patient Monitoring Service");
		lbl_serviceName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_serviceName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_serviceName.setBounds(170, 10, 280, 40);
		frmSmartHealthEnvironment.getContentPane().add(lbl_serviceName);
		
		//Lalel Change Status
		JLabel lbl_changeStatus = new JLabel("Turn the Device:");
		lbl_changeStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_changeStatus.setBounds(40, 80, 180, 25);
		frmSmartHealthEnvironment.getContentPane().add(lbl_changeStatus);
		
		//Toggle button turn monitoring device On/Off
		tglbtn_deviceOnOff = new JToggleButton("On");
		tglbtn_deviceOnOff.setFont(new Font("Tahoma", Font.BOLD, 18));
		tglbtn_deviceOnOff.setBounds(220, 70, 70, 40);
		frmSmartHealthEnvironment.getContentPane().add(tglbtn_deviceOnOff);
		tglbtn_deviceOnOff.addActionListener(this);
		
		//Label info device status	
		JLabel lbl_infoStatus = new JLabel("Device Status:");
		lbl_infoStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_infoStatus.setBounds(300, 80, 160, 25);
		frmSmartHealthEnvironment.getContentPane().add(lbl_infoStatus);
		
		//Text field show device status
		txt_showStatus = new JTextField();
		txt_showStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txt_showStatus.setBackground(Color.WHITE);
		txt_showStatus.setEditable(false);
		txt_showStatus.setFont(new Font("Tahoma", Font.BOLD, 20));
		txt_showStatus.setBounds(460, 75, 45, 35);
		frmSmartHealthEnvironment.getContentPane().add(txt_showStatus);
		txt_showStatus.setColumns(10);		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(69, 167, 45, 13);
		frmSmartHealthEnvironment.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(56, 203, 96, 19);
		frmSmartHealthEnvironment.getContentPane().add(textField);
		textField.setColumns(10);
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
