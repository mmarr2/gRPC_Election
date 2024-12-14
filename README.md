# Questions

What is gRPC and why does it work accross languages and platforms?

gRPC stands for (google) Remote Procedure Calls and is an open-source framework that enables communication between servers and clients. 

It works across languages and platforms because it uses protocol buffers - a serialization format. 

Describe the RPC life cycle starting with the RPC client

1. Client calls a stub method → server is notified that RPC has been invoked with the meta data of the client, the method name and optionally a deadline
2. Server can either (application specific):
    1. Send back its own initial metadata
    2. wait for the client’s request messages 
3. Does whatever is necessary to create a response
4. Response is returned to the client with status details
5. If the response status is OK, the client receives the response → call on client side is completed

Describe the workflow of Protocol Buffers

- define data structures in a `.proto`  file
- compile `.proto` file
- implement service
- create client and server

What are the benefits of using protocol buffers?

- Compact and fast
- Cross-platform
- Human readable
- Strongly typed

When is the use of protocol not recommended?

- If human readability is a requirement
- Simple data exchanges
- High Compatibility Requirements

List 3 different data types that can be used with protocol buffers

- string
- bool
- int32

# Hello World Application

**Using this tutorial:** https://intuting.medium.com/implement-grpc-service-using-java-gradle-7a54258b60b8

## Gradle

Added these lines to the `gradle.build` file: 

```
plugins {
    id 'java'
    id 'com.google.protobuf' version '0.9.4'

}

group = 'org.example'
version = '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    implementation 'io.grpc:grpc-netty:1.57.2'
    implementation 'io.grpc:grpc-protobuf:1.57.2'
    implementation 'io.grpc:grpc-stub:1.57.2'
    implementation 'javax.annotation:javax.annotation-api:1.3.2'
}

test {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = 'com.google.protobuf:protoc:3.24.3'
    }
    plugins {
        grpc {
            artifact = 'io.grpc:protoc-gen-grpc-java:1.57.2'
        }
    }
    generateProtoTasks {
        all()*.plugins {
            grpc {}
        }
    }
}

sourceSets {
    src {
        main {
            java {
                srcDirs 'build/generated/source/proto/main/grpc'
                srcDirs 'build/generated/source/proto/main/java'
            }
        }
    }
}
```

## Proto File

Add a `proto` file in the `src/main/proto` directory. 

```protobuf
syntax = "proto3";

service HelloWorldService {
  rpc hello(HelloRequest) returns (HelloResponse) {}
}

message HelloRequest {
  string text = 1;

}

message HelloResponse {
  string text = 1;
}
```

Then run `./gradlew build` to generate classes. They can be found in `build/generated/source/proto/main` 

## Server and Client.

Using the `HelloWorldServiceImplBase` class that was generated, you can make a `HelloWorldServiceImpl`

class. The method `hello()`  needs to be overridden. The next step is to to add a `HelloWorldServer` to run the gRPC service. 

## Problems

I had the whole project in a OneDrive environment, which caused a few issues with Gradle. To resolve this problem, I simply moved the project to a directory, that isn’t backed up by OneDrive. 

# ElectionData

To transfer election data through gRPC, a few parts of the program need to be modified. The `.proto` file has to be changed, to allow more variables. Additionally, the Client and Server classes need to be adjusted, so they can handle the data. 

The `.proto` file now looks like this: 

```protobuf
syntax = "proto3";

service ElectionDataService {
  rpc sendElectionData(ElectionData) returns (ElectionResponse){}
}

message ElectionData {
  string regionID = 1;
  string regionName = 2;
  string regionAddress = 3;
  string regionPostalCode = 4;
  string federalState = 5;
  int32 oevpVotes = 6;
  int32 spoeVotes = 7;
  int32  fpoeVotes = 8;
  int32 grueneVotes = 9;
  int32 neosVotes = 10;
  string timetstamp = 11;

}

message ElectionResponse {
  string status = 1;
  string message = 2;
}
```

# EK

As my second language I want to use Python. First, I need to install the necessary tools: 

```powershell
pip install grpcio grpcio-tools
```

Then the `.proto` file needs to be defined. I will be using the same one as before. 

Then run this command in the same directory as the `.proto` file. 

```powershell
python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. election.proto
```

Then define a server and a client in two different files. To execute them. , run `python server.py` in one terminal and `python client.py`  in the other.
