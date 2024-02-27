import java.util.List;

import org.mockito.Mockito;

public class ComputeAPIImpl implements ComputeAPI{

    //instance variables 
    DataAPI dataAPI;
    EngineAPI engineAPI;

    ComputeAPIImpl(DataAPI dataAPI, EngineAPI engineAPI) {
        this.dataAPI = dataAPI;
        this.engineAPI = engineAPI;
    }

    @Override
    public ComputeResult compute(EngineComputeRequest request) {
        // TODO Auto-generated method stub
        EngineComputeResult result = engineAPI.compute(request);

        if(result == EngineComputeResult.SUCCESS)
        {
                
                return ComputeResult.SUCCESS;
        }
        else
            return ComputeResult.FAILURE;
    }

    public List<String> getHexInputs()
    {
        return ((ComputeAPIImpl) engineAPI).getHexInputs();
    }

}