
public interface EngineComputeRequest {
	static EngineComputeRequest SUCCESS = () -> EngineComputeRequestStatus.SUCCESS;
	static EngineComputeRequest FAILURE = () -> EngineComputeRequestStatus.FAILURE;

	EngineComputeRequestStatus getStatus();
	
	public static enum EngineComputeRequestStatus {
		SUCCESS,
		FAILURE;
	}
}
