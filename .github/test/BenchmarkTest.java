 /*
 What Data Store you want to use for the benchmark is up to you
2. Using any or all of the techniques we've covered, find at least one CPU-based
performance bottleneck in your compute or coordinator components
3. Fix/improve the bottleneck(s) from (2) to improve performance by at least 10%
4. Document the results in either the README or an additional text document in the
repo
a. Include before and after benchmark numbers
b. Include a link to the benchmark test in your repo
c. Document what the issue and fix were at a high level (the level of detail
you might put in a commit description). Feel free to include links to the
specific PR for the fix alongside the description*/

import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

 public class BenchmarkTest {

    /* BASELINE BENCHMARK DATA
     *   Threads: 89, 82
     *   CPU Usage: 2.8%, 3.0%, 2.2%
     */

     /* IMPROVED BENCHMARK DATA
      *   Threads: 86, 85
      *   CPU Usage: 1.4%, 1.6%, 1.8%
      */
    @Test
    public void benchmarkTest() {

        DataAPIImpl dataAPI = Mockito.mock(DataAPIImpl.class);

        ComputeRequestImplTest request = Mockito.mock(ComputeRequestImplTest.class);
        EngineCompute engineAPI = Mockito.mock(EngineCompute.class);

        ComputeAPI computeAPI = new EngineManager(dataAPI, engineAPI);
        ComputeResult result =  computeAPI.compute(request);
        Assert.assertEquals(result.getStatus(), ComputeResult.ComputeResultStatus.SUCCESS);

    }
}
