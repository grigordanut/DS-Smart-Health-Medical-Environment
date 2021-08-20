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
		
		int adminPort = Integer.valueOf( adminProp.getProperty("administration_service_port")); //#50052
		
		try {
			Server adminServer = ServerBuilder.forPort(adminPort)
											.addService(patAdminServer)
											.build()
											.start();
			
			System.out.println("Patient Administration Server started listening on port: " + adminPort);
			System.out.println("--------------------------------------------------------------\n");
			
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
			System.out.println("\t service_type: " + adminProp.getProperty("administration_service_type"));
			System.out.println("\t service_name: " + adminProp.getProperty("administration_service_name"));					
			System.out.println("\t service_description: " + adminProp.getProperty("administration_service_description"));
			System.out.println("\t service_port: " + adminProp.getProperty("administration_service_port"));
											
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
			String administration_service_type = adminProp.getProperty("administration_service_type"); //"_medical._tcp.local.";
			String administration_service_name = adminProp.getProperty("administration_service_name"); //"patient_administration_service";
			int administration_service_port = Integer.valueOf( adminProp.getProperty("administration_service_port")); //#50052;
			String administration_service_description_properties = adminProp.getProperty("administration_service_description"); //"path=index.html";
			
			//Register a service			
			ServiceInfo adminServiceInfo = ServiceInfo.create(administration_service_type, 
															administration_service_name, 
															administration_service_port, 
															administration_service_description_properties);
			
			jmdns.registerService(adminServiceInfo);
			
			System.out.printf("Registering service with type: %s and name: %s \n", administration_service_type, administration_service_name);
			
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
		
	//Client streaming
	//Patient Registration 
	public StreamObserver<RegisterRequest> registerPatient(StreamObserver<RegisterResponse> responseObserver){
		
		return new StreamObserver<RegisterRequest>() {

			@Override
			public void onNext(RegisterRequest value) {
				System.out.println("Receiving patient registration request with,\nPatient Name: " + value.getName()
																			+ ", Age: " + value.getAge()
																			+ ", Gender: " + value.getGender() 
																			+ "\n");
				
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
	
	//Server streaming
	//Display list of patients
	public void displayPatients(DisplayRequest request, StreamObserver<DisplayResponse> responseObserver) {
		
		System.out.print("Receiving patient list display request,\n\n" + request.getPatList());

		ArrayList<String> patList = new ArrayList<String>();
		patList.add("Patient Name: Gary Skidmore, Age: 51, Gender: male");
		patList.add("Patient Name: Lisa Hogan, Age: 45, Gender: female");		
		patList.add("Patient Name: Peter Mark, Age: 57, Gender: male");
		patList.add("Patient Name: Wendy Gamnon, Age: 42, Gender: female");
		patList.add("Patient Name: Gavin Smith, Age: 59, Gender: male");		

		for (int i = 0; i <  patList.size(); i++) {

			DisplayResponse reply = DisplayResponse.newBuilder().setAllPatients(patList.get(i)).build();
			
			responseObserver.onNext(reply);
			
			System.out.println("Display details of patient: " + i + "\n" + patList.set(i, null) +"\n");			
			
			// error handling
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Patient listing request completed.");
		System.out.println("--------------------------------\n");
		responseObserver.onCompleted();			
	}	
	
	//Unary call
	//Calculate Patient Accommodation price
	public void calculatePrice(CalculateRequest request, StreamObserver<CalculateResponse> responseObserver) {
		
		System.out.println("Receiving patient accommodation price calculation request for,\nPatient Name: " + request.getPatName()
																				+ ", for: " + request.getNumberDays() 
																				+" days, in a: " + request.getRoom() + " room.\n");
		
		float priceDay = (float) 0.00;
		float totalPrice = (float) 0.00;
		String message = "The total accommodation price for, patient:\n " + request.getPatName() + 
															", for: "+ request.getNumberDays() + 
															" days, in a: " + request.getRoom() + " room is: € ";
		
		String result = "";
		
		if (request.getRoom()==Room.PUBLIC) {	
			
			priceDay = (float) 100.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = message + totalPrice + "\n";			
		}
		
		else if (request.getRoom()==Room.SEMIPRIVATE) {
			
			priceDay = (float) 200.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = message + totalPrice + "\n";			
		}
		
		else if (request.getRoom()==Room.PRIVATE) {
			
			priceDay = (float) 500.00;
			totalPrice = request.getNumberDays() * (float) priceDay;
			
			result = message + totalPrice + "\n";
		}
		
		else {
			result = "the type of room not founded";
		}
		
		CalculateResponse reply = CalculateResponse.newBuilder().setMessage(result).build();
		responseObserver.onNext(reply);			
		
		responseObserver.onCompleted();		
		System.out.println("Patient accommodation price calculation request completed.");
		System.out.println("----------------------------------------------------------\n");		
	}
}
