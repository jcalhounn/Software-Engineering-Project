import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ComputeEngineIntegrationTest {

    @Test
    public void computeEngineIntegrationTest()
    {
        List<Integer> list = Arrays.asList(1,2,3);
        List<String> output = null;

        //initialinput
        InputConfigTest inputTest = new InputConfigTest();
        inputTest.configureInput(list);

        //validation
        OutputConfigTest outputTest = new OutputConfigTest();
        outputTest.configureOutput(output);
        
    }
    
}
