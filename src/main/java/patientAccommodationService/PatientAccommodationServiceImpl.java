// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: patientAccommodation.proto

package patientAccommodationService;

public final class PatientAccommodationServiceImpl {
  private PatientAccommodationServiceImpl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_RegisterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_RegisterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_RegisterResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_RegisterResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_DisplayRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_DisplayRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_DisplayResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_DisplayResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_CalculateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_CalculateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_patientAccommodationService_CalculatetResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_patientAccommodationService_CalculatetResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032patientAccommodation.proto\022\033patientAcc" +
      "ommodationService\"<\n\017RegisterRequest\022\014\n\004" +
      "name\030\001 \001(\t\022\013\n\003age\030\002 \001(\t\022\016\n\006gender\030\003 \001(\t\"" +
      "\"\n\020RegisterResponse\022\016\n\006result\030\001 \001(\t\"!\n\016D" +
      "isplayRequest\022\017\n\007patList\030\001 \001(\t\"&\n\017Displa" +
      "yResponse\022\023\n\013allPatients\030\001 \001(\t\"\235\001\n\020Calcu" +
      "lateRequest\022\022\n\nnumberDays\030\001 \001(\005\022B\n\005price" +
      "\030\002 \001(\01623.patientAccommodationService.Cal" +
      "culateRequest.Price\"1\n\005Price\022\n\n\006PUBLIC\020\000" +
      "\022\017\n\013SEMIPRIVATE\020\001\022\013\n\007PRIVATE\020\002\"(\n\022Calcul" +
      "atetResponse\022\022\n\ntotalPrice\030\001 \001(\0052\371\002\n\033Pat" +
      "ientAccommodationService\022r\n\017registerPati" +
      "ent\022,.patientAccommodationService.Regist" +
      "erRequest\032-.patientAccommodationService." +
      "RegisterResponse\"\000(\001\022p\n\017displayPatients\022" +
      "+.patientAccommodationService.DisplayReq" +
      "uest\032,.patientAccommodationService.Displ" +
      "ayResponse\"\0000\001\022t\n\016calculatePrice\022-.patie" +
      "ntAccommodationService.CalculateRequest\032" +
      "/.patientAccommodationService.Calculatet" +
      "Response\"\000(\001B@\n\033patientAccommodationServ" +
      "iceB\037PatientAccommodationServiceImplP\001b\006" +
      "proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_patientAccommodationService_RegisterRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_patientAccommodationService_RegisterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_RegisterRequest_descriptor,
        new java.lang.String[] { "Name", "Age", "Gender", });
    internal_static_patientAccommodationService_RegisterResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_patientAccommodationService_RegisterResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_RegisterResponse_descriptor,
        new java.lang.String[] { "Result", });
    internal_static_patientAccommodationService_DisplayRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_patientAccommodationService_DisplayRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_DisplayRequest_descriptor,
        new java.lang.String[] { "PatList", });
    internal_static_patientAccommodationService_DisplayResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_patientAccommodationService_DisplayResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_DisplayResponse_descriptor,
        new java.lang.String[] { "AllPatients", });
    internal_static_patientAccommodationService_CalculateRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_patientAccommodationService_CalculateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_CalculateRequest_descriptor,
        new java.lang.String[] { "NumberDays", "Price", });
    internal_static_patientAccommodationService_CalculatetResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_patientAccommodationService_CalculatetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_patientAccommodationService_CalculatetResponse_descriptor,
        new java.lang.String[] { "TotalPrice", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
