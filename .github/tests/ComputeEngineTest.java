import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class ComputeEngineTest {

    @Test
    public void computeEngineAPITest() {

        ComputeEngine mockInterface = Mockito.mock(ComputeEngine.class);
        when(mockInterface.compute(any(Integer.class))).thenReturn(ComputationResult.SUCCESS);

        //compute integer
        //ComputationResult mockResult = mockInterface.compute(5);

        //if success, get next integer
        //if(mockResult == mockResult.SUCCESS)
        //{
            //next int
        //}
        //else{
            //else error
        //}

    }
    
}
