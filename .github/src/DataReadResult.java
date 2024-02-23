//decides how to model the arbitrarily-large number of integer inputs
public interface DataReadResult {


	static DataReadResult SUCCESS = () -> ReadResult.SUCCESS;
	static DataReadResult FAILURE = () -> ReadResult.FAILURE;


	ReadResult getStatus();
	
    //CHANGE NAME TO READRESULT PLEASE
	//Done
	public static enum ReadResult {
		SUCCESS,
		FAILURE;
	}



}
