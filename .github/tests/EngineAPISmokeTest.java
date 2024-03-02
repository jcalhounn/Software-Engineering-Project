import org.junit.Assert;
import org.junit.Test;


public class EngineAPISmokeTest {

    @Test
    public void smokeTest() {

        EngineAPI engine = new EngineCompute();
		Assert.assertEquals("1", engine.compute(1));
       
    }
    
}
