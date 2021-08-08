package patientAccommodationService;

import java.util.ArrayList;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceBlockingStub;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceStub;

public class PatientAccommodationClient {
	
	static ArrayList<String> patientsList = new ArrayList<String>();
	
	private static PatientAccommodationServiceBlockingStub blockingStub;
	private static PatientAccommodationServiceStub asyncStub;

	public static void main(String[] args) {
	
		ManagedChannel channel = ManagedChannelBuilder.
							forAddress("localhost", 50052)
							.usePlaintext()
							.build();
		
		//stubs -- generate from proto
		blockingStub = PatientAccommodationServiceGrpc.newBlockingStub(channel);
		asyncStub = PatientAccommodationServiceGrpc.newStub(channel);
		
		registerPatient();
		displayPatients();		

	}
	
	//Client Streaming
	//Register Patients service
	public static void registerPatient() {
		
		StreamObserver<RegisterResponse> responseObserver = new StreamObserver<RegisterResponse>() {

			@Override
			public void onNext(RegisterResponse value) {
				System.out.println("Patient Registered with, " + value.getResult());
				patientsList.add(value.getResult());				
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Patient registering completed.");
				
			}
			
		};
		
		StreamObserver<RegisterRequest> requestObserver = asyncStub.registerPatient(responseObserver);
		requestObserver.onNext(RegisterRequest.newBuilder()
											.setName("Grigor Danut")
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
	
	public static void displayPatients() {	
		
		for(int i = 0; i < patientsList.size(); i++) {			
		
			DisplayRequest request = DisplayRequest.newBuilder().setPatList(patientsList.get(i)).build();
		
			StreamObserver<DisplayResponse> responseObserver = new StreamObserver<DisplayResponse>() {

				@Override
				public void onNext(DisplayResponse value) {
					System.out.println("Patients list: " + value.getAllPatients());				
				}

				@Override
				public void onError(Throwable t) {
					t.printStackTrace();				
				}

				@Override
				public void onCompleted() {
					System.out.println("Displaying patient list request completed.");
				
				}			
			};
		
			asyncStub.displayPatients(request, responseObserver);
		}
	}

}
