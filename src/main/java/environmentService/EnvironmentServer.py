import grpc
from concurrent import futures
import time

#import generated classes
import environment_pb2
import environment_pb2_grpc

class EnvironmentServer(environment_pb2_grpc.EnvironmentServiceServicer):
    
    currentTemp = 20
    maxTemp = 24
    minTemp = 16
    newTemp = 0
   
    
   
    def getCurrentRoomTemp(self, request, context):
        
        return environment_pb2.CurrentResponse(value = self.currentTemp )
        
        # response = environment_pb2.CurrentResponse()
        # response.value = self.currentTemp
        # return response
    
    def setRoomTemp(self, request, context):
        
        return environment_pb2.TempResponse(result = request.getvalue());
        
        
        # response = environment_pb2.TempResponse()
        # if self.minTemp > request.value:
        #     print("Temperature too low")
        # elif self.maxTemp < request.value:
        #     print("Temperature too high")
        # else:
        #     response.value = self.newTemp
        #     print("Temperature updated to " + str(response.value))
        # return response
    
    


server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))


environment_pb2_grpc.add_EnvironmentServiceServicer_to_server(EnvironmentServer(), server)


print('Starting server. Listening on port 50054.')
server.add_insecure_port('[::]:50054')
server.start()


try:
    while True:
        time.sleep(1000)
except KeyboardInterrupt:
    server.stop(0)
        