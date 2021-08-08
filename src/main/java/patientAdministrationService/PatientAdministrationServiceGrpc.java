package patientAdministrationService;

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
    comments = "Source: patient_administration.proto")
public final class PatientAdministrationServiceGrpc {

  private PatientAdministrationServiceGrpc() {}

  public static final String SERVICE_NAME = "PatientAdministrationService.PatientAdministrationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<patientAdministrationService.RegisterRequest,
      patientAdministrationService.RegisterResponse> getRegisterPatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerPatient",
      requestType = patientAdministrationService.RegisterRequest.class,
      responseType = patientAdministrationService.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<patientAdministrationService.RegisterRequest,
      patientAdministrationService.RegisterResponse> getRegisterPatientMethod() {
    io.grpc.MethodDescriptor<patientAdministrationService.RegisterRequest, patientAdministrationService.RegisterResponse> getRegisterPatientMethod;
    if ((getRegisterPatientMethod = PatientAdministrationServiceGrpc.getRegisterPatientMethod) == null) {
      synchronized (PatientAdministrationServiceGrpc.class) {
        if ((getRegisterPatientMethod = PatientAdministrationServiceGrpc.getRegisterPatientMethod) == null) {
          PatientAdministrationServiceGrpc.getRegisterPatientMethod = getRegisterPatientMethod = 
              io.grpc.MethodDescriptor.<patientAdministrationService.RegisterRequest, patientAdministrationService.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "PatientAdministrationService.PatientAdministrationService", "registerPatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientAdministrationService.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  patientAdministrationService.RegisterResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PatientAdministrationServiceMethodDescriptorSupplier("registerPatient"))
                  .build();
          }
        }
     }
     return getRegisterPatientMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PatientAdministrationServiceStub newStub(io.grpc.Channel channel) {
    return new PatientAdministrationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PatientAdministrationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PatientAdministrationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PatientAdministrationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PatientAdministrationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PatientAdministrationServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientAdministrationService.RegisterRequest> registerPatient(
        io.grpc.stub.StreamObserver<patientAdministrationService.RegisterResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getRegisterPatientMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterPatientMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                patientAdministrationService.RegisterRequest,
                patientAdministrationService.RegisterResponse>(
                  this, METHODID_REGISTER_PATIENT)))
          .build();
    }
  }

  /**
   */
  public static final class PatientAdministrationServiceStub extends io.grpc.stub.AbstractStub<PatientAdministrationServiceStub> {
    private PatientAdministrationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAdministrationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAdministrationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAdministrationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<patientAdministrationService.RegisterRequest> registerPatient(
        io.grpc.stub.StreamObserver<patientAdministrationService.RegisterResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getRegisterPatientMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class PatientAdministrationServiceBlockingStub extends io.grpc.stub.AbstractStub<PatientAdministrationServiceBlockingStub> {
    private PatientAdministrationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAdministrationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAdministrationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAdministrationServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class PatientAdministrationServiceFutureStub extends io.grpc.stub.AbstractStub<PatientAdministrationServiceFutureStub> {
    private PatientAdministrationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PatientAdministrationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PatientAdministrationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PatientAdministrationServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REGISTER_PATIENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PatientAdministrationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PatientAdministrationServiceImplBase serviceImpl, int methodId) {
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
              (io.grpc.stub.StreamObserver<patientAdministrationService.RegisterResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PatientAdministrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PatientAdministrationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return patientAdministrationService.PatientAdministrationServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PatientAdministrationService");
    }
  }

  private static final class PatientAdministrationServiceFileDescriptorSupplier
      extends PatientAdministrationServiceBaseDescriptorSupplier {
    PatientAdministrationServiceFileDescriptorSupplier() {}
  }

  private static final class PatientAdministrationServiceMethodDescriptorSupplier
      extends PatientAdministrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PatientAdministrationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PatientAdministrationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PatientAdministrationServiceFileDescriptorSupplier())
              .addMethod(getRegisterPatientMethod())
              .build();
        }
      }
    }
    return result;
  }
}
