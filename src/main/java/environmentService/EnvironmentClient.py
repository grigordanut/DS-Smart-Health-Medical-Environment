from __future__ import print_function
import logging
import grpc

import environment_pb2
import environment_pb2_grpc

def current():
    with grpc.insecure_channel('localhost:50057') as channel:
        stub = environment_pb2_grpc.EnvironmentServiceStub(channel)
        response = stub.getCurrentRoomTemp(environment_pb2.Empty)
        print("Client has received: " + response)

def set():
    with grpc.insecure_channel('localhost:50057') as channel:
        stub = environment_pb2_grpc.EnvironmentServiceStub(channel)
        response = stub.setRoomTemp(environment_pb2.TempRequest(setTemp='22'))
        print("Client has received: " + response.newTemp)

if __name__ == '__main__':
    logging.basicConfig()
    current()
    set()