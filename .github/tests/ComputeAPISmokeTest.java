import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class ComputeAPISmokeTest {

    @Test
    public void smokeTest() {
        DataAPI dataAPI = Mockito.mock(DataAPI.class);

        ComputeRequest request = Mockito.mock(ComputeRequest.class);
        EngineAPI engineAPI = Mockito.mock(EngineAPI.class);

        ComputeAPI computeAPI = new ComputeAPIImpl(dataAPI, engineAPI);
        ComputeResult result =  computeAPI.compute(request);
        Assert.assertEquals(result.getStatus(), ComputeResult.ComputeResultStatus.SUCCESS);


    }

}
