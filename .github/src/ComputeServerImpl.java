import io.grpc.stub.StreamObserver;

public class ComputeServerImpl extends ComputeServerGrpc.ComputeServerImplBase {
    public void compute(ComputeRequest request,
                        StreamObserver<Server.ComputeResult.Builder> responseObserver) {
        Server.ComputeResult.Builder response; //TODO: make sure this is supposed to be a builder

        try {
            response = Server.ComputeResult.newBuilder();
            Server.ComputeResult.ComputeResultStatus status = createComputation();

            response
                    .setStatus(status)
                    .build();

        } catch (Exception e) {
            //TODO: add error here?
            response = Server.ComputeResult.newBuilder()
                    .setStatus(Server.ComputeResult.ComputeResultStatus.FAILURE);
            response.build();
            e.printStackTrace();

        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public Server.ComputeResult.ComputeResultStatus
    createComputation() {

        //TODO: not completed code.
            return Server.ComputeResult.ComputeResultStatus.SUCCESS;
    }
}
