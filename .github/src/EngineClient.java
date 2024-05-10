import io.grpc.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

import javax.xml.crypto.Data;

public class EngineClient implements DataAPI{

    //Client creating ComputeRequest
 // Boilerplate TODO: change to <servicename>Client
    private final DataAPIGrpc.DataAPIBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public EngineClient(Channel channel) {
        blockingStub = DataAPIGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    //        //TODO: Replace these variables with the proper ones needed to talk to DataServer
    //        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName("test.txt").build();
    //        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName("test.txt").build();
    //        UserProto.Page response;

    @Override
    public Iterable<Integer> read(InputConfig input) {
        //TODO: User provided list will not work for this "(FileInputConfig)input).getFileName())" logic
        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName(((FileInputConfig)input).getFileName()).build();
        UserProto.Page response = UserProto.Page.newBuilder().build();
        FileInputConfig fileInputConfig = new FileInputConfig(inputConfig.getFileName());



        return InputConfig.visitInputConfig(input, fileConfig -> {

            // Iterables are more convenient for method callers than iterators, so wrap our file-based iterator before returning
            return new Iterable<Integer>() {
                @Override
                public Iterator<Integer> iterator() {
                    return getFileBasedIterator(fileInputConfig.getFileName());
                }
            };
        });

    }


    public Iterator<Integer> getFileBasedIterator(String fileName) {
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

//        Iterable<Integer> list = read(fileInputConfig);
//        UserProto.Page page = UserProto.Page.newBuilder().build();
//        for(int i:list) {
//            page.getResults(i);
//        }

//
//        response = blockingStub.read(inputConfig);
//
//        return response.getResultsList();//add page to iterable list logic


    @Override
    public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {

        //TODO: Convert Parameters into "UserProto." Versions and call appendSingleResult with blockingStub
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName(((FileOutputConfig)output).getFileName()).build();

        UserProto.DataWriteResult protoResult = blockingStub.appendSingleResult(outputConfig);
        //Above returns UserProto.DataWriteResult and needs conversion

        //TODO: Return type will be DataWriteResult
        if(protoResult.getStatus().equals(UserProto.DataWriteResult.WriteResultStatus.SUCCESS)) {
            return DataWriteResult.SUCCESS;
        }
        else
            return DataWriteResult.FAILURE;
    }
}