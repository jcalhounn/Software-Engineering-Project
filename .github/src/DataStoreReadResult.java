
//decides how to model the arbitrarily-large number of integer inputs
public interface DataStoreReadResult {
	DataReadResult getStatus();
	
	public static enum DataReadResult {
		SUCCESS,
		FAILURE;
	}
}
