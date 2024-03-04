import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.jupiter.api.Test;



public class ComputeAPISmokeTest {

    @Test
    public void smokeTest() {
        DataAPIImpl dataAPI = Mockito.mock(DataAPIImpl.class);

        ComputeRequest request = Mockito.mock(ComputeRequest.class);
        EngineCompute engineAPI = Mockito.mock(EngineCompute.class);

        ComputeAPI computeAPI = new EngineManager(dataAPI, engineAPI);
        ComputeResult result =  computeAPI.compute(request);
        Assert.assertEquals(result.getStatus(), ComputeResult.ComputeResultStatus.SUCCESS);
        

    }

}
//done