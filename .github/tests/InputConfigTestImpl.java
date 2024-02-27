import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InputConfigTestImpl implements InputConfig {

    private final List<Integer> inputs = new ArrayList<>();

    public InputConfigTestImpl(int... inputsArr) {
        for(int i = 0; i < inputsArr.length; i++) {
            inputs.add(inputsArr[i]);
        }
    }

    public InputConfigTestImpl(Collection<Integer> inputs) {
        this.inputs.addAll(inputs);
    }


    /*class isnt immutable(Immutable means not capable of or susceptible to change)
    * because we're returning the List of inputs.
    * to be immutable we would use an ImmutableList in the first Place(implement this in the
    * actual program(not test code)) 
    *
    */

    public void getInputConfig(){
        
    }


    public List<Integer> getInputs() {
        return inputs;
    }
    
    


}
