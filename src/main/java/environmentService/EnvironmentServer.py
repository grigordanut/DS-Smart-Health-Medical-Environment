"""The Python implementation of the GRPC helloworld.Greeter server."""

import logging
import socket
from concurrent import futures

import grpc

from jproperties import Properties
from zeroconf import ServiceInfo, Zeroconf

import environment_pb2
import environment_pb2_grpc


class EnvironmentService(environment_pb2_grpc.EnvironmentServiceServicer):
    minTemp: int = 16
    maxTemp: int = 24

    def getCurrentRoomTemp(self, request, context):
        return environment_pb2.CurrentResponse(currentNew='The current temperature is: %s C.' % request.current)

    def setRoomTemp(self, request, context):
        if request.temp < self.minTemp:
            return environment_pb2.TempResponse(tempNew='The temperature is too low.')

        elif request.temp > self.maxTemp:
            return environment_pb2.TempResponse(tempNew='The temperature is too high.')

        else:
            return environment_pb2.TempResponse(tempNew='The temperature has been set to: %s C.' % request.temp)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    environment_pb2_grpc.add_EnvironmentServiceServicer_to_server(EnvironmentService(), server)
    server.add_insecure_port('[::]:50055')
    server.start()
    print("Environment Server started listening on port: 50055")
    server.wait_for_termination()


def register():
    global zeroconf
    desc = {'path': 'environment.properties'}

    info = ServiceInfo(
        "_environment._tcp.local.",
        "environment_monitoring_service._environment._tcp.local.",
        addresses=[socket.inet_aton("192.168.0.94")],
        port=50055,
        properties=desc,
        server="medical.local.",
    )
    zeroconf = Zeroconf()
    zeroconf.register_service(info)
    print('Registering Environment service ...')    


def proprieties():
    configs = Properties()
    with open('environment.properties', 'rb') as config_file:
        configs.load(config_file)
    print('Environment Service properties ...')
    print(configs.get("environment_service_type"))
    print(configs.get("environment_service_name"))
    print(configs.get("environment_service_description"))
    print(configs.get("environment_service_port"))


if __name__ == '__main__':
    logging.basicConfig()
    proprieties()
    register()    
    serve()