/* Step 1: Identify the user
 *    Compute Engine Class
 * 
 * Step 2: What the user needs to do
 *      process the input and output sources
 * Step 3: Build a prototype
 * 
 */

public interface ProcessInterface
{
    public ProcessResponse processData(Sources input, Sources output);
    
}