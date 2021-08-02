package patientMonitoring;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceBlockingStub;
import patientMonitoring.PatientMonitoringServiceGrpc.PatientMonitoringServiceStub;

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
	
	public static void bloodPressure() {
		
		StreamObserver<PressureResponse> responseObserver = new StreamObserver<PressureResponse>(){

			@Override
			public void onNext(PressureResponse value) {
				System.out.println("Blod presure result: " + value.getResult());
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Blod presure result completed.");
				
			}			
		};
		
		StreamObserver<PressureRequest> requestObserver = asyncStub.bloodPressure(responseObserver);
		requestObserver.onNext(PressureRequest.newBuilder().setSystolic(110).build());
		requestObserver.onNext(PressureRequest.newBuilder().setDiastolic(70).build());
		
		requestObserver.onCompleted();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}	

}
