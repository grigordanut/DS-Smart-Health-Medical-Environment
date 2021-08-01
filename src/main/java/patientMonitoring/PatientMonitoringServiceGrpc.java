package patientMonitoring;

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

  public static final String SERVICE_NAME = "patientMonitoring.PatientMonitoringService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<patientMonitoring.DeviceRequest,
      patientMonitoring.DeviceResponse> getMonitoringDeviceOnOffMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "monitoringDeviceOnOff",
      requestType = patientMonitoring.DeviceRequest.class,
      responseType = patientMonitoring.DeviceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<patientMonitoring.DeviceRequest,
      patientMonitoring.DeviceResponse> getMonitoringDeviceOnOffMethod() {
    io.grpc.MethodDescriptor<patientMonitoring.DeviceRequest, patientMonitoring.DeviceResponse> getMonitoringDeviceOnOffMethod;
    if ((getMonitoringDeviceOnOffMethod = PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod) == null) {
      synchronized (PatientMonitoringServiceGrpc.class) {
        if ((getMonitoringDeviceOnOffMethod = PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod) == null) {
          PatientMonitoringServiceGrpc.getMonitoringDeviceOnOffMethod = getMonitoringDeviceOnOffMethod = 
              io.grpc.MethodDescriptor.<patientMonitoring.DeviceRequest, patientMonitoring.DeviceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "patientMonitoring.PatientMonitoringService", "monitoringDeviceOnOff"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoring.DeviceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoring.DeviceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientMonitoringServiceMethodDescriptorSupplier("monitoringDeviceOnOff"))
                  .build();
          }
        }
     }
     return getMonitoringDeviceOnOffMethod;
  }

  private static volatile io.grpc.MethodDescriptor<patientMonitoring.PressureRequest,
      patientMonitoring.PressureResponse> getBloodPressureMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bloodPressure",
      requestType = patientMonitoring.PressureRequest.class,
      responseType = patientMonitoring.PressureResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<patientMonitoring.PressureRequest,
      patientMonitoring.PressureResponse> getBloodPressureMethod() {
    io.grpc.MethodDescriptor<patientMonitoring.PressureRequest, patientMonitoring.PressureResponse> getBloodPressureMethod;
    if ((getBloodPressureMethod = PatientMonitoringServiceGrpc.getBloodPressureMethod) == null) {
      synchronized (PatientMonitoringServiceGrpc.class) {
        if ((getBloodPressureMethod = PatientMonitoringServiceGrpc.getBloodPressureMethod) == null) {
          PatientMonitoringServiceGrpc.getBloodPressureMethod = getBloodPressureMethod = 
              io.grpc.MethodDescriptor.<patientMonitoring.PressureRequest, patientMonitoring.PressureResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "patientMonitoring.PatientMonitoringService", "bloodPressure"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoring.PressureRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientMonitoring.PressureResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientMonitoringServiceMethodDescriptorSupplier("bloodPressure"))
                  .build();
          }
        }
     }
     return getBloodPressureMethod;
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
    public void monitoringDeviceOnOff(patientMonitoring.DeviceRequest request,
        io.grpc.stub.StreamObserver<patientMonitoring.DeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMonitoringDeviceOnOffMethod(), responseObserver);
    }

    /**
     * <pre>
     *Bidirectional
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoring.PressureRequest> bloodPressure(
        io.grpc.stub.StreamObserver<patientMonitoring.PressureResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getBloodPressureMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMonitoringDeviceOnOffMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                patientMonitoring.DeviceRequest,
                patientMonitoring.DeviceResponse>(
                  this, METHODID_MONITORING_DEVICE_ON_OFF)))
          .addMethod(
            getBloodPressureMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                patientMonitoring.PressureRequest,
                patientMonitoring.PressureResponse>(
                  this, METHODID_BLOOD_PRESSURE)))
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
    public void monitoringDeviceOnOff(patientMonitoring.DeviceRequest request,
        io.grpc.stub.StreamObserver<patientMonitoring.DeviceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMonitoringDeviceOnOffMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Bidirectional
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientMonitoring.PressureRequest> bloodPressure(
        io.grpc.stub.StreamObserver<patientMonitoring.PressureResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getBloodPressureMethod(), getCallOptions()), responseObserver);
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
    public patientMonitoring.DeviceResponse monitoringDeviceOnOff(patientMonitoring.DeviceRequest request) {
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
    public com.google.common.util.concurrent.ListenableFuture<patientMonitoring.DeviceResponse> monitoringDeviceOnOff(
        patientMonitoring.DeviceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMonitoringDeviceOnOffMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MONITORING_DEVICE_ON_OFF = 0;
  private static final int METHODID_BLOOD_PRESSURE = 1;

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
          serviceImpl.monitoringDeviceOnOff((patientMonitoring.DeviceRequest) request,
              (io.grpc.stub.StreamObserver<patientMonitoring.DeviceResponse>) responseObserver);
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
              (io.grpc.stub.StreamObserver<patientMonitoring.PressureResponse>) responseObserver);
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
      return patientMonitoring.PatientMonitoringServiceImpl.getDescriptor();
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
              .build();
        }
      }
    }
    return result;
  }
}
