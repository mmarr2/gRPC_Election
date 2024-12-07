import io.grpc.stub.StreamObserver;

public class HelloWorldServiceImpl extends
        HelloWorldServiceGrpc.HelloWorldServiceImplBase{

    @Override
    public void hello(
            Hello.HelloRequest request,
            StreamObserver<Hello.HelloResponse> responseObserver) {
        System.out.println(
                "Handling hello endpoint: " + request.toString());


        String text = "Hello " + request.getText();
        Hello.HelloResponse response =
                Hello.HelloResponse.newBuilder()
                        .setText(text).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
