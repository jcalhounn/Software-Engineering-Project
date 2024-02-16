
public interface WriteResult {
  
	static WriteResult SUCCESS = () -> WriteResultStatus.SUCCESS;
	static WriteResult FAILURE = () -> WriteResultStatus.FAILURE;


	WriteResultStatus getStatus();
	
	public static enum WriteResultStatus 
	{
		SUCCESS,
		FAILURE;
	}
}
