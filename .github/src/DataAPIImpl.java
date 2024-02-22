import org.mockito.Mockito;

public class DataAPIImpl implements DataAPI  {

    @Override
    public DataReadResult read(InputConfig input) {
       
        return DataReadResult.FAILURE;
    }

    @Override
    public DataWriteResult writeSingleResult(OutputConfig output, String result) {
       
        return DataWriteResult.FAILURE;
    }
    
}
