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
	
	private boolean updateDevice = false;		

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
			String service_type = prop.getProperty("service_type"); //"_patientMonitoring._tcp.local.";
			String service_name = prop.getProperty("service_name"); //"patientMonitoring_service";
			int service_port = Integer.valueOf( prop.getProperty("service_port")); //#50052;
			String service_description_properties = prop.getProperty("service_description"); //"path=index.html";
			
			//Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, 
														service_name, 
														service_port, 
														service_description_properties);
			
			jmdns.registerService(serviceInfo);
			
			System.out.printf("Registering service with type %s and name %s \n", service_type, service_name);
			
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
	
	//GRPC Unary remote procedure call
	//TURN ON/OFF Monitoring Device	
	@Override
	public void monitoringDeviceOnOff(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {
		
		System.out.println("Received request for changing Monititoring Device status!");
		
		updateDevice = !updateDevice;
		
		if (updateDevice) {
			System.out.println("The Device has been turned: On");
		}
		
		else {
			System.out.println("The Device has been turned: Off!");
		}
		
		DeviceResponse reply = DeviceResponse.newBuilder().setDeviceStatus(updateDevice).build();
		
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
		
	}
	
	//GRPC Bidirectional remote procedure call	
	//Blood Pressure monitoring
	public StreamObserver<PressureRequest> bloodPressure(StreamObserver<PressureResponse> responseObserver){
		return new StreamObserver<PressureRequest>() {

			float systolic = (float) 110.0;
			float diastolic = (float) 70.0;
			
			@Override
			public void onNext(PressureRequest value) {
				
				System.out.println("Received systolic Blood Pressure: " + value.getSystolic() + "/mmHg");
				System.out.println("Received diastolic Blood Pressure: " + value.getDiastolic() + "/mmHg");				
				
				String result = "";
				
				//The Systolic figures too low			
				if (((float)systolic >= 0 && systolic < 70) && ((float)diastolic >= 30)){					
					result = ("The Systolic figures are too low!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}				
				
				//The Diastolic figures too low				
				else if (((float)systolic > 70) && ((float)diastolic >= 0 && diastolic < 30)){					
					result = ("The Diastolic figures are too low!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}	
				
				//Both figures Systolic and Diastolic are too low				
				else if (((float)systolic >= 0 && systolic < 70) && ((float)diastolic >= 0 && diastolic < 30)){					
					result = ("Both figures Systolic and Diastolic are too low!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				//Low Blood Pressure				
				else if (((float)systolic >= 70 && systolic < 90) || ((float)diastolic >= 30 && diastolic < 60)){					
					result = ("Low Blood Pressure!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				//Normal Blood Pressure
				else if (((float) systolic >= 90 && systolic < 120) && ((float) diastolic >= 60 && diastolic < 80 )) {
					result = ("Normal Blood Pressure!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				//Prehypertesion (High Normale)
				else if (((float) systolic >= 120 && systolic < 140) || ((float) diastolic >= 80 && diastolic < 90 )) {
					result = ("Prehypertesion (High Normale)!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				//Hypertesion Stage 1
				else if (((float) systolic >= 140 && systolic < 160) || ((float) diastolic >= 90 && diastolic < 100 )) {
					result = ("Hypertesion Stage 1!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}	
				
				//Hypertision Stage 2
				else if (((float) systolic >= 160 && systolic < 180) || ((float) diastolic >= 100 && diastolic < 110 )) {
					result = ("Hypertision Stage 2!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}
				
				//Hypertensive Crisis (Medical Emergency)
				else if (((float) systolic >= 180 && systolic < 200) || ((float) diastolic >= 110 && diastolic < 120 )) {
					result = ("Hypertensive Crisis (Medical Emergency)!");					
					PressureResponse reply = PressureResponse.newBuilder().setResult(result).build();
					responseObserver.onNext(reply);
				}	
				
				//Wrong figures of Blood Pressure has been entered
				else {
					JOptionPane.showMessageDialog(null, "Enter a valid Blood Pressure figures"); 
				}					
				
			}

			@Override
			public void onError(Throwable t) {				
				t.printStackTrace();				
			}

			@Override
			public void onCompleted() {	
				
				System.out.println("Receiving Blood Pressure completed: ");
				
				//completed too
				responseObserver.onCompleted();
				
			}
			
		};
	}

}
