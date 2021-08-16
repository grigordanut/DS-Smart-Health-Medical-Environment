package patientMonitoringService;

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
import patientMonitoringService.PatientMonitoringServiceGrpc.PatientMonitoringServiceImplBase;

public class PatientMonitoringServer extends PatientMonitoringServiceImplBase {
	
	//private boolean updateDevice = false;		

	public static void main(String[] args) {
		
		PatientMonitoringServer patMonitorServer = new PatientMonitoringServer();
		
		Properties prop = patMonitorServer.getProperties();
		
		patMonitorServer.registerService(prop);
		
		int port = Integer.valueOf( prop.getProperty("service_port")); //#50052
		
		try {
			Server server = ServerBuilder.forPort(port)
										.addService(patMonitorServer)
										.build()
										.start();
			
			System.out.println("Patient Monitoring Server started listening on port: " + port);
			System.out.println("----------------------------------------------------------\n");	
			
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
		try (InputStream input = new FileInputStream("src/main/resources/patient_monitoring.properties")){
			
			prop = new Properties();
			
			//load a properties file
			prop.load(input);
			
			//get the properties value and print it out
			System.out.println("Monitoring Service properties ...");
			System.out.println("\t service_type: " +prop.getProperty("service_type"));
			System.out.println("\t service_name: " + prop.getProperty("service_name"));
			System.out.println("\t service_description: " + prop.getProperty("service_description"));
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
			String service_type = prop.getProperty("service_type"); //"_patient_monitoring._tcp.local.";
			String service_name = prop.getProperty("service_name"); //"patient_monitoring_service";
			int service_port = Integer.valueOf( prop.getProperty("service_port")); //#50053;
			String service_description_properties = prop.getProperty("service_description"); //"path=index.html";
			
			//Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, 
														service_name, 
														service_port, 
														service_description_properties);
			
			jmdns.registerService(serviceInfo);
			
			System.out.printf("Registering service with: type %s and name: %s \n", service_type, service_name);
			
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
	
	//Unary call
	//TURN ON/OFF Monitoring Device	
	@Override
	public void monitoringDeviceOnOff(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {		
				
		System.out.println("Receiving Monitoring Device status change request: " + request.getText());
		
		//updateDevice = !updateDevice;
		
		DeviceResponse reply = DeviceResponse.newBuilder().setValue(request.getText()).build();
		
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
		
		System.out.println("Monitoring Device status changing request completed.");
		System.out.println("----------------------------------------------------\n");		
	}
	
	//Bi-Directional
	//Blood Pressure monitoring
	public StreamObserver<PressureRequest> bloodPressure(StreamObserver<PressureResponse> responseObserver){
		return new StreamObserver<PressureRequest>() {
			
			@Override
			public void onNext(PressureRequest value) {
				
				System.out.println("Receiving Blood Pressure check request with,");				
				System.out.println("Systolic Blood Pressure value: " + value.getSystolic() + "/mmHg");
				System.out.println("Diastolic Blood Pressure value " + value.getDiastolic() + "/mmHg");					
			
				String result = "";
				
				//The Systolic figures too low				
				if ((value.getSystolic() >= 0 && value.getSystolic() < 70) && (value.getDiastolic() >= 30)){						
					result = ("The Systolic figures are too low!");					
				}				
				
				//The Diastolic figures too low				
				else if ((value.getSystolic() > 70) && (value.getDiastolic() >= 0 && value.getDiastolic() < 30)){					
					result = ("The Diastolic figures are too low!");					
				}	
				
				//Both figures Systolic and Diastolic are too low				
				else if ((value.getSystolic() >= 0 && value.getSystolic() < 70) && (value.getDiastolic() >= 0 && value.getDiastolic() < 30)){					
					result = ("Both figures Systolic and Diastolic are too low!");					
				}
				
				//Low Blood Pressure				
				else if ((value.getSystolic() >= 70 && value.getSystolic() < 90) || (value.getDiastolic() >= 30 && value.getDiastolic() < 60)){					
					result = ("Low Blood Pressure!");					
				}
				
				//Normal Blood Pressure
				else if ((value.getSystolic() >= 90 && value.getSystolic() < 120) && (value.getDiastolic() >= 60 && value.getDiastolic() < 80 )) {
					result = ("Normal Blood Pressure!");					
				}
				
				//Prehypertesion (High Normale)
				else if (((float) value.getSystolic() >= 120 && value.getSystolic() < 140) || (value.getDiastolic() >= 80 && value.getDiastolic() < 90 )) {
					result = ("Prehypertesion (High Normale)!");					
				}
				
				//Hypertesion Stage 1
				else if (((float) value.getSystolic() >= 140 && value.getSystolic() < 160) || (value.getDiastolic() >= 90 && value.getDiastolic() < 100 )) {
					result = ("Hypertesion Stage 1!");					
				}	
				
				//Hypertision Stage 2
				else if ((value.getSystolic() >= 160 && value.getSystolic() < 180) || (value.getDiastolic() >= 100 && value.getDiastolic() < 110 )) {
					result = ("Hypertision Stage 2!");					
				}
				
				//Hypertensive Crisis (Medical Emergency)
				else if ((value.getSystolic() >= 180 && value.getSystolic() < 200) || (value.getDiastolic() >= 110 && value.getDiastolic() < 120 )) {
					result = ("Hypertensive Crisis (Medical Emergency)!");					
				}	
				
				//Wrong figures of Blood Pressure has been entered
				else {
					JOptionPane.showMessageDialog(null, "Enter a valid Blood Pressure figures"); 
				}	
				
				PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
				responseObserver.onNext(reply);
			}

			@Override
			public void onError(Throwable t) {				
				t.printStackTrace();				
			}

			@Override
			public void onCompleted() {					
				
				System.out.println("Blood Pressure checking request completed.");
				System.out.println("------------------------------------------\n");
				
				//completed too
				responseObserver.onCompleted();				
			}			
		};
	}
}
