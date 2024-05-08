import io.grpc.*;

import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

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
        UserProto.Page response;

            response = blockingStub.read(inputConfig);

        return response.getResultsList();//add page to interable list logic
    }

    @Override
    public DataWriteResult appendSingleResult(OutputConfig output, String result, char delimiter) {

        //TODO: Convert Parameters into "UserProto." Versions and call appendSingleResult with blockingStub
        UserProto.OutputConfig outputConfig = UserProto.OutputConfig.newBuilder().setFileName((OutputConfig)output).getFileName()).build();

        UserProto.DataWriteResult protoResult = blockingStub.appendSingleResult(outputConfig);
        //Above returns UserProto.DataWriteResult and needs conversion
        //TODO: Return type will be DataWriteResult
        return ;
    }
}
