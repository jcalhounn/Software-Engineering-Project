/*in-memory implementation of DataStore API that accepts input
* and output from our in memory inputConfigTest and outputConfigTest
*
*/

import java.util.List;

import org.mockito.Mockito;

public class DataAPITestImp implements DataAPI {

    private List<Integer> userInputData;

    /*ASK PROFESSOR ABOUT THIS METHOD LOLOLOL*/
    @Override
    public DataReadResult read(InputConfig input) {
        //since this is test code, we can assume its getting the right type of data. 
        //for our production code we want some better input validation
        

        //im not sure how we would return this as she does in her test sample because we 
        // are using a wrapper class. 
        userInputData = ((InputConfigTestImpl)input).getInputs();

        //assuming the data transfer is successful
        return () -> DataReadResult.ReadResult.SUCCESS;
    }

    public List<Integer> getData() {

        return userInputData;// copied from dataStorage
    }

    @Override
    public DataWriteResult writeSingleResult(OutputConfig output, String result) {
        
        ((OutputConfigTestImpl)output).getOutputMutable().add(result);

        return () -> DataWriteResult.WriteResultStatus.SUCCESS;
       
    }
    
}
