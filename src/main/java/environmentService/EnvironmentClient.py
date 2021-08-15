from __future__ import print_function
import logging
import grpc

import environment_pb2
import environment_pb2_grpc


def current():
    with grpc.insecure_channel('localhost:50054') as channel:
        stub = environment_pb2_grpc.EnvironmentServiceStub(channel)
        response = stub.getCurrentRoomTemp(environment_pb2.CurrentRequest(current=int(20)))
        print('Request received to show the current temperature.'"\n" + response.currentNew +
              "\n"'-------------------------------------------------')


def temperature():
    with grpc.insecure_channel('localhost:50054') as channel:
        stub = environment_pb2_grpc.EnvironmentServiceStub(channel)
        response = stub.setRoomTemp(environment_pb2.TempRequest(temp=int(22)))
        print('Request received to set a new temperature.'"\n" + response.tempNew +
              "\n"'-------------------------------------------------')


if __name__ == '__main__':
    logging.basicConfig()
    current()
    temperature()