
//decides how to model the arbitrarily-large number of integer inputs
public interface DataReadResult {

	static DataReadResult SUCCESS = () -> DataResult.SUCCESS;
	static DataReadResult FAILURE = () -> DataResult.FAILURE;
	
	DataReadResult getStatus();
	
	public static enum DataResult {
		SUCCESS,
		FAILURE;
	}

}
