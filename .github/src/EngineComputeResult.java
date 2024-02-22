

public interface EngineComputeResult {
	static EngineComputeResult SUCCESS = () -> EngineComputeResultStatus.SUCCESS;
	static EngineComputeResult FAILURE = () -> EngineComputeResultStatus.FAILURE;

	EngineComputeResultStatus getStatus();
	
	public static enum EngineComputeResultStatus {
		SUCCESS,
		FAILURE;
	}
}
