package patientAccommodationService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceImplBase;

public class PatientAccommodationServer extends PatientAccommodationServiceImplBase {
	
	private ArrayList<String> patientList = new ArrayList<String>();

	public static void main(String[] args) {		
		
		PatientAccommodationServer patAccommServer = new PatientAccommodationServer();
		
		Properties prop = patAccommServer.getProperties();
		
		patAccommServer.registeringService(prop);
		
		int port = Integer.valueOf( prop.getProperty("service_port")); //#50052
		
				
		try {
			Server server = ServerBuilder.forPort(port)
										.addService(patAccommServer)
										.build()
										.start();
			
			System.out.println("Patient Accommodation Server started listening on port: " + port);
			
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
		
		Properties prop = new Properties();
		
		//Define the input properties path		
		try(InputStream input = new FileInputStream("src/main/resources/patientAccommodation.properties")){
			
			//load a properties file
			prop.load(input);
			
			//get the properties value and print it out
			System.out.println("Accommodation Service properties ...");
			System.out.println("\t service_type: " +prop.getProperty("service_type"));
			System.out.println("\t service_name: " + prop.getProperty("service_name"));
			System.out.println("\t service_description: " + prop.getProperty("service_description"));
			System.out.println("\t service_port: " + prop.getProperty("service_port"));	
			
			
			
		} catch (IOException ex) {			
			ex.printStackTrace();
		} 		
		
		return prop;
	}
	
	public void registeringService(Properties prop) {		
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());			
			
			/*
			 * Setting service information - prepare parameters for creating the ServiceInfo
			 */
			
			//Assume that there is registering an http server			
			String service_type = prop.getProperty("service_type"); //"_accommodationMonitoring._tcp.local.";
			String service_name = prop.getProperty("service_name"); //"accommodationMonitoring_service";
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
	
	//Client Streaming
	//Patient Register service
	public StreamObserver<RegisterRequest> registerPatient(StreamObserver<RegisterResponse> responseObserver){
		
		return new StreamObserver<RegisterRequest>() {					

			@Override
			public void onNext(RegisterRequest value) {
				System.out.println("Request received to register patient with Name: " +value.getName());
				System.out.println("Request received to register patient with Age: " +value.getAge());
				System.out.println("Request received to register patient with Gender: " +value.getGender());
				
				String result = ("Patient Name: "+ value.getName() 
								      +", Age: " + value.getAge() 
								  + ", Gender: " + value.getGender());
				
				patientList.add(result);
				
				RegisterResponse reply = RegisterResponse.newBuilder().setResult(result).build();
	
				responseObserver.onNext(reply);				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {			
				
				System.out.println("Patient registering request completed. ");
				
				//completed too
				responseObserver.onCompleted();				
			}
			
		};
	}
	
	//Server Streaming
	//Display Patient List
	public void displayPatients(String  request, StreamObserver<DisplayResponse> responseObserver) {	
		
		System.out.println("Received request to display the patient list:" + patientList.toString());
		
		for(int i = 0; i < patientList.size(); i++) {		            	
			request = patientList.get(i);	
			DisplayResponse reply = DisplayResponse.newBuilder().setAllPatients(request).build();
			
			System.out.println("Display patient request completed.");
			
			responseObserver.onNext(reply);
        	
        }	 
			
	};

}
