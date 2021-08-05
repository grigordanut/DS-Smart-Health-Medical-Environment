package patientAccommodationService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceBlockingStub;
import patientAccommodationService.PatientAccommodationServiceGrpc.PatientAccommodationServiceStub;

public class PatientAccommodationClient {
	
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

	}
	
	//Client Streaming
	//Register Patients service
	public static void registerPatient() {
		StreamObserver<RegisterResponse> responseObserver = new StreamObserver<RegisterResponse>() {

			@Override
			public void onNext(RegisterResponse value) {
				System.out.println("Registerng Patient with: " + value.getResult());
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Registering Patient is completed.");
				
			}
			
		};
		
		StreamObserver<RegisterRequest> requestObserver = asyncStub.registerPatient(responseObserver);
		requestObserver.onNext(RegisterRequest.newBuilder().setName("Grigor Danut").build());
		requestObserver.onNext(RegisterRequest.newBuilder().setAge("51").build());
		requestObserver.onNext(RegisterRequest.newBuilder().setGender("Male").build());
		
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

}
