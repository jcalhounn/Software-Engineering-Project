

public interface ComputationResult {

	static ComputationResult SUCCESS = () -> ComputationResultStatus.SUCCESS;
	static ComputationResult FAILURE = () -> ComputationResultStatus.FAILURE;

	ComputationResultStatus getStatus();
	
	public static enum ComputationResultStatus {
		SUCCESS,
		FAILURE;
	}

}
