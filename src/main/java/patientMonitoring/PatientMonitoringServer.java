package patientMonitoring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.swing.JOptionPane;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceImplBase;

public class PatientMonitoringServer extends PatientMonitoringServiceImplBase {
	
	private boolean updateDevice = false;		
	

	public static void main(String[] args) {
		
		PatientMonitoringServer patMonitoringServer = new PatientMonitoringServer();
		
		Properties prop = patMonitoringServer.getProperties();
		
		patMonitoringServer.registerService(prop);
		
		int port = Integer.valueOf( prop.getProperty("service_port")); //#50051
		
		try {
			Server server = ServerBuilder.forPort(port)
										.addService(patMonitoringServer)
										.build().start();
			
			System.out.println("Patient Monitoring Server started listening on port: " +port);
			
			server.awaitTermination();			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private Properties getProperties() {
		
		Properties prop = null;
		
		//Define the input properties path		
		try (InputStream input = new FileInputStream("src/main/resources/patientMonitoring.properties")){
			
			prop = new Properties();
			
			//load a properties file
			prop.load(input);
			
			//get the properties value and print it out
			System.out.println("Supplying Setvice properties ...");
			System.out.println("\t service_type: " +prop.getProperty("service_type"));
			System.out.println("\t service_name: " + prop.getProperty("service_name"));
			System.out.println("\t servive_description: " + prop.getProperty("service_description"));
			System.out.println("\t service_port: " + prop.getProperty("service_port"));			
			
		} catch (IOException ex) {			
			ex.printStackTrace();
		} 
		
		return prop;		
	}
	
	public void registerService(Properties prop ) {
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			/*
			 * Setting service information - prepare parameters for creating the ServiceInfo
			 */
			//Assume that there is registering an http server
			
			//Assume that there is registering an http server
			String service_type = prop.getProperty("service_type"); //"_patientMonitoring._tcp.local.";
			String service_name = prop.getProperty("service_name"); //"patientMonitoring_service";
			int service_port = Integer.valueOf( prop.getProperty("service_port")); //#50051;
			String service_description_properties = prop.getProperty("service_description"); //"path=index.html";
			
			//Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, 
														service_name, 
														service_port, 
														service_description_properties);
			
			jmdns.registerService(serviceInfo);
			
			System.out.printf("Registering servive with type %s and name %s \n", service_type, service_name);
			
			//Wait a bit
			Thread.sleep(1000);
			
			
			//System.out.println("Ready to unregister services");
			
			//Unregister all services
			//jmdns.unregisterAllServices();
			//jmdns.unregisterService(serviceInfo);			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	//GRPC Unary procedure call
	//TURN ON/OFF Monitoring Device	
	@Override
	public void monitoringDeviceOnOff(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {
		
		System.out.println("Receiving request for changing supplying status!");
		
		updateDevice = !updateDevice;
		
		if (updateDevice) {
			System.out.println("Device turned: On");
		}
		
		else {
			System.out.println("Device turned: Off!");
		}
		
		DeviceResponse response = DeviceResponse.newBuilder().setDeviceStatus(updateDevice).build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		
	}
	
	//Bidirectional
	public StreamObserver<PressureRequest> bloodPressure(StreamObserver<PressureResponse> responseObserver){
		return new StreamObserver<PressureRequest>() {

			float systolic = (float) 110.0;
			float diastolic = (float) 70.0;
			
			@Override
			public void onNext(PressureRequest value) {
				
				System.out.println("Blood Pressure results with systolic: " + value.getSystolic() +"/mmHg");
				System.out.println("Blood Pressure results with diastolic: " + value.getDiastolic() + "/mmHg");
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCompleted() {
				
				System.out.println("Receiving systolic value: " + systolic + "/mmHg");
				System.out.println("Receiving diastolic value: " + diastolic + "/mmHg");
				
				String result = "";
				
				if (((int)systolic >= 0 && systolic < 70) && ((int)diastolic >= 30)){
					JOptionPane.showMessageDialog(null, "The Systolic figures are too low!");
					
					result = ("The Systolic figures are too low!");
					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				
				//Low Blood Presure				
				else if (((int)systolic > 70) && ((int)diastolic >= 0 && diastolic < 30)){					
					JOptionPane.showMessageDialog(null, "The Diastolic are too low!"); 
				}				
				//Low Blood Presure				
				else if (((int)systolic >= 0 && systolic < 70) && ((int)diastolic >= 0 && diastolic < 30)){					
					JOptionPane.showMessageDialog(null, "Both figures Systolic and Diastolic are too low!"); 
				}
				
				//Low Blood Presure				
				else if (((int)systolic >= 70 && systolic < 90) || ((int)diastolic >= 30 && diastolic < 60)){					
					JOptionPane.showMessageDialog(null, "Low Blood Presure!"); 
				}
				//Normal Blood Presure
				else if (((int) systolic >= 90 && systolic < 120) && ((int) diastolic >= 60 && diastolic < 80 )) {
					JOptionPane.showMessageDialog(null, "Normal Blood Presure!"); 
				}
				//Prehypertision (High Normale)
				else if (((int) systolic >= 120 && systolic < 120) && ((int) diastolic >= 80 && diastolic < 90 )) {
					JOptionPane.showMessageDialog(null, "Prehypertision (High Normale)!"); 
				}				
				//Hypertision Stage 1
				else if (((int) systolic >= 140 && systolic < 160) && ((int) diastolic >= 90 && diastolic < 100 )) {
					JOptionPane.showMessageDialog(null, "Hypertision Stage 1!"); 
				}				
				//Hypertision Stage 2
				else if (((int) systolic >= 160 && systolic < 180) && ((int) diastolic >= 100 && diastolic < 110 )) {
					JOptionPane.showMessageDialog(null, "Hypertision Stage 2!"); 
				}
				//Hypertensive Crisis (Medical Emergency)
				else if (((int) systolic >= 180 && systolic < 200) && ((int) diastolic >= 110 && diastolic < 120 )) {
					JOptionPane.showMessageDialog(null, "Hypertision Stage 2!"); 
				}	
				
				else {
					JOptionPane.showMessageDialog(null, "Enter a valid blood Presure figures"); 
				}				
				
				responseObserver.onCompleted();
				
			}
			
		};
	}

}
