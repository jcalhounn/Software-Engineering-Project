import io.grpc.*;

import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

public class EngineClient {

//Client creating ComputeRequest
 // Boilerplate TODO: change to <servicename>Client
    private final DataAPIGrpc.DataAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public EngineClient(Channel channel) {
        blockingStub = DataAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    public void request() {

        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName("test.txt").build();
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName("test.txt").build();
        UserProto.Iterable response;
        try {
            response = blockingStub.read(inputConfig);
        } catch (StatusRuntimeException e) {
            return;
        }
        if (response==null) {
            System.err.println("Failure Response"); //+ response.getErrorMessage());
        } else {
            System.out.println("Compute Success!"); //+ response.getOrderNumber());
        }
    }

    public static void main(String[] args) throws Exception {
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
