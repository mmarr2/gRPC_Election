import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.JsonUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class ElectionDataClient {

    public static int generateVotes(){
        return new Random().nextInt(500) + 100;
    }
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();

        ElectionDataServiceGrpc.ElectionDataServiceBlockingStub stub = ElectionDataServiceGrpc.newBlockingStub(channel);
        Instant currentTimestamp = Instant.now();
        String timestamp = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(currentTimestamp);
        Election.ElectionResponse electionResponse = stub.sendElectionData(
                Election.ElectionData.newBuilder()
                        .setRegionID("12345")
                        .setRegionName("Linz Bahnhof")
                        .setRegionAddress("Bahnhofsstraße 27/9")
                        .setRegionPostalCode("Linz")
                        .setFederalState("Austria")
                        .setTimetstamp(timestamp)
                        .setOevpVotes(generateVotes())
                        .setSpoeVotes(generateVotes())
                        .setFpoeVotes(generateVotes())
                        .setGrueneVotes(generateVotes())
                        .setNeosVotes(generateVotes())
                        .build());


        System.out.println(electionResponse.getMessage());
        System.out.println(electionResponse.getStatus());
        channel.shutdown();
    }


}
