
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.grpc.*;
import io.grpc.protobuf.services.ProtoReflectionService;

public class EngineServer { // Boilerplate TODO: Change name of class
    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051; // Boilerplate TODO: Consider changing the port (only one server per port)

        //EngineManager object
        String target = "localhost:50052";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();


        EngineManager manager = new EngineManager(new EngineClient(channel),new EngineCompute()); //EngineManager parameters may not work
        //DataAPIImpl will become the DataClient that is added later


        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new ComputeAPIServerImpl(manager)) // pass EngineManager Object in impl()
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        System.out.println("Server started on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    if (server != null) {
                        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        EngineServer server = new EngineServer(); // Boilerplate TODO: Change name of class
        server.start();
        server.blockUntilShutdown();
    }
}
