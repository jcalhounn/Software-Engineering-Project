

public class ComputeAPIServerImpl extends ComputeAPIGrpc.ComputeAPIImplBase {

    EngineManager engineManager;


    public ComputeAPIServerImpl(EngineManager engineManager) {
        this.engineManager = engineManager;
    }
    //Explanaition 4
    //This compute method will convert the proto datatypes into the actual datatypes that the engine needs to compute
    //anything. Once the engine manager makes its changes it will respond with a success or failure.
    //This success or failure is then turned into proto readable type and then sent back
    //Continued on DataServer

    public void compute(UserProto.ComputeRequest request,
                         io.grpc.stub.StreamObserver<UserProto.ComputeResult> responseObserver) {
        //Build EngineManager parameter
        //convert proto request to ComputeRequest
        System.out.println("Delim:" + request.getDelimiter());
        ComputeRequestImpl computeRequest = new ComputeRequestImpl(request.getInput().getFileName(),request.getDelimiter().charAt(0),request.getOutput().getFileName());

        ComputeResult computeResult = engineManager.compute(computeRequest);

        int value = computeResult.getStatus().ordinal();

        UserProto.ComputeResult.ComputeResultStatus computeResultStatus
                = UserProto.ComputeResult.ComputeResultStatus.forNumber(value + 1); //TODO: add a test to this
        UserProto.ComputeResult protoResult = UserProto.ComputeResult.newBuilder().setStatus(computeResultStatus).build();

        responseObserver.onNext(protoResult); //ComputeResult onNext

        responseObserver.onCompleted();//ComputeResult onNext
    }

}
