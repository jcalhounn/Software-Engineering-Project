
public class NetworkTest{
    
    public void networkTest(NetworkInterface interface)
    {
        //set the source for the input
        InputResponse inputResponse = interface.inputSource(new InputSourceRequest());

        //set the delimiter characters
        if(inputResponse.valid())
        {
            DelimiterResponse delimiterResponse = interface.setDelimeter(new DelimiterRequest());
        }

        //set the destination for output
        if(delimiterResponse.valid())
        {
            OutputResponse outputResponse = interface.outputSource(new OuputRequest());
        }

    }
}