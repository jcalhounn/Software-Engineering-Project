public interface ProcessBoundary
{
    /*
     * ComputeEngine is Required to tell the data storage where the DataStorage is going to get the input data from
     * 
     * ComputeEngine is Required to tell the data storage where the DataStorage is going to output the computations to
     * 
     * ComputeEngine is Required to return its computations to the DataStorage class
     * 
     * DataStorage is Required to feed the ComputeEngine the data from the source
     * 
     * DataStorage is Required to feed the specified output location, the computations from ComputeEngine
     * 
     */
    

    public void setDataLocations(User address);

    //setDelimiters must have defaults
    public void setDelimiters();


    public User readUserData();


    public void writeUserData();
    

}