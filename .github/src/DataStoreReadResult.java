
//decides how to model the arbitrarily-large number of integer inputs
public interface DataStoreReadResult {

	static DataStoreReadResult SUCCESS = () -> DataReadResult.SUCCESS;
	static DataStoreReadResult FAILURE = () -> DataReadResult.FAILURE;
	DataReadResult getStatus();
	
	public static enum DataReadResult {
		SUCCESS,
		FAILURE;
	}

}
