/* Step 1: Identify the user
 *    User Class, where we are intaking the input and returning the output
 * 
 * Step 2: What the user needs to do
 *      Specify source for the input
 *      Specify delimiter characters for the output
 *      Specify destination for the output
 * 
 * Step 3: Build a prototype
 * 
 */

 public interface NetworkInterface{
    InputResponse inputSource(InputSourceRequest request);
    DelimiterResponse setDelimeter(DelimiterRequest delimiter);
    OutputResponse outputSource(OutputRequest output);
 }

