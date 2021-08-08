package patientAccommodationService;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.JFrame;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceBlockingStub;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceStub;

public class accomodationGUI {

	private JFrame frame;
	
	private static PatientAccommodationServiceBlockingStub accommodationBlockingStub;
	private static PatientAccommodationServiceStub accommodationAsyncStub;
	
	private ServiceInfo accommodationServiceInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					accomodationGUI window = new accomodationGUI();
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
	public accomodationGUI() {
		
		String accomm_service_type = "_pat_accommod._tcp.local.";
		discoveryPatientAccommodationService(accomm_service_type);
		int accommodPort = accommodationServiceInfo.getPort();
		@SuppressWarnings("deprecation")
		String accommHost = accommodationServiceInfo.getHostAddress();
		
		ManagedChannel accommChannel = ManagedChannelBuilder
										.forAddress(accommHost, accommodPort)
										.usePlaintext()
										.build();
		
		accommodationBlockingStub = PatientAccommodationServiceGrpc.newBlockingStub(accommChannel);
		accommodationAsyncStub = PatientAccommodationServiceGrpc.newStub(accommChannel);
		
		
		initialize();
	}
	
public void discoveryPatientAccommodationService(String service_type) {		
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			jmdns.addServiceListener(service_type, new ServiceListener(){				
			
				@SuppressWarnings("deprecation")
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Patient Monitoring Service resolved: " + event.getInfo());
					
					accommodationServiceInfo = event.getInfo();
					int port = accommodationServiceInfo.getPort();
					
					System.out.println("Resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type: " + event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + accommodationServiceInfo.getNiceTextString());
					System.out.println("\t host: " + accommodationServiceInfo.getHostAddress());			
					
				}				

				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Patient Monitoring Service removed: " + event.getInfo());
					
				}		
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Patient Monitoring Service added: " + event.getInfo());
					
				}
			});
			
			//Wait a bit
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
