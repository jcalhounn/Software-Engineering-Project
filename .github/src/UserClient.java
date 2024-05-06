import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
//
//import phoneservice.PhoneOrderServiceGrpc.PhoneOrderServiceBlockingStub;
//import phoneservice.PhoneService.PhoneOrderRequest;
//import phoneservice.PhoneService.PhoneOrderResponse;

//Client creating ComputeRequest

public class UserClient { // Boilerplate TODO: change to <servicename>Client
    private final ComputeAPIGrpc.ComputeAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public UserClient(Channel channel) {
        blockingStub = ComputeAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    public void request() {

        //user fills out input output configs and delimiter

        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName(".github/test/testInputFile.txt").build();
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName(".github/test/testOutputFile.txt").build();
        UserProto.ComputeRequest request = UserProto.ComputeRequest.newBuilder().setInput(inputConfig).setOutput(outputConfig).setDelimiter(",").build();
        UserProto.ComputeResult response;
        try {
            response = blockingStub.compute(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return;
        }
        if (response==null) {
            System.err.println("Failure Response"); //+ response.getErrorMessage());
        } else {
            System.out.println("Compute Success!"); //+ response.getOrderNumber());
        }
    }

    public static void main(String[] args) throws Exception {

        //TODO: system.out.print to user and outputing to terminal (keep super basic, bc we will be implementing a GUI soon after)
        //TODO: Move the UserProto. variables to here so we can scan user inputs and set them here.

        String target = "localhost:50051";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            UserClient client = new UserClient(channel); // Boilerplate TODO: update to this class name
            client.request();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
