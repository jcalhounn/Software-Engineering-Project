import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class EngineAPISmokeTest {

    @Test
    public void smokeTest() {

        
        EngineComputeRequest request = Mockito.mock(EngineComputeRequest.class);
        EngineAPI engineAPI = new EngineCompute();
        List<Integer> templist = new ArrayList<>(2);
        templist.add(1);
        templist.add(11);
        request.setDecInputs(templist);
        //EngineComputeResult result =  engineAPI.compute(Mockito.mock(List.class));
        EngineComputeResult result =  engineAPI.compute(request);
        Assert.assertEquals(result.getStatus(), EngineComputeResult.EngineComputeResultStatus.SUCCESS);
       
        // Expected Result is a FAILURE because the mock Integer List has no real manipulatable data, if data was hard coded then it would be SUCCESS
        //                      ExpectedResult , ActualResult
        //Assert.assertEquals(result.getStatus(), EngineComputeResult.EngineComputeResultStatus.SUCCESS);

    }
    
}
