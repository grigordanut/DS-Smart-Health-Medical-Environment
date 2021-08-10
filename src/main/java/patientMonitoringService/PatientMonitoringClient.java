package patientMonitoringService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientMonitoringService.PatientMonitoringServiceGrpc.PatientMonitoringServiceBlockingStub;
import patientMonitoringService.PatientMonitoringServiceGrpc.PatientMonitoringServiceStub;

public class PatientMonitoringClient {
	
	private static PatientMonitoringServiceBlockingStub blockingStub;
	private static PatientMonitoringServiceStub asyncStub;

	public static void main(String[] args) {
		
		ManagedChannel channel = ManagedChannelBuilder.
							forAddress("localhost", 50053)
							.usePlaintext()
							.build();
		
		//stubs -- generate from proto
		blockingStub = PatientMonitoringServiceGrpc.newBlockingStub(channel);
		asyncStub = PatientMonitoringServiceGrpc.newStub(channel);	
		
		monitoringDeviceOnOff();
		bloodPressure();

	}
	
	//GRPC Unary remote procedure call
	//TURN ON/OFF Monitoring Device	
	public static void monitoringDeviceOnOff() {
		
		//sent the request
		DeviceRequest request = DeviceRequest.newBuilder().setDeviceStatus(false).build();
		
		//check the response
		DeviceResponse response = blockingStub.monitoringDeviceOnOff(request);
		//System.out.println("Server responded with: "+request.getDeviceStatus());
		
		//print appropriate response		
		if(response.getDeviceStatus()) {
			System.out.println("Server responded with, The Device has been turned: On!");
			System.out.println("Monitoring Device changing status completed.");
			System.out.println("--------------------------------------------\n");
		}
				
		else {
			System.out.println("Server responded with, The Device has been turned: Off!");
			System.out.println("Monitoring Device changing status completed.");
			System.out.println("--------------------------------------------\n");
		}			
	}
	
	//GRPC Bidirectional remote procedure call	
	//Blood Pressure monitoring
	public static void bloodPressure() {
		
		StreamObserver<PressureResponse> responseObserver = new StreamObserver<PressureResponse>(){

			@Override
			public void onNext(PressureResponse value) {
				System.out.println("Received Blood Pressure request result: " + value.getResult());				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();				
			}

			@Override
			public void onCompleted() {
				System.out.println("Blood Pressure receiving completed.");
				System.out.println("-----------------------------------\n");
				
			}			
		};
		
		StreamObserver<PressureRequest> requestObserver = asyncStub.bloodPressure(responseObserver);
		
		requestObserver.onNext(PressureRequest.newBuilder().setSystolic(110).setDiastolic(70).build());		
		
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
