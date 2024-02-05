public class ConceputualAPITest{

    public void test(CoordinateData component){

        //initialize data
        JobInitializeResponse initializeResponse = component.initializeJob(new InitializeRequest());

        //compute data
        if(initializeResponse.isValid())
        {
            ComputeResponse computeRequest = component.performComputation(null);

            //organize output
             if(computeRequest.isValid())
            {
                component.completeJob();
            }
        }

    
    }

    
}
