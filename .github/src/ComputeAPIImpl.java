
public class ComputeAPIImpl implements ComputeAPI{

    //instance variables 
    DataAPI dataAPI;
    EngineAPI engineAPI;

    ComputeAPIImpl(DataAPI dataAPI, EngineAPI engineAPI) {
        this.dataAPI = dataAPI;
        this.engineAPI = engineAPI;
    }

    @Override
    public ComputeResult compute(ComputeRequest request) {
        // TODO Auto-generated method stub
        return ComputeResult.SUCCESS;
    }

}