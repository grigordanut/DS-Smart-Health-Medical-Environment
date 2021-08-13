package patientAdministrationService;

import java.util.Iterator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
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
		
		//registerPatient();
		displayPatients();
		//calculatePrice();

	}
	
	//Client Streaming
	//Register Patients service
//	public static void registerPatient() {
//		
//		StreamObserver<RegisterResponse> responseObserver = new StreamObserver<RegisterResponse>() {
//
//			@Override
//			public void onNext(RegisterResponse value) {
//				System.out.println("Patient Registered with, " + value.getResult());
//				//patientsList.add(value.getResult());		
//			}
//
//			@Override
//			public void onError(Throwable t) {
//				t.printStackTrace();				
//			}
//
//			@Override
//			public void onCompleted() {
//				System.out.println("Patient registering completed.");	
//				System.out.println("------------------------------\n");
//				
//			}			
//		};
//		
//		StreamObserver<RegisterRequest> requestObserver = adminAsyncStub.registerPatient(responseObserver);
//		requestObserver.onNext(RegisterRequest.newBuilder()
//											.setName("Grigor Danut")
//											.setAge("51")
//											.setGender("Male")
//											.build());	
//				
//		//Mark the end of requests
//		requestObserver.onCompleted();
//		
//		try {
//			//Wait a bit
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
//	public static void displayPatients() {				
//		
//		DisplayRequest request = DisplayRequest.newBuilder().setPatList("The pat list is: ").build();
//		
//		try {			
//			Iterator<DisplayResponse> responses = adminBlockingStub.displayPatients(request);
//			
//			while(responses.hasNext()) {
//				DisplayResponse patients = responses.next();
//				System.out.println("the patttirnt list " + patients.getAllPatients() + "\n");
//			}					
//			
//		} catch (StatusRuntimeException e) {
//			e.printStackTrace();
//		}		
//	}
	
	//old version
	//Server streaming
//	public static void displayPatients() {	
//		
//		//for(int i = 0; i < patientsList.size(); i++) {	
//		
//		String pat1 = ("Patient Name: Grigor Danutuus, Age: 51, Gender: male");
//		String pat2 = ("Patient Name: Maria Danut, Age: 42, Gender: female");
//		
//	
//		
//			DisplayRequest request = DisplayRequest.newBuilder().setPatList(pat1).build();
//		
//			StreamObserver<DisplayResponse> responseObserver = new StreamObserver<DisplayResponse>() {
//
//				@Override
//				public void onNext(DisplayResponse value) {
//					System.out.println("Patients list: " + value.getAllPatients());				
//				}
//
//				@Override
//				public void onError(Throwable t) {
//					t.printStackTrace();				
//				}
//
//				@Override
//				public void onCompleted() {
//					System.out.println("Displaying patient list request completed.");
//				
//				}			
//			};
//		
//			adminAsyncStub.displayPatients(request, responseObserver);
//		//}
//	}
	
//	public static void calculatePrice () {
//		
//		int noDays = 2;
//		String patName = "Grigor Danut";
//		
//		CalculateRequest request = CalculateRequest.newBuilder().setPatName(patName).setNumberDays(noDays).build();
//		CalculateResponse response = adminBlockingStub.calculatePrice(request);
//		
//		System.out.println(response.getMessage());
//		System.out.println("Patient calculate Accommodation Price completed.");	
//		System.out.println("------------------------------------------------\n");
//		
//	}
	
	public static void displayPatients() {
		// creating request to send server for account stats
		DisplayRequest request = DisplayRequest.newBuilder()
				.setPatList("Request to view monthly spending statistics by top category.")
				.build();
		
		// try catch for error handling
		try {
			// while the server is still responding/ sending data back, keep printing data
			Iterator<DisplayResponse> responses = adminBlockingStub.displayPatients(request);
			
			while (responses.hasNext()) {
				DisplayResponse temp = responses.next();
				System.out.println("Category: " + temp.getAllPatients() + "\n");
			}
			System.out.println("Retrieved stock prices.");
			
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
	}

}
