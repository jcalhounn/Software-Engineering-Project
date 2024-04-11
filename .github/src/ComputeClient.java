
import io.grpc.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//USER FRONTEND
public class ComputeClient {

    private final ComputeServerGrpc.ComputeServerBlockingStub blockingStub;

    public ComputeClient(Channel channel) {
        blockingStub = ComputeServerGrpc.newBlockingStub(channel);
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    public void compute() {
        ComputeRequest request = ComputeRequest.newBuilder().build();
        PhoneOrderResponse response;
        try {
            response = blockingStub.createPhoneOrder(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        if (response.hasErrorMessage()) {
            System.err.println("Error: " + response.getErrorMessage());
        } else {
            System.out.println("Order number: " + response.getOrderNumber());
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //display menu
        // Main menu
        System.out.println("Welcome to the Our Program"); //TODO: add a description of program
        System.out.println("1. Start a Job");

        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                startJob(scan);
                break;
            case 2:
                System.out.println("Thank you for using Our Program. Exiting...");
        }

        //make input int arrayList

        Server.Input.Builder builder = Server.Input.newBuilder()
                .setDelimiter(" ")
                //add while loop here
                .addNumberList(scan.nextInt());

        blockingStub.compute(builder.build());

        String target = "localhost:50051";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            PhoneOrderClient client = new PhoneOrderClient(channel); // Boilerplate TODO: update to this class name
            client.order();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

    }

    private static void startJob(Scanner scan) {

        processDelimiter(scan);

        // Sub menu for starting a job
        System.out.println("Choose how to provide input:");
        System.out.println("1. Enter a list of integers");
        System.out.println("2. Enter the path to a file containing a list of integers");

        // Process user choice
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                // Handle entering a list of integers
                processListInput(scan);
                break;
            case 2:
                // Handle entering a file path
                processFileInput(scan);
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                break;
        }
    }

    private static void processFileInput(Scanner scan) {
        // Handle entering the path to a file containing a list of integers
        System.out.println("Enter the path to the file containing a list of integers:");
        String filePath = scan.next();

        // Process output file
        processOutputFile(scan);
    }

    private static void processOutputFile(Scanner scan) {
        // Handle output file
        System.out.println("Enter the path to the output file:");
        String outputFile = scan.next();

        // Perform the processing
        // This is where you would perform your job processing logic
        System.out.println("Job processing completed!");
    }

    private static void processListInput(Scanner scan) {

        // Handle entering a list of integers
        System.out.println("Enter a list of integers separated by spaces:");
        scan.nextLine(); // Consume newline
        String input = scan.nextLine();

        // Process output file
        processOutputFile(scan);

    }

    private static void processDelimiter(Scanner scan) {

        System.out.println("Choose a delimiter:");
        System.out.println("1. Default: Spaces ");
        System.out.println("2. Specify delimiter");

        // Process user choice
        int delimiterChoice = scan.nextInt();
        String delimiter = "";
        switch (delimiterChoice) {
            case 1:
                break; // No delimiter
            case 2:
                System.out.println("Enter the delimiter:");
                delimiter = scan.next(); //TODO: actually do something with this 
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                return;
        }
    }
}







