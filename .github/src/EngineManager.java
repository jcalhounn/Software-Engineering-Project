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

    DataAPI dataStoreAPI;
    EngineAPI computeEngineAPI;
    EngineComputeRequest engineRequest;
    /* j commented out 2/22 these instance variables only need to access the method calls in their
    respective interfaces to transfer arguments to correct files
    
    public EngineManager(DataStorage dataStorage, EngineCompute engineCompute ) {

        dataStoreAPI =  dataStorage;
        computeEngineAPI = engineCompute;

    }
    */


    /*Receive requests from the user to start the computation (the
    * method signature for this will come from your API), and return a
    * suitable result status back to the user
    */
    @Override
    public ComputeResult compute(ComputeRequest request) {
        
        //request DataAPI object to read ints
        request = new ComputeRequestTestImpl();
        //readRequest will either become a SUCCESS or FAILURE after instantiation
        // this will determine if the EngineManager can go ahead with the compute call
        DataReadResult readRequest = dataStoreAPI.read(request.getInputConfig());
        
           /* //EngineComputeRequest computeRequest = new EngineComputeRequestImpl(list);
            //pass the ints to the EngineAPI object
            /*EngineComputeResult computeResult = computeEngineAPI.compute(list); //THIS is where I am stuck on what to do next

                if(computeResult.equals(EngineComputeResult.SUCCESS))
                {
                    request.

                    //request DataAPI object to write the output
                    DataWriteResult writeResult = dataStoreAPI.writeSingleResult(request.getOutputConfig(), computeResult);

                    if(writeResult.equals(DataWriteResult.SUCCESS)) {
                        return ComputeResult.SUCCESS;
                    }
                }
                
            //DataStorage read method was a success!
            //Here, we know that the DataStorage can read the data. 
            //I believe that there should be another method inside of the DataAPI interface that DataStorage implements, 
            //that has a return type of List<Integer> or what ever we change it to in the future.
            //
            //Using this List<Integer> return type, we use our EngineAPI instance variable to call the method in EngineCompute, passing the arguments List<Integer>,
            //and from there, the EngineCompute will handle that data. 
            //
            //The EngineManager is just a middleman to pass the data into the EngineCompute constructor
            */
         
        //if the data read was a success, call the EngineCompute method "compute()"
        if(readRequest.equals(DataReadResult.SUCCESS)) {

            engineRequest.setDecInputs(dataStoreAPI.getData());
            //if(computeEngineAPI.compute(dataStoreAPI.getData()).equals(EngineComputeResult.SUCCESS)){
            if(computeEngineAPI.compute(engineRequest).equals(EngineComputeResult.SUCCESS)){
                // ^^ recieves an EngineComputeResult SUCCESS or FAILURE ^^
                request.getOutputConfig().setOutput(engineRequest.getHexOutput());
            return ComputeResult.SUCCESS;
            }
            else{//FAILURE ComputeResult bc EngineComputeRequest failed 
                return ComputeResult.FAILURE;
            }
        }
        else{
            //FAILURE ComputeResult bc readRequest failed
        return ComputeResult.FAILURE;
        }
    }
    
}
