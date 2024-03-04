/*
 * Assignment 4 instructions: 
 * In the 'src' folder, implement the coordination component of the engine.
 * This piece will do a couple of things, although exactly how it does these is
 * up to you!
 *      a.Receive requests from the user to start the computation (the
 *        method signature for this will come from your API), and return a
 *        suitable result status back to the user
 * 
 *      b. Request that the data storage component read integers from the
 *      location specified by the user (remember, the number of integers
 *      involved is potentially very large!)
 * 
 *      c. Pass the integers to the compute component
 * 
 *      d. Request that the data storage component write the results to the output
 */

import java.util.List;

public class EngineManager implements ComputeAPI {

    DataAPIImpl ds;
	EngineAPI ec;
	
	public EngineManager(DataAPIImpl ds, EngineAPI ec) {
		this.ds = ds;
		this.ec = ec;
	}

	@Override
	public ComputeResult compute(ComputeRequestImplTest request) {
		Iterable<Integer> integers = ds.read(request.getInputConfig());
		
		//compute GCD
		int gcd=1;// = ec.getGCD((List<Integer>) integers,getInputs());

		for (int val : integers) {
			ds.appendSingleResult(request.getOutputConfig(), ec.compute(val), request.getDelimeter());
		}

		//append GCD last 
		ds.appendSingleResult(request.getOutputConfig(), ec.compute(gcd), request.getDelimeter());

		return ComputeResult.SUCCESS;
	}

}
//done
