import grpc
import election_pb2
import election_pb2_grpc
from datetime import datetime
import random

def randomVotes():
    return random.randint(100, 500)

def run():
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = election_pb2_grpc.ElectionDataServiceStub(channel)
        now = datetime.now()
        date_time = now.strftime("%m/%d/%Y, %H:%M:%S")

        request = election_pb2.ElectionData(
            regionID="12345",
            regionName="Linz Bahnhof",
            regionAddress="Bahnhofsstraße 27/9",
            regionPostalCode="4020",
            federalState="Austria",
            oevpVotes=randomVotes(),
            spoeVotes=randomVotes(),
            fpoeVotes=randomVotes(),
            grueneVotes=randomVotes(),
            neosVotes=randomVotes(),
            timetstamp=date_time
        )

        response = stub.sendElectionData(request)
        print(f"Status: {response.status}")
        print(f"Message: {response.message}")

if __name__ == '__main__':
    run()