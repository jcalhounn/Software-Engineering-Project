
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
		Iterable<Integer> integers = dataAPI.read(request.getInputConfig());
		for (int val : integers) {
			dataAPI.appendSingleResult(request.getOutputConfig(), engineAPI.compute(val), request.getDelimeter());
		}
		return ComputeResult.SUCCESS;
	}

}