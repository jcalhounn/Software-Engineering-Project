//decides how to model the arbitrarily-large number of integer inputs

import java.util.List;

public interface DataReadResult {

	static DataReadResult SUCCESS = () -> ReadResult.SUCCESS;
	static DataReadResult FAILURE = () -> ReadResult.FAILURE;

	public List<Integer> getResult();
	ReadResult getStatus();
	
	public static enum ReadResult {
		SUCCESS,
		FAILURE;
	}

}
