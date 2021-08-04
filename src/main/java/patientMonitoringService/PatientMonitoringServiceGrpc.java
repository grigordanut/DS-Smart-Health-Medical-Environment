package patientMonitoringService;

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
    comments = "Source: patientMonitoring.proto")
public final class PatientMonitoringServiceGrpc {

  private PatientMonitoringServiceGrpc() {}

  public static final String SERVICE_NAME = "patientMonitoringService.PatientMonitoringService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<patientMonitoringService.DeviceRequest,
      patientMonitoringService.DeviceResponse> getMonitoringDeviceOnOffMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "monitoringDeviceOnOff",
      requestType = patientMonitoringService.DeviceRequest.class,
      responseType = patientMonitoringService.DeviceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<patientMonitoringService.DeviceRequest,
      patientMonitoringService.DeviceResponse> getMonitoringDeviceOnOffMethod() {
    io.grpc.MethodDescriptor<patientMonitoringService.DeviceRequest, patientMonitoringService.DeviceResponse> getMonitoringDeviceOnOffMethod;
    if ((getMonitoringDeviceOnOffMethod = PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod) == null) {
      synchronized (PatientMonitoringServiceGrpc.class) {
        if ((getMonitoringDeviceOnOffMethod = PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod) == null) {
          PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod = getMonitoringDeviceOnOffMethod = 
              io.grpc.MethodDescriptor.<patientMonitoringService.DeviceRequest, patientMonitoringService.DeviceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "patientMonitoringService.PatientMonitoringService", "monitoringDeviceOnOff"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.DeviceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.DeviceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientMonitoringServiceMethodDescriptorSupplier("monitoringDeviceOnOff"))
                  .build();
          }
        }
     }
     return getMonitoringDeviceOnOffMethod;
  }

  private static volatile io.grpc.MethodDescriptor<patientMonitoringService.PressureRequest,
      patientMonitoringService.PressureResponse> getBloodPressureMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bloodPressure",
      requestType = patientMonitoringService.PressureRequest.class,
      responseType = patientMonitoringService.PressureResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<patientMonitoringService.PressureRequest,
      patientMonitoringService.PressureResponse> getBloodPressureMethod() {
    io.grpc.MethodDescriptor<patientMonitoringService.PressureRequest, patientMonitoringService.PressureResponse> getBloodPressureMethod;
    if ((getBloodPressureMethod = PatientMonitoringServiceGrpc.getBloodPressureMethod) == null) {
      synchronized (PatientMonitoringServiceGrpc.class) {
        if ((getBloodPressureMethod = PatientMonitoringServiceGrpc.getBloodPressureMethod) == null) {
          PatientMonitoringServiceGrpc.getBloodPressureMethod = getBloodPressureMethod = 
              io.grpc.MethodDescriptor.<patientMonitoringService.PressureRequest, patientMonitoringService.PressureResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "patientMonitoringService.PatientMonitoringService", "bloodPressure"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.PressureRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.PressureResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientMonitoringServiceMethodDescriptorSupplier("bloodPressure"))
                  .build();
          }
        }
     }
     return getBloodPressureMethod;
  }

  private static volatile io.grpc.MethodDescriptor<patientMonitoringService.CalculateRequest,
      patientMonitoringService.CalculatetResponse> getCalculatePriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "calculatePrice",
      requestType = patientMonitoringService.CalculateRequest.class,
      responseType = patientMonitoringService.CalculatetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<patientMonitoringService.CalculateRequest,
      patientMonitoringService.CalculatetResponse> getCalculatePriceMethod() {
    io.grpc.MethodDescriptor<patientMonitoringService.CalculateRequest, patientMonitoringService.CalculatetResponse> getCalculatePriceMethod;
    if ((getCalculatePriceMethod = PatientMonitoringServiceGrpc.getCalculatePriceMethod) == null) {
      synchronized (PatientMonitoringServiceGrpc.class) {
        if ((getCalculatePriceMethod = PatientMonitoringServiceGrpc.getCalculatePriceMethod) == null) {
          PatientMonitoringServiceGrpc.getCalculatePriceMethod = getCalculatePriceMethod = 
              io.grpc.MethodDescriptor.<patientMonitoringService.CalculateRequest, patientMonitoringService.CalculatetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "patientMonitoringService.PatientMonitoringService", "calculatePrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.CalculateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoringService.CalculatetResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientMonitoringServiceMethodDescriptorSupplier("calculatePrice"))
                  .build();
          }
        }
     }
     return getCalculatePriceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PatientMonitoringServiceStub newStub(io.grpc.Channel channel) {
    return new PatientMonitoringServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PatientMonitoringServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PatientMonitoringServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PatientMonitoringServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PatientMonitoringServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PatientMonitoringServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void monitoringDeviceOnOff(patientMonitoringService.DeviceRequest request,
        io.grpc.stub.StreamObserver<patientMonitoringService.DeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMonitoringDeviceOnOffMethod(), responseObserver);
    }

    /**
     * <pre>
     *Bidirectional
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoringService.PressureRequest> bloodPressure(
        io.grpc.stub.StreamObserver<patientMonitoringService.PressureResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getBloodPressureMethod(), responseObserver);
    }

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoringService.CalculateRequest> calculatePrice(
        io.grpc.stub.StreamObserver<patientMonitoringService.CalculatetResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getCalculatePriceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMonitoringDeviceOnOffMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                patientMonitoringService.DeviceRequest,
                patientMonitoringService.DeviceResponse>(
                  this, METHODID_MONITORING_DEVICE_ON_OFF)))
          .addMethod(
            getBloodPressureMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                patientMonitoringService.PressureRequest,
                patientMonitoringService.PressureResponse>(
                  this, METHODID_BLOOD_PRESSURE)))
          .addMethod(
            getCalculatePriceMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                patientMonitoringService.CalculateRequest,
                patientMonitoringService.CalculatetResponse>(
                  this, METHODID_CALCULATE_PRICE)))
          .build();
    }
  }

  /**
   */
  public static final class PatientMonitoringServiceStub extends io.grpc.stub.AbstractStub<PatientMonitoringServiceStub> {
    private PatientMonitoringServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientMonitoringServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientMonitoringServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientMonitoringServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public void monitoringDeviceOnOff(patientMonitoringService.DeviceRequest request,
        io.grpc.stub.StreamObserver<patientMonitoringService.DeviceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMonitoringDeviceOnOffMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Bidirectional
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoringService.PressureRequest> bloodPressure(
        io.grpc.stub.StreamObserver<patientMonitoringService.PressureResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getBloodPressureMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoringService.CalculateRequest> calculatePrice(
        io.grpc.stub.StreamObserver<patientMonitoringService.CalculatetResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getCalculatePriceMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class PatientMonitoringServiceBlockingStub extends io.grpc.stub.AbstractStub<PatientMonitoringServiceBlockingStub> {
    private PatientMonitoringServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientMonitoringServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientMonitoringServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientMonitoringServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public patientMonitoringService.DeviceResponse monitoringDeviceOnOff(patientMonitoringService.DeviceRequest request) {
      return blockingUnaryCall(
          getChannel(), getMonitoringDeviceOnOffMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PatientMonitoringServiceFutureStub extends io.grpc.stub.AbstractStub<PatientMonitoringServiceFutureStub> {
    private PatientMonitoringServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientMonitoringServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientMonitoringServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientMonitoringServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary call
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<patientMonitoringService.DeviceResponse> monitoringDeviceOnOff(
        patientMonitoringService.DeviceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMonitoringDeviceOnOffMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MONITORING_DEVICE_ON_OFF = 0;
  private static final int METHODID_BLOOD_PRESSURE = 1;
  private static final int METHODID_CALCULATE_PRICE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PatientMonitoringServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PatientMonitoringServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MONITORING_DEVICE_ON_OFF:
          serviceImpl.monitoringDeviceOnOff((patientMonitoringService.DeviceRequest) request,
              (io.grpc.stub.StreamObserver<patientMonitoringService.DeviceResponse>) responseObserver);
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
        case METHODID_BLOOD_PRESSURE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bloodPressure(
              (io.grpc.stub.StreamObserver<patientMonitoringService.PressureResponse>) responseObserver);
        case METHODID_CALCULATE_PRICE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.calculatePrice(
              (io.grpc.stub.StreamObserver<patientMonitoringService.CalculatetResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PatientMonitoringServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PatientMonitoringServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return patientMonitoringService.PatientMonitoringServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PatientMonitoringService");
    }
  }

  private static final class PatientMonitoringServiceFileDescriptorSupplier
      extends PatientMonitoringServiceBaseDescriptorSupplier {
    PatientMonitoringServiceFileDescriptorSupplier() {}
  }

  private static final class PatientMonitoringServiceMethodDescriptorSupplier
      extends PatientMonitoringServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PatientMonitoringServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PatientMonitoringServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PatientMonitoringServiceFileDescriptorSupplier())
              .addMethod(getMonitoringDeviceOnOffMethod())
              .addMethod(getBloodPressureMethod())
              .addMethod(getCalculatePriceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
