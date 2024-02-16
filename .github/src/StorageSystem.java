/* Must be able to handle the method calls that are layed out in the DataStore
 * Interface
 * 
 * 
 */



public class StorageSystem implements DataStore {

    @Override
    public DataStoreReadResult read(InputConfig input) {
        // Should return the data 
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public WriteResult writeSingleResult(OutputConfig output, String result) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'appendSingleResult'");
    }
    
}
