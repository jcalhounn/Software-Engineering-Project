import java.io.File;


public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// User <-> ComputeEngine API; also update the parameter passed to the constructor
	private final ComputeAPI coordinator;

	public TestUser(ComputeAPI coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ',';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";

		ComputeRequest request = new ComputeRequestImplTest(inputPath, delimiter, outputPath);

		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter
		coordinator.compute(request);
	}

}
