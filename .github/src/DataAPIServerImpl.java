import java.util.Iterator;

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

        //TODO: Convert UserProto.InputConfig to InputConfig and return using stream observer on completed *Reference the ComputeAPIServerImpl
        FileInputConfig inputConfig = new FileInputConfig(request.getFileName());


        UserProto.Page page = UserProto.Page.newBuilder().build();
        int pageNumber = page.getCurrentPage();
        Iterable<Integer> list = dataAPI.read(inputConfig);

        for(int i=((pageNumber*100) - 100);i<99*pageNumber;i++){


        }


//        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
//                iterator = list.iterator();
//        }
//
//        list.forEach(page::getResults);

//        for(int i : list) {
//            page.getResults(i);
//        }
        responseObserver.onNext(page);
        responseObserver.onCompleted();
    }


}

