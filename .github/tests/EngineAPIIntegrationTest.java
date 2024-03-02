import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

//API#3 test (ENGINEAPI)
public class EngineAPIIntegrationTest {

    @Test
    public void testComputeWorkflow() {

        //API 3
        EngineAPI engine = new EngineCompute();

        //API 2   ask about datatype "DataAPITestTemp" at beginning rather than DataAPI
        DataAPITestImp testDataAPI = new DataAPITestImp();

        //API 1
        ComputeAPI computeAPI = new ComputeAPIImpl(testDataAPI, engine);

        //using the variable-length int constructor (int...) to avoid having to create an array and 
        //manually enter these values. (this makes it easier to test over and over)
        InputConfigTestImpl input = new InputConfigTestImpl(1,10,25);

        OutputConfigTestImpl output = new OutputConfigTestImpl();

        //could be a real implementation, test implementation, or a mock (in this case were doing a mock)
        ComputeRequest mockRequest = Mockito.mock(ComputeRequest.class);
        when(mockRequest.getInputConfig()).thenReturn(input);
        when(mockRequest.getOutputConfig()).thenReturn(output);

        //we might need to add a delimiter once we start implementing

        ComputeResult result = computeAPI.compute(mockRequest);

        Assert.assertEquals(ComputeResult.SUCCESS, result);

        //now we check if the computation really worked! 
        //OUR expected result will be (each number in hex + GCF in hex)
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("10");
        expected.add("25");

        //if everything worked, we should have written out these results to the output
        Assert.assertEquals(expected, output.getOutputMutable());
        
    }
    
}
