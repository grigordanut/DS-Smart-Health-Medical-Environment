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
							forAddress("localhost", 50051)
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
		
		//print appropriate response		
		if(response.getDeviceStatus()) {
			System.out.println("Server responded with, service has been turned: On!");
		}
				
		else {
			System.out.println("Server responded with, service has been turned: Off!");
		}			
	}
	
	//GRPC Bidirectional remote procedure call	
	//Blood Pressure monitoring
	public static void bloodPressure() {
		
		StreamObserver<PressureResponse> responseObserver = new StreamObserver<PressureResponse>(){

			@Override
			public void onNext(PressureResponse value) {
				System.out.println("Blood pressure result: " + value.getResult());
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Blood pressure result completed.");
				
			}			
		};
		
		StreamObserver<PressureRequest> requestObserver = asyncStub.bloodPressure(responseObserver);
		requestObserver.onNext(PressureRequest.newBuilder().setSystolic(110).build());
		requestObserver.onNext(PressureRequest.newBuilder().setDiastolic(70).build());
		
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
