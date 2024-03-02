public class TestDataStore implements DataAPI {

	@Override
	public Iterable<Integer> read(InputConfig input) {
		// Test code is allowed to assume it's getting the right types; this will fail with a ClassCastException if it gets
		// another type of input. For production code, we'd want some better user input validation
		return ((InputConfigTestImpl)input).getInputs();
	}

	@Override
	public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {
		// Test code is allowed to assume it's getting the right types; this will fail with a ClassCastException if it gets
		// another type of input. For production code, we'd want some better user input validation
		((OutputConfigTestImpl)output).getOutputMutable().add(result);
		return () -> DataWriteResult.WriteResultStatus.SUCCESS;
	}

}