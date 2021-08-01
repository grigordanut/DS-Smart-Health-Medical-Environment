package patientMonitoring;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

}
