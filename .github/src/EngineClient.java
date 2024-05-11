import io.grpc.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

import javax.xml.crypto.Data;

public class EngineClient implements DataAPI {

    //EXPLANATION 6
    //This client is meant to act like the class DataAPIImpl
    //The DataAPIImpl was first used in the "EngineServer" in the line,
    //EngineManager manager = new EngineManager(new DataAPIImpl(),new EngineCompute());
    //this version was working with the output file
    //now, the line is
    //EngineManager manager = new EngineManager(new EngineClient(channel),new EngineCompute());
    //this is why the EngineClient must behave like the DataAPIImpl
    //I believe the method appendSingleResult currently works but may have errors so double check and I am struggling on the read method
    //Reference emails with the Professor.
    //My current interpretation is that the Engine client needs to be able to determine what the current page number
    //is and then read out the next 100 numbers on that page or until null on that page and return those numbers.
    //I am struggling to figure out the "1 page at a time" reading of the file and understanding where the
    //returned Iterable<Integer> is going.
    //To test the program, Run the DataServer first, then the EngineServer, then finally the UserClient
    //

    private final DataAPIGrpc.DataAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public EngineClient(Channel channel) {
        blockingStub = DataAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    //        //TODO: Replace these variables with the proper ones needed to talk to DataServer
    //        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName("test.txt").build();
    //        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName("test.txt").build();
    //        UserProto.Page response;


    public Iterable<Integer> read(InputConfig input) {

        //TODO: If the User provided a list it will not work for this "(FileInputConfig)input).getFileName())" logic
        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName(((FileInputConfig) input).getFileName()).build();

        UserProto.List response = blockingStub.read(inputConfig);
        return response.getResultsList();

        /* Use the visitor pattern so that we can easily and safely add additional config types in the future
        return InputConfig.visitInputConfig(input, fileConfig -> {

            // Iterables are more convenient for method callers than iterators, so wrap our file-based iterator before returning
            return new Iterable<Integer>() {
                @Override
                public Iterator<Integer> iterator() {
                    return getFileBasedIterator(fileConfig.getFileName());
                }
            };
        });*/
    }


    private Iterator<Integer> getFileBasedIterator(String fileName) {
        try {
            return new Iterator<Integer>() {
                // A Scanner is also fine to use, but has an important difference as we add threads:
                // BufferedReader is by default thread-safe, but Scanner is not (like a synchronizedList vs ArrayList)
                // BufferedReader is also slightly more efficient at large file reads (larger buffer than Scanner),
                // but by default breaks at newlines rather than all whitespace, and doesn't parse the
                // input as it goes
                BufferedReader buff = new BufferedReader(new FileReader(fileName));
                String line = buff.readLine(); // read the first line so that hasNext() correctly recognizes empty files as empty
                boolean closed = false;

                @Override
                public Integer next() {
                    // this particular iterator reads the first line during the (implicit) constructor, so line is already
                    // set up for the next integer
                    int result = Integer.parseInt(line);
                    try {
                        line = buff.readLine();
                        if (!hasNext()) {
                            buff.close();
                            closed = true;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return result;
                }

                @Override
                public boolean hasNext() {
                    return line != null;
                }

                /*
                 * finalize() is a method on Object, much like toString or equals. It's called when an object is garbage collected, and is used as
                 * a final cleanup step for resources. It's a bit fragile - it isn't guaranteed to always be called, or be called at any specific time -
                 * but unless a cleanup is particularly critical, it's generally sufficient as a back-stop against weird circumstances. In this case,
                 * we would at worst leak a read-lock file handle, which is NBD and certainly not worth architecting a larger solution around (honestly,
                 * finalize() might even be overkill in this situation).
                 */

            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String fileName, String line) {
        // use try-with-resources syntax to automatically close the file writer
        // use the append-friendly version of the constructor
        try (FileWriter writer = new FileWriter(new File(fileName), true)) {
            writer.append(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {
        OutputConfig.visitOutputConfig(output, config -> {
            writeToFile(config.getFileName(), result + delimiter);
        });

        /*
         * Using lambda syntax to create an instance of WriteResult. This is an alternative to the ComputeResult approach of providing
         * constants for success/failure.
         */
        return () -> DataWriteResult.WriteResultStatus.SUCCESS;
    }

    /*public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {


        //TODO: Convert Parameters into "UserProto." Versions and call appendSingleResult with blockingStub
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName(((FileOutputConfig)output).getFileName()).build();

        UserProto.DataWriteResult protoResult = blockingStub.appendSingleResult(outputConfig);
        //Above returns UserProto.DataWriteResult and needs conversion // DONE

        //TODO: Return type will be DataWriteResult
        if(protoResult.getStatus().equals(UserProto.DataWriteResult.WriteResultStatus.SUCCESS)) {
            return DataWriteResult.SUCCESS;
        }
        else
            return DataWriteResult.FAILURE;
    }*/

}