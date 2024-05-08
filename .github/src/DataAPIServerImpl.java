public class DataAPIServerImpl extends DataAPIGrpc.DataAPIImplBase {

    DataAPIImpl dataAPI;

    public DataAPIServerImpl(DataAPIImpl dataAPI) {
        this.dataAPI = dataAPI;
    }

    public void appendSingleResult(UserProto.Output request,
                                    io.grpc.stub.StreamObserver<UserProto.DataWriteResult> responseObserver) {

        FileOutputConfig outputConfig = new FileOutputConfig(request.getOutput().getFileName());
        DataWriteResult writeResult = dataAPI.appendSingleResult(outputConfig,request.getResult(),request.getDelimiter().charAt(0));


        //TODO: Convert writeResult to Proto
        int value=writeResult.getStatus().ordinal();
        UserProto.DataWriteResult.WriteResultStatus protoWriteResult =
                UserProto.DataWriteResult.WriteResultStatus.forNumber(value+1);

        UserProto.DataWriteResult protoResult = UserProto.DataWriteResult.newBuilder().setStatus(protoWriteResult).build();

        responseObserver.onNext(protoResult); //

        responseObserver.onCompleted();

    }

    public void read(UserProto.InputConfig request,
                      io.grpc.stub.StreamObserver<UserProto.Page> responseObserver) {

        //TODO: Convert UserProto.InputConfig to INputConfig and return using streamobserver on completed *Reference the ComputeAPIServerImpl
        FileInputConfig inputConfig = new FileInputConfig(request.getFileName());


        responseObserver.onNext();

        responseObserver.onCompleted();

    }
}

