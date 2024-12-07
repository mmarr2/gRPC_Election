public class ElectionDataServiceImpl extends ElectionDataServiceGrpc.ElectionDataServiceImplBase {
    @Override
    public void sendElectionData(Election.ElectionData request,
                                 io.grpc.stub.StreamObserver<Election.ElectionResponse> responseObserver){
        String message = String.format(
                "Region ID: %s, Region Name: %s, Region Address: %s, Region Postal Code: %s, Federal State: %s, Timestamp: %s OEVP Votes: %d, SPOE Votes: %d, FPOE Votes: %d, Gruene Votes: %d, NEOS Votes: %d",
                request.getRegionID(),
                request.getRegionName(),
                request.getRegionAddress(),
                request.getRegionPostalCode(),
                request.getFederalState(),
                request.getTimetstamp(),
                request.getOevpVotes(),
                request.getSpoeVotes(),
                request.getFpoeVotes(),
                request.getGrueneVotes(),
                request.getNeosVotes()
        );


        Election.ElectionResponse response = Election.ElectionResponse.newBuilder().setMessage(message).setStatus("Succesful").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
