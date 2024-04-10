//"implements ComputationCoordinator" was removed 
// since its supposed to be implemented in the ComputeEngine class
/*
public class User {
    ComputeAPI coordinator;
    ComputeRequest request;


    //takes in the user's request 
    public ComputeResult userCommunication(ComputeRequest request){
        
        return coordinator.compute(request);
    }    
    
}
*/

import java.util.Scanner;

public class UserClient {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //ask user for input
        System.out.println("Enter a list of Integers seperated by Spaces: ");

        //make input int arrayList

        Server.Input.Builder builder = Server.Input.newBuilder()
                .setDelimiter(" ")
                //add while loop here
                .addNumberList(scan.nextInt());

        ComputeServerGrpc.ComputeServerBlockingStub blockingStub;
        blockingStub.compute(builder.build());

    }


}
