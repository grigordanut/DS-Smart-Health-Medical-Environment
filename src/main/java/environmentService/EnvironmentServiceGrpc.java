package environmentService;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: environment.proto")
public final class EnvironmentServiceGrpc {

  private EnvironmentServiceGrpc() {}

  public static final String SERVICE_NAME = "environmentService.EnvironmentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<environmentService.TempRequest,
      environmentService.TempResponse> getSetRoomTempMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "setRoomTemp",
      requestType = environmentService.TempRequest.class,
      responseType = environmentService.TempResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<environmentService.TempRequest,
      environmentService.TempResponse> getSetRoomTempMethod() {
    io.grpc.MethodDescriptor<environmentService.TempRequest, environmentService.TempResponse> getSetRoomTempMethod;
    if ((getSetRoomTempMethod = EnvironmentServiceGrpc.getSetRoomTempMethod) == null) {
      synchronized (EnvironmentServiceGrpc.class) {
        if ((getSetRoomTempMethod = EnvironmentServiceGrpc.getSetRoomTempMethod) == null) {
          EnvironmentServiceGrpc.getSetRoomTempMethod = getSetRoomTempMethod = 
              io.grpc.MethodDescriptor.<environmentService.TempRequest, environmentService.TempResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "environmentService.EnvironmentService", "setRoomTemp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  environmentService.TempRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  environmentService.TempResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EnvironmentServiceMethodDescriptorSupplier("setRoomTemp"))
                  .build();
          }
        }
     }
     return getSetRoomTempMethod;
  }

  private static volatile io.grpc.MethodDescriptor<environmentService.Empty,
      environmentService.CurrentResponse> getGetCurrentRoomTempMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCurrentRoomTemp",
      requestType = environmentService.Empty.class,
      responseType = environmentService.CurrentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<environmentService.Empty,
      environmentService.CurrentResponse> getGetCurrentRoomTempMethod() {
    io.grpc.MethodDescriptor<environmentService.Empty, environmentService.CurrentResponse> getGetCurrentRoomTempMethod;
    if ((getGetCurrentRoomTempMethod = EnvironmentServiceGrpc.getGetCurrentRoomTempMethod) == null) {
      synchronized (EnvironmentServiceGrpc.class) {
        if ((getGetCurrentRoomTempMethod = EnvironmentServiceGrpc.getGetCurrentRoomTempMethod) == null) {
          EnvironmentServiceGrpc.getGetCurrentRoomTempMethod = getGetCurrentRoomTempMethod = 
              io.grpc.MethodDescriptor.<environmentService.Empty, environmentService.CurrentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "environmentService.EnvironmentService", "getCurrentRoomTemp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  environmentService.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  environmentService.CurrentResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EnvironmentServiceMethodDescriptorSupplier("getCurrentRoomTemp"))
                  .build();
          }
        }
     }
     return getGetCurrentRoomTempMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EnvironmentServiceStub newStub(io.grpc.Channel channel) {
    return new EnvironmentServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnvironmentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EnvironmentServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EnvironmentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EnvironmentServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EnvironmentServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void setRoomTemp(environmentService.TempRequest request,
        io.grpc.stub.StreamObserver<environmentService.TempResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSetRoomTempMethod(), responseObserver);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void getCurrentRoomTemp(environmentService.Empty request,
        io.grpc.stub.StreamObserver<environmentService.CurrentResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCurrentRoomTempMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSetRoomTempMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                environmentService.TempRequest,
                environmentService.TempResponse>(
                  this, METHODID_SET_ROOM_TEMP)))
          .addMethod(
            getGetCurrentRoomTempMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                environmentService.Empty,
                environmentService.CurrentResponse>(
                  this, METHODID_GET_CURRENT_ROOM_TEMP)))
          .build();
    }
  }

  /**
   */
  public static final class EnvironmentServiceStub extends io.grpc.stub.AbstractStub<EnvironmentServiceStub> {
    private EnvironmentServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnvironmentServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvironmentServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnvironmentServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void setRoomTemp(environmentService.TempRequest request,
        io.grpc.stub.StreamObserver<environmentService.TempResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetRoomTempMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void getCurrentRoomTemp(environmentService.Empty request,
        io.grpc.stub.StreamObserver<environmentService.CurrentResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCurrentRoomTempMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EnvironmentServiceBlockingStub extends io.grpc.stub.AbstractStub<EnvironmentServiceBlockingStub> {
    private EnvironmentServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnvironmentServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvironmentServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnvironmentServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public environmentService.TempResponse setRoomTemp(environmentService.TempRequest request) {
      return blockingUnaryCall(
          getChannel(), getSetRoomTempMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public environmentService.CurrentResponse getCurrentRoomTemp(environmentService.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetCurrentRoomTempMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EnvironmentServiceFutureStub extends io.grpc.stub.AbstractStub<EnvironmentServiceFutureStub> {
    private EnvironmentServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnvironmentServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvironmentServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnvironmentServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<environmentService.TempResponse> setRoomTemp(
        environmentService.TempRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSetRoomTempMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<environmentService.CurrentResponse> getCurrentRoomTemp(
        environmentService.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCurrentRoomTempMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SET_ROOM_TEMP = 0;
  private static final int METHODID_GET_CURRENT_ROOM_TEMP = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EnvironmentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EnvironmentServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SET_ROOM_TEMP:
          serviceImpl.setRoomTemp((environmentService.TempRequest) request,
              (io.grpc.stub.StreamObserver<environmentService.TempResponse>) responseObserver);
          break;
        case METHODID_GET_CURRENT_ROOM_TEMP:
          serviceImpl.getCurrentRoomTemp((environmentService.Empty) request,
              (io.grpc.stub.StreamObserver<environmentService.CurrentResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EnvironmentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EnvironmentServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return environmentService.EnvironmentServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EnvironmentService");
    }
  }

  private static final class EnvironmentServiceFileDescriptorSupplier
      extends EnvironmentServiceBaseDescriptorSupplier {
    EnvironmentServiceFileDescriptorSupplier() {}
  }

  private static final class EnvironmentServiceMethodDescriptorSupplier
      extends EnvironmentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EnvironmentServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EnvironmentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EnvironmentServiceFileDescriptorSupplier())
              .addMethod(getSetRoomTempMethod())
              .addMethod(getGetCurrentRoomTempMethod())
              .build();
        }
      }
    }
    return result;
  }
}
