import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class DataAPISmokeTest {
    
    @Test
    public void readSmokeTest() {

        
        InputConfig input = Mockito.mock(InputConfig.class);
        DataAPI dataAPI = new DataAPIImpl();
        
        DataReadResult readResult = dataAPI.read(input);

        Assert.assertEquals(readResult.getStatus(),DataReadResult.DataResult.SUCCESS);

    }

    @Test
    public void writeSmokeTest() {

        OutputConfig output = Mockito.mock(OutputConfig.class);
        String result = "";

        DataAPI dataAPI = new DataAPIImpl();

        DataWriteResult writeResult = dataAPI.writeSingleResult(output,result);

        Assert.assertEquals(writeResult.getStatus(),DataWriteResult.WriteResultStatus.SUCCESS);


    }
}
