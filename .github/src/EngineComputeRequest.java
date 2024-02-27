// J- 2/22
// I believe that the EngineComputeRequest should be an interface that has the different computations
// that the EngineCompute should be able to compute
// EngineComputeResult already has the SUCCESS or FAILURE
// This interface should be formatted like the "ComputeRequest" interface. 

import java.util.List;

public interface EngineComputeRequest {

	public EngineComputeResult setDecInputs(List<Integer> data);
	public List<String> getHexOutput();
	public InputConfig getInputConfig();
	public OutputConfig getOutputConfig();
	
	//DecimalComputation findDecGCD();
	//HexidecimalComputation getHexGCD();
	/*
	static EngineComputeRequest SUCCESS = () -> EngineComputeRequestStatus.SUCCESS;
	static EngineComputeRequest FAILURE = () -> EngineComputeRequestStatus.FAILURE;

	EngineComputeRequestStatus getStatus();
	
	public static enum EngineComputeRequestStatus {
		SUCCESS,
		FAILURE;
	}
	*/
}
