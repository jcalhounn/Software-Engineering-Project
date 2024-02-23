import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import org.mockito.Mockito;

public class DataAPIImpl implements DataAPI  {

    private List<Integer> userInputData = new ArrayList<>();;

    @Override
    public DataReadResult read(InputConfig input) {
        
        /*try{
            //reader passes new FileReader(String), where the string is the name of the file gotten from the InputConfig variable 
            BufferedReader reader = new BufferedReader(new FileReader(input.getInputLocation()));
           
            //data allows for detecting lines of data
            String data;
            while((data = reader.readLine())!=null){
                
                //This reads each line in the input file Location, and splits the string at delimiter locations, into a string array
                //Then, the for loop will execute for the duration of the array length, and add the integers to the List<Integer> userInputData
                //by parsing the Strings to an int
                String[] temp = reader.readLine().split(",");
                for(int j = 0;j < temp.length;j++){
                    userInputData.add(Integer.parseInt(temp[j]));
                }
                //resets the string array
                temp=null;

                reader.close(); // probably can change this logic to get next line and then close after whole files is done
            }
        }
        catch(IOException e){

            e.printStackTrace();
        }
        */ /************ UNCOMMENT THE FILE READER IN THE FUTURE. It is commented out to simplify the implementation tests***********/
        userInputData.add(1);//TEMPORARY Population
        //Something like this to check if the data read was successfull
        if(userInputData.isEmpty()) {
       
            return DataReadResult.FAILURE;
        }
        return DataReadResult.SUCCESS;
        
    // Essentially this block is returning SUCCESS or FAILURE to the EngineManager,
    // while storing the data that was read, into the local variable "userInputData"
    // this will now be accessable later when the getData() method is called
    // which again can only happen if this block returns a SUCCESS.
    }

    //Strings for now to test reading and writing
    public List<Integer> getData() {

        return userInputData;// copied from dataStorage
    }

    @Override
    public DataWriteResult writeSingleResult(OutputConfig output, String result) {
      
        /*try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(output.getOutputLocation()));
            writer.write(result);
            writer.close();
           
        return DataWriteResult.SUCCESS;
        }
        catch (IOException e){
            e.printStackTrace();
            return DataWriteResult.FAILURE;
        }*/
        // UNCOMMENT BLOCK ABOVE IN THE FUTURE. It is commented to simplify testing
        //Something like this to check if the data read was successfull
        if(output.equals(null)) {
       
            return DataWriteResult.FAILURE;
        }
        return DataWriteResult.SUCCESS;
    }
    
}
