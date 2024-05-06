public class DataAPIServerImpl extends DataAPIGrpc.DataAPIImplBase {

    DataAPIImpl dataAPI;

    public DataAPIServerImpl(DataAPIImpl dataAPI) {
        this.dataAPI = dataAPI;
    }

    public void appendSingleResult(UserProto.Output request,
                                    io.grpc.stub.StreamObserver<UserProto.DataWriteResult> responseObserver) {

        FileOutputConfig outputConfig = new FileOutputConfig(request.getOutput().getFileName());


        DataWriteResult writeResult = dataAPI.appendSingleResult(outputConfig,request.getResult(),request.getDelimiter().charAt(0));


    }

    public void read(UserProto.InputConfig request,
                      io.grpc.stub.StreamObserver<UserProto.Page> responseObserver) {



    }
}

