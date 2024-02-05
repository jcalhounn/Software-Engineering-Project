public class ProcessTest {

    public void processTest(ComputeEngine engine)
    {
        //check input and output source 
        if(engine.processData(null, null).isValid()) //(null for now, until Sources are filled in)
        {
            engine.compute();
        }
    }
    
}
