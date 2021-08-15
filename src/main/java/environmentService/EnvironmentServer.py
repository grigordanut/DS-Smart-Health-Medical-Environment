from concurrent import futures
import logging
import grpc

import environment_pb2
import environment_pb2_grpc


class EnvironmentServer(environment_pb2_grpc.EnvironmentServiceServicer):

    def getCurrentRoomTemp(self, request, context):
        return environment_pb2.CurrentResponse(currentNew='The current temperature is: %s C.' % request.current)

    def setRoomTemp(self, request, context):
        return environment_pb2.TempResponse(tempNew='The temperature has been set to: %s C.' % request.temp)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    environment_pb2_grpc.add_EnvironmentServiceServicer_to_server(EnvironmentServer(), server)
    server.add_insecure_port('[::]:50054')
    print('Starting server. Listening on port 50054.')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    logging.basicConfig()
    serve()