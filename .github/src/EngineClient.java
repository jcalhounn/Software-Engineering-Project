import io.grpc.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
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
        UserProto.InputConfig inputConfig = UserProto.InputConfig.newBuilder().setFileName(((FileInputConfig) input).getFileName()).setDelimiter(((FileInputConfig) input).getDelimiter() + "").build();
        UserProto.List response = blockingStub.read(inputConfig);

        // Use the visitor pattern so that we can easily and safely add additional config types in the future
        return InputConfig.visitInputConfig(input, fileConfig -> {

            // Iterables are more convenient for method callers than iterators, so wrap our file-based iterator before returning
            return new Iterable<Integer>() {
                @Override
                public Iterator<Integer> iterator() {
                    return getFileBasedIterator(fileConfig.getFileName(), fileConfig.getDelimiter());
                }
            };
        });

    }

    private static Iterator<Integer> getFileBasedIterator(String fileName, char delimiter) {
        try {
            BufferedReader buff = new BufferedReader(new FileReader(fileName));

            return new Iterator<Integer>() {
                StringTokenizer tokenizer;
                String nextLine;

                private boolean prepareNextToken() throws IOException {
                    while ((tokenizer == null || !tokenizer.hasMoreTokens()) && (nextLine = buff.readLine()) != null) {
                        tokenizer = new StringTokenizer(nextLine, String.valueOf(delimiter));
                    }
                    return tokenizer != null && tokenizer.hasMoreTokens();
                }

                @Override
                public boolean hasNext() {
                    try {
                        return prepareNextToken();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public Integer next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Integer.parseInt(tokenizer.nextToken().trim());
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    private Iterator<Integer> getFileBasedIterator(String fileName, char delimiter) {
//        try {
//            BufferedReader buff = new BufferedReader(new FileReader(fileName));
//
//            return new Iterator<Integer>() {
//                String line;
//                StringTokenizer tokenizer;
//                int nextInt = -1;
//
//                private boolean readNextInt() throws IOException {
//                    while ((line = buff.readLine()) != null) {
//                        tokenizer = new StringTokenizer(line, String.valueOf(delimiter));
//                        if (tokenizer.hasMoreTokens()) {
//                            nextInt = Integer.parseInt(tokenizer.nextToken().trim());
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//
//                {
//                    readNextInt();
//                }
//
//                @Override
//                public boolean hasNext() {
//                    if (nextInt != -1) {
//                        return true;
//                    } else {
//                        try {
//                            return readNextInt();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//
//                @Override
//                public Integer next() {
//                    if (!hasNext()) {
//                        throw new NoSuchElementException();
//                    }
//                    int result = nextInt;
//                    nextInt = -1;
//                    return result;
//                }
//            };
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    private void writeToFile(String fileName, String line) {
        // use try-with-resources syntax to automatically close the file writer
        // use the append-friendly version of the constructor

        try (FileWriter writer = new FileWriter(new File(fileName), true)) {
            if(line.equals("\\n")){
                writer.write("\r\n");
                writer.append(line);
            }
            else {
                writer.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {
        OutputConfig.visitOutputConfig(output, config -> {
            if(delimiter=='\\'){
                writeToFile(config.getFileName(),result);
                writeToFile(config.getFileName(),'\n'+"");
            }
            else {
                writeToFile(config.getFileName(), result + delimiter);
            }
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