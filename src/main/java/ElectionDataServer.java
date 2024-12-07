import io.grpc.ServerBuilder;
import io.grpc.Server;
import java.io.IOException;

public class ElectionDataServer {
    private static final int PORT =  50052;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT).addService(new ElectionDataServiceImpl()).build().start();
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server == null)
            return;

        server.awaitTermination();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ElectionDataServer server = new ElectionDataServer();
        server.start();
        server.blockUntilShutdown();
    }
}
