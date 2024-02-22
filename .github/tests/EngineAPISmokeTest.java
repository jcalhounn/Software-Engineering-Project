import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class EngineAPISmokeTest {

    @Test
    public void smokeTest() {

        
        EngineComputeRequest request = Mockito.mock(EngineComputeRequest.class);
        
        EngineAPI engineAPI = new EngineCompute();
        EngineComputeResult result =  engineAPI.compute(request);
        Assert.assertEquals(result.getStatus(), EngineComputeResult.EngineComputeResultStatus.SUCCESS);

    }
    
}
