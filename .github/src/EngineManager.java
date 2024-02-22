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

    public EngineManager(DataStorage dataStorage, EngineCompute engineCompute ) {

        dataStoreAPI =  dataStorage;
        computeEngineAPI = engineCompute;

    }

    /*Receive requests from the user to start the computation (the
    * method signature for this will come from your API), and return a
    * suitable result status back to the user
    */
    @Override
    public ComputeResult compute(ComputeRequest request) {
        
        //request DataAPI object to read ints
        request = new ComputeRequestTestImpl();
        InputConfig inputConfig = request.getInputConfig();
        DataReadResult readRequest = dataStoreAPI.read(inputConfig);

        if(readRequest.equals(DataReadResult.SUCCESS))
        {
            
            
            //EngineComputeRequest computeRequest = new EngineComputeRequestImpl(list);

            //pass the ints to the EngineAPI object
            EngineComputeResult computeResult = computeEngineAPI.compute(list); //THIS is where I am stuck on what to do next

                if(computeResult.equals(EngineComputeResult.SUCCESS))
                {
                    request.

                    //request DataAPI object to write the output
                    DataWriteResult writeResult = dataStoreAPI.writeSingleResult(request.getOutputConfig(), computeResult);

                    if(writeResult.equals(DataWriteResult.SUCCESS)) {
                        return ComputeResult.SUCCESS;
                    }
                }

        }

        //send back failed ComputeResult 
        return ComputeResult.FAILURE;

    }
    
}
