package patientAccommodationService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceImplBase;

public class PatientAccommodationServer extends PatientAccommodationServiceImplBase {

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
			
			System.out.println("Patient Accommodation Server started listening on port: " +port);
			
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
			System.out.println("\t servive_description: " + prop.getProperty("service_description"));
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
	
	//Client Streaming
	//Patient Register service
	public StreamObserver<RegisterRequest> registerPatient(StreamObserver<RegisterResponse> responseObserver){
		
		return new StreamObserver<RegisterRequest>() {

			@Override
			public void onNext(RegisterRequest value) {
				System.out.println("Registering result:\n" 
									+ "Patient Name: " + value.getName() 
									+ ", Age" + value.getAge() 
									+ ", Gender: " + value.getGender());
				
				String result = (" Patient Name: " + value.getName() 
								+ ", Age: " + value.getAge() 
								+ ", Gender: " + value.getGender());
				
				RegisterResponse reply = RegisterResponse.newBuilder().setResult(result).build();				
				responseObserver.onNext(reply);
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Receiving Register Patient completed ");
				
				//completed too
				responseObserver.onCompleted();
				
			}
			
		};
	}

}
