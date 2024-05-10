import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

//Client creating ComputeRequest

public class UserClient {

    private final ComputeAPIGrpc.ComputeAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public UserClient(Channel channel) {
        blockingStub = ComputeAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }


    //Explaination 2
    //We need to send our code across the network and this process is buffered by a protocol file
    //this means that the data types that our project expects to see is defined in the proto file
    //To send our actual request through the compute method, the proto is structured in a way that
    //states what data type will be expected as a parameter and as the return type
    //The blockingStub.compute(request) is an rpc service defined in the proto, so it needs proto parameters
    //these parameters are set through the initialization of inputConfig,and outputConfig, and explicitly for delimiter
    //this proto readable request is then sent across the channel to the EngineServer
    //Continued on EngineServer

    public void request() {

        //TODO: user fills out input output configs and delimiter attached through GUI

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


    //EXPLANATION 1
    //UserClient wants to make a compute request across a "network"
    //To do this, we host a server on "EngineServer"
    //To connect to this server we use the target address "localhost:50051" that matches up to the port declared in EngineSerer
    //The channel is then built between the client and server.
    //we then call the request method on our UserClient object
    //Continued above.

    public static void main(String[] args) throws Exception {

        //TODO: system.out.print to user and outputting to terminal (keep super basic, bc we will be implementing a GUI soon after)
        //TODO: Move the UserProto. variables to here so we can scan user inputs and set them here.


        String target = "localhost:50054";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            UserClient client = new UserClient(channel); // Boilerplate TODO: update to this class name
            client.request();//
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
