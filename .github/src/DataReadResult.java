//decides how to model the arbitrarily-large number of integer inputs
public interface DataReadResult {


	static DataReadResult SUCCESS = () -> DataResult.SUCCESS;
	static DataReadResult FAILURE = () -> DataResult.FAILURE;


	DataResult getStatus();
	
    //CHANGE NAME TO READRESULT PLEASE
	public static enum DataResult {
		SUCCESS,
		FAILURE;
	}



}
