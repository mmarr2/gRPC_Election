import grpc
from concurrent import futures
import election_pb2
import election_pb2_grpc

class ElectionService(election_pb2_grpc.ElectionDataServiceServicer):
    def sendElectionData(self, request, context):
        print("Received election data:")
        print(f"Region ID: {request.regionID}")
        print(f"Region Name: {request.regionName}")
        print(f"Region Address: {request.regionAddress}")
        print(f"Region Postal Code: {request.regionPostalCode}")
        print(f"Federal State: {request.federalState}")
        print(f"Votes: ÖVP={request.oevpVotes}, SPÖ={request.spoeVotes}, FPÖ={request.fpoeVotes}, Grüne={request.grueneVotes}, NEOS={request.neosVotes}")
        print(f"Timestamp: {request.timetstamp}")

        return election_pb2.ElectionResponse(status="Success", message="Election data received successfully.")

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    election_pb2_grpc.add_ElectionDataServiceServicer_to_server(ElectionService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server started on port 50051")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
