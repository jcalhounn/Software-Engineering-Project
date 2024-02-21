import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;

public class  ComputeAPITest {

    @Test
    public void computationTest() {
        ComputeRequest mockRequest = Mockito.mock(ComputeRequest.class);

         ComputeAPI mockInterface = Mockito.mock( ComputeAPI.class);
        when(mockInterface.compute(any(ComputeRequest.class))).thenReturn(ComputeResult.SUCCESS);

        //process compute request
        //User testUser = new User();

        //ComputeResult mockResult = testUser.compute(mockRequest);
        
        //check if valid
        //if(mockResult == mockResult.SUCCESS){
            //print results ??
        //}   
        //else{
            //error , try again 
       // }     

    }

}
