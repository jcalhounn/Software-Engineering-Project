import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

/* Assignment 4 instructions: In the 'src' folder, implement the 
computation you chose and added to the README in the computation component 
of the engine. Remember to use good coding style! Choose descriptive variable names, 
and break out sub-routines into their own methods */

//EngineAPI Implementation
public class EngineCompute implements EngineAPI {

    private List<Integer> decInputs;
    private int decGCD;
    private String hexGCD;
    private List<String> hexInputs;
    private int comparable;

    //public EngineCompute() {

    //}

    //not sure if this is needed since our integration test does not 
    //have anything in constructor. but idk how else we would get the
    //inputs to compute

    // j commented out 2/22

    public void setDecInputs(List<Integer> inDecInputs) {
        this.decInputs = inDecInputs;
    }

    public List<String> getHexOutput(){
        
        return getHexInputs();
    }
    
    @Override
    public EngineComputeResult compute(EngineComputeRequest request) {
    //public EngineComputeResult compute(List<Integer> request) {
        //not sure if this is a valid comparison since its giving me a warning
        //j- This comparison is looking at two slightly different enum types. One of type EngineComputeRequest, and one of EngineComputeRequestStatus
        //Fix - Before
        //request.getStatus().equals(EngineComputeRequest.SUCCESS
        //after
        //this if statement isn't relevant anymore
        //if(request.equals(EngineComputeResult.SUCCESS)) { 
            // request is an EngineComputeRequest type, this has since been changed to a list of methods instead of status interface 
                this.hexInputs = toHex(this.decInputs);
                //do computation
                this.decGCD = getGCD(this.decInputs);
                this.hexGCD = toHex(this.decGCD);
                if(this.hexInputs==null||this.decGCD==comparable||this.hexGCD==null){

                    return EngineComputeResult.FAILURE;
                }
                return EngineComputeResult.SUCCESS;
            //at this point i am confused on whether i should be sending back
            //a message OR the organized output. So I added getmethod for everything for now

            // I believe having the get method is a good idea, and should only return values
            // if the compute is a success 
            // I believe the if statement at the top of this block should compare if the computation was completed
            // if true, change local data and return success
            // else, return failure and dont change local data 

            

    }


    //finds greatest common divisor of a list of integers
    private int getGCD(List<Integer> decInputs) {

        int gcd = decInputs.get(0);

        for(int i = 1; i < decInputs.size(); i++) {
            gcd = getGCD(gcd, decInputs.get(i));
        }

        return gcd;
    }

    //finds GCD of two numbers using Euclid's algorithmn
    private int getGCD(int numOne, int numTwo) {

        //Euclid's algorithm
        while(numTwo != 0){

            int temp = numTwo;
            numTwo = numOne % numTwo;
            numOne = temp;
        }

        return numOne;
    }
    private List<String> toHex(List<Integer> decInputs) {

        List<String> hexInputs = new ArrayList<>();
        
        for(int i = 0; i < decInputs.size() - 1; i++) {

            hexInputs.add(toHex(decInputs.get(i)));
        }

        return hexInputs;
    }
    private String toHex(int num) {

        StringBuilder hexNum = new StringBuilder();

        //incase number is 0 (even though it should be only positive numbers if all exceptions are implemented correctly?)
        if(num == 0){
            hexNum.insert(0, 0);
        }else{

            while(num > 0){

                int remainder = num % 16;
                char hexDigit = getHexDigit(remainder);
                hexNum.insert(0, hexDigit);
                num /= 16;
            }
        }

        return hexNum.toString();
    }

    private char getHexDigit(int remainder) {
        
        char digit = '0';

        if(remainder < 10){

            digit = (char) ('0' + remainder); //takes the character 0(ASCII) and adds the remainder to get the remainder ASCII value
        }else if(remainder >= 10){ //can only be betwen 10 & 15 since were dividing by 16

            digit = (char) ('A' + remainder - 10); //finds correct ASCII value
        }

        return digit;
    }

    //GETTER METHODS THAT WE MIGHT REMOVE LATER
    public List<Integer> getDecInputs() {
        return decInputs;
    }

    public int getDecGCD() {
        return decGCD;
    }

    public String getHexGCD() {
        return hexGCD;
    }

    public List<String> getHexInputs() {
        return hexInputs;
    }

    
    
    
}
