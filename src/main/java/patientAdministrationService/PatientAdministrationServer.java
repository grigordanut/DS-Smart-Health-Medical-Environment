package patientAdministrationService;

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
import patientAdministrationService.CalculateRequest.Room;
import patientAdministrationService.PatientAdministrationServiceGrpc.PatientAdministrationServiceImplBase;

public class PatientAdministrationServer extends PatientAdministrationServiceImplBase {
			
	public static void main(String[] args) {

		PatientAdministrationServer patAdminServer = new PatientAdministrationServer();
		
		Properties adminProp = patAdminServer.getAdminProperties();
		
		patAdminServer.registeringPatientAdministationService(adminProp);
		
		int adminPort = Integer.valueOf( adminProp.getProperty("service_port")); //#50052
		
		try {
			Server adminServer = ServerBuilder.forPort(adminPort)
											.addService(patAdminServer)
											.build()
											.start();
			
			System.out.println("Patient Administration Server started listening on port: " +adminPort +"\n");
			
			adminServer.awaitTermination();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Properties getAdminProperties() {
		
		Properties adminProp = null;
		
		//Define the input properties path
		try (InputStream adminInput = new FileInputStream("src/main/resources/patient_administration.properties")){
			
			adminProp = new Properties();
			
			//load a properties file
			adminProp.load(adminInput);		
			
			//get the properties value and print it out
			System.out.println("Accommodation Service properties ...");
			System.out.println("\t service_type: " + adminProp.getProperty("service_type"));
			System.out.println("\t service_name: " + adminProp.getProperty("service_name"));					
			System.out.println("\t service_description: " + adminProp.getProperty("service_description"));
			System.out.println("\t service_port: " + adminProp.getProperty("service_port"));
											
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return adminProp;
	}		
	
	public void registeringPatientAdministationService(Properties adminProp) {		
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			/*
			 * Setting service information - prepare parameters for creating the ServiceInfo
			 */
			
			//Assume that there is registering an http server
			String service_type = adminProp.getProperty("service_type"); //"_patient_administration._tcp.local.";
			String service_name = adminProp.getProperty("service_name"); //"patient_administration_service";
			int service_port = Integer.valueOf( adminProp.getProperty("service_port")); //#50052;
			String service_description_properties = adminProp.getProperty("service_description"); //"path=index.html";
			
			//Register a service			
			ServiceInfo adminServiceInfo = ServiceInfo.create(service_type, 
															service_name, 
															service_port, 
															service_description_properties);
			
			jmdns.registerService(adminServiceInfo);
			
			System.out.printf("Registering service with type: %s and name: %s \n", service_type, service_name);
			
			////Wait a bit
			Thread.sleep(1000);
			
			//System.out.println("Ready to unregister services");
			
			//Unregister all services			
			//jmdns.unregisterAllServices();
			//jmdns.unregisterService(adminServiceInfo);
			
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
				System.out.println("Request received to register patient with, Name: " + value.getName()
																			+ ", Age: " + value.getAge()
																			+ ", Gender: " + value.getGender());
				
				String result = ("Patient Name: " + value.getName() 
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
				System.out.println("Patient registering request completed.");
				System.out.println("--------------------------------------\n");
				
				//completed too
				responseObserver.onCompleted();				
			}			
		};
	}
	
	public void displayPatients(DisplayRequest request, StreamObserver<DisplayResponse> responseObserver) {
		// server receives request for bank account stats
		// and returns stream of responses to client
		System.out.print("Receiving a request to show the patient list.\n\n" + request.getPatList());

		ArrayList<String> patList = new ArrayList<String>();
		patList.add("Patient Name: Grigor Danut, Age: 51, Gender: male");
		patList.add("Patient Name: Maria Danut, Age: 42, Gender: female");
		patList.add("Patient Name: John Skydmore, Age: 57, Gender: male");
		patList.add("Patient Name: Wendy Gamnon, Age: 45, Gender: female");
		patList.add("Patient Name: Gary Lineker, Age: 59, Gender: male");		

		for (int i = 0; i <  patList.size(); i++) {

			DisplayResponse reply = DisplayResponse.newBuilder().setAllPatients( patList.get(i)).build();
			
			responseObserver.onNext(reply);
			
			System.out.println("Show details of patient: " + i + "\n" +  patList.set(i, null) +"\n");			
			
			// error handling
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		System.out.println("Showing Patients list completed.");
		System.out.println("--------------------------------\n");
		responseObserver.onCompleted();		
		
	}	
	
	//Unary Call
	//Calculate Patient Accommodation price
	public void calculatePrice(CalculateRequest request, StreamObserver<CalculateResponse> responseObserver) {
		
		System.out.println("Receiving calculate accommodation price for: " 
		+ request.getPatName() + ", for: " + request.getNumberDays() +" days " + ", in: " +request.getRoom() + " room.");
		
		float priceDay = (float) 0.00;
		float totalPrice = (float) 0.00;
		String result = "";
		
		if (request.getRoom()==Room.PUBLIC) {	
			
			priceDay = (float) 100.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = "The acomodation price in a: " + request.getRoom() + " room for a day is: € " + priceDay + " per day, " 
					+"\nand the total price for: " + request.getPatName() 
					+" for: "+ request.getNumberDays() + " days is: € " + totalPrice + "\n";			
		}
		
		else if (request.getRoom()==Room.SEMIPRIVATE) {
			
			priceDay = (float) 200.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = "The acomodation price in a: " + request.getRoom() + " room for a day is: € " + priceDay + " per day, " 
					+"\nand the total price for: " + request.getPatName() 
					+" for: "+ request.getNumberDays() + " days is: € " + totalPrice + "\n";
		}
		
		else if (request.getRoom()==Room.PRIVATE) {
			
			priceDay = (float) 500.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = "The acomodation price in a: " + request.getRoom() + " room for a day is: € " + priceDay + " per day, " 
					+"\nand the total price for: " + request.getPatName() 
					+" for: "+ request.getNumberDays() + " days is: € " + totalPrice + "\n";
		}
		
		else {
			result = "the type of room not founded";
		}
		
		CalculateResponse reply = CalculateResponse.newBuilder().setMessage(result).build();
		responseObserver.onNext(reply);			
		
		responseObserver.onCompleted();		
		System.out.println("Patient calculate Accommodation Price request completed.");
		System.out.println("--------------------------------------------------------\n");		
	}
}
