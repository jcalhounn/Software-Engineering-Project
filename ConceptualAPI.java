/* Step 1: Identify the user
 *    the user is the component initializing and coordinating the data
 * 
 * Step 2: What the user needs to do
 *      initialize the job
 *      tell other component to compute data
 *      organize output
 *   
 * Step 3: Build a prototype
 * 
 */
public interface ConceptualAPI {

    JobInitializeResponse initializeJob(InitializeRequest request);
    ComputeResponse performComputation(Sources inputData);
    CompleteResponse completeJob();
}