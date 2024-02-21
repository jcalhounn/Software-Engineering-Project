//decides how to model the arbitrarily-large number of integer inputs
public interface DataReadResult {

	static DataReadResult SUCCESS = () -> DataResult.SUCCESS;
	static DataReadResult FAILURE = () -> DataResult.FAILURE;


	DataResult getStatus();
	
	public static enum DataResult {
		SUCCESS,
		FAILURE;
	}

}
