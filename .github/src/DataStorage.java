import java.util.List;

public class DataStorage implements DataAPI {

    //Simply reads the data in the InputConfig type, and handles where it should go.
    //
    private List<Integer> userInputData;

    public DataReadResult read(InputConfig input) {

        userInputData = input.getInputs(); //grabs the List<Integer>
        //if successfull return SUCCESS
        //Something like this to check if the data read was successfull
        if(userInputData.isEmpty()) {
       
            return DataReadResult.FAILURE;
        }
        return DataReadResult.SUCCESS;
        //if failure return FAILURE
    }
    
    public List<Integer> getData(){
        
        return userInputData;
    }

    @Override
    public DataWriteResult writeSingleResult(OutputConfig output, String result) {
      
        
        if(result!=null){
            
            return DataWriteResult.SUCCESS;
        }
        else{

            return DataWriteResult.FAILURE;
        }
    }
    
}
