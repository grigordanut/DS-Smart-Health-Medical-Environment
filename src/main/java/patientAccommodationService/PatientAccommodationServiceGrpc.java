package patientAccommodationService;

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
    comments = "Source: patientAccommodation.proto")
public final class PatientAccommodationServiceGrpc {

  private PatientAccommodationServiceGrpc() {}

  public static final String SERVICE_NAME = "patientAccommodationService.PatientAccommodationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<patientAccommodationService.RegisterRequest,
      patientAccommodationService.RegisterResponse> getRegisterPatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerPatient",
      requestType = patientAccommodationService.RegisterRequest.class,
      responseType = patientAccommodationService.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<patientAccommodationService.RegisterRequest,
      patientAccommodationService.RegisterResponse> getRegisterPatientMethod() {
    io.grpc.MethodDescriptor<patientAccommodationService.RegisterRequest, patientAccommodationService.RegisterResponse> getRegisterPatientMethod;
    if ((getRegisterPatientMethod = PatientAccommodationServiceGrpc.getRegisterPatientMethod) == null) {
      synchronized (PatientAccommodationServiceGrpc.class) {
        if ((getRegisterPatientMethod = PatientAccommodationServiceGrpc.getRegisterPatientMethod) == null) {
          PatientAccommodationServiceGrpc.getRegisterPatientMethod = getRegisterPatientMethod = 
              io.grpc.MethodDescriptor.<patientAccommodationService.RegisterRequest, patientAccommodationService.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "patientAccommodationService.PatientAccommodationService", "registerPatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientAccommodationService.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientAccommodationService.RegisterResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientAccommodationServiceMethodDescriptorSupplier("registerPatient"))
                  .build();
          }
        }
     }
     return getRegisterPatientMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PatientAccommodationServiceStub newStub(io.grpc.Channel channel) {
    return new PatientAccommodationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PatientAccommodationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PatientAccommodationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PatientAccommodationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PatientAccommodationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PatientAccommodationServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientAccommodationService.RegisterRequest> registerPatient(
        io.grpc.stub.StreamObserver<patientAccommodationService.RegisterResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getRegisterPatientMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterPatientMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                patientAccommodationService.RegisterRequest,
                patientAccommodationService.RegisterResponse>(
                  this, METHODID_REGISTER_PATIENT)))
          .build();
    }
  }

  /**
   */
  public static final class PatientAccommodationServiceStub extends io.grpc.stub.AbstractStub<PatientAccommodationServiceStub> {
    private PatientAccommodationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAccommodationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAccommodationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAccommodationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientAccommodationService.RegisterRequest> registerPatient(
        io.grpc.stub.StreamObserver<patientAccommodationService.RegisterResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getRegisterPatientMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class PatientAccommodationServiceBlockingStub extends io.grpc.stub.AbstractStub<PatientAccommodationServiceBlockingStub> {
    private PatientAccommodationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAccommodationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAccommodationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAccommodationServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class PatientAccommodationServiceFutureStub extends io.grpc.stub.AbstractStub<PatientAccommodationServiceFutureStub> {
    private PatientAccommodationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAccommodationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAccommodationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAccommodationServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REGISTER_PATIENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PatientAccommodationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PatientAccommodationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_PATIENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.registerPatient(
              (io.grpc.stub.StreamObserver<patientAccommodationService.RegisterResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PatientAccommodationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PatientAccommodationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return patientAccommodationService.PatientAccommodationServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PatientAccommodationService");
    }
  }

  private static final class PatientAccommodationServiceFileDescriptorSupplier
      extends PatientAccommodationServiceBaseDescriptorSupplier {
    PatientAccommodationServiceFileDescriptorSupplier() {}
  }

  private static final class PatientAccommodationServiceMethodDescriptorSupplier
      extends PatientAccommodationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PatientAccommodationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PatientAccommodationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PatientAccommodationServiceFileDescriptorSupplier())
              .addMethod(getRegisterPatientMethod())
              .build();
        }
      }
    }
    return result;
  }
}
