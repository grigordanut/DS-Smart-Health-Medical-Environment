package patientAdministrationService;

import java.util.Iterator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import patientAdministrationService.PatientAdministrationServiceGrpc.PatientAdministrationServiceBlockingStub;
import patientAdministrationService.PatientAdministrationServiceGrpc.PatientAdministrationServiceStub;

public class PatientAdministrationClient {
	
	private static PatientAdministrationServiceBlockingStub adminBlockingStub;
	private static PatientAdministrationServiceStub adminAsyncStub;

	public static void main(String[] args) {
	
		ManagedChannel adminChannel = ManagedChannelBuilder.
							forAddress("localhost", 50052)
							.usePlaintext()
							.build();
		
		//stubs -- generate from proto
		adminBlockingStub = PatientAdministrationServiceGrpc.newBlockingStub(adminChannel);
		adminAsyncStub = PatientAdministrationServiceGrpc.newStub(adminChannel);
		
		registerPatient();
		displayPatients();
		calculatePrice();
	}	
	
	//Client Streaming
	//Register Patients service
	public static void registerPatient() {
		
		StreamObserver<RegisterResponse> responseObserver = new StreamObserver<RegisterResponse>() {

			@Override
			public void onNext(RegisterResponse value) {
				System.out.println("Server responded; Patient registered with,\n" + value.getResult() + "\n");	
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
											.setName("Gary Skidmore")
											.setAge("51")
											.setGender("Male")
											.build());	
				
		//Mark the end of requests
		requestObserver.onCompleted();
		
		try {
			//Wait a bit
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	//Server Streaming
	//Display Patients List
	public static void displayPatients() {
		//creating request to send the patients list
		DisplayRequest request = DisplayRequest.newBuilder().setPatList("").build();		
		
		
		// try catch for error handling
		try {
			// while the server is still responding/ sending data back, keep printing data
			Iterator<DisplayResponse> responses = adminBlockingStub.displayPatients(request);
			
			System.out.println("Server responded; Patient list is displayed,\n");
			
			while (responses.hasNext()) {
				DisplayResponse patList = responses.next();				
				System.out.println(patList.getAllPatients() + "\n");
			}	
			
			System.out.println("The display of the patient list has been completed.");
			System.out.println("---------------------------------------------------\n");
			
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
	}
	
	//Unary Call
	//Calculate Patient Accommodation price
	public static void calculatePrice () {
			
		int noDays = (int) 2;
		String patName = "Lisa Hogan";
			
		CalculateRequest request = CalculateRequest.newBuilder().setPatName(patName).setNumberDays(noDays).build();
		CalculateResponse response = adminBlockingStub.calculatePrice(request);
			
		System.out.println("Server responded; Patient's accommodation price calculated,\n" + response.getMessage());
		System.out.println("Calculation of the patient's accommodation price completed.");	
		System.out.println("-----------------------------------------------------------\n");			
	}
}
