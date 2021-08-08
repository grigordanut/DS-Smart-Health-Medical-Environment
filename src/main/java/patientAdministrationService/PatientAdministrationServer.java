package patientAdministrationService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
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
			
			System.out.println("Patient Administration Server started listening on port: " +adminPort);
			
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
	
	
	
	

}
