import java.util.Scanner;

//Implementation of ComputeRequest, we can change the file name later
public class ComputeRequestTestImpl implements ComputeRequest{

    //instance variables
    Scanner scan = new Scanner(System.in);
    char deliminator = ',';

    @Override
    public InputConfig getInputConfig() {
        
        //local variables
        int[] numList; 
        String input;

        //ask user to get input??
        System.out.println("Please enter numbers with the deliminator of \",\"");
        input = scan.nextLine();

        numList = makeList(input);

        InputConfig inputConfig = new InputConfigTestImpl(numList);

        return inputConfig;

    }

    @Override
    public OutputConfig getOutputConfig() {
        
        //im honestly not sure if this is correct
        OutputConfig outputConfig = new OutputConfigTestImpl();
        return outputConfig;
    }

    @Override
    public char getDelimeter() {
        return deliminator;
    }

    //converts string array into int array
    public int[] makeList(String numbers) {

        String[] listStr = numbers.split(deliminator + ""); //convert deliminator to String(was char)
        int[] list = new int[listStr.length];

        for(int i = 0; i < listStr.length - 1; i++) {
            list[i] = Integer.parseInt(listStr[i]);
        }

        return list;

    }

    
}
