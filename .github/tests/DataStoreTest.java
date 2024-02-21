import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class DataStoreTest {
    
    @Test
    public void dataStoreAPITest() {

        DataAPI mockInterface = Mockito.mock(DataAPI.class);
        when(mockInterface.read(any(InputConfig.class))).thenReturn(DataReadResult.SUCCESS);
        when(mockInterface.writeSingleResult(any(OutputConfig.class), null)).thenReturn(WriteResult.SUCCESS);

        //read to first input
        //while there are more inputs
            //if success, write to output
            //else error
            //if sucess, read next input
            //else error
        //END

    }
}
