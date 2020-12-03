package com.gabrielaigner.service;

import io.grpc.stub.StreamObserver;
import io.quarkus.example.GreeterGrpc;
import io.quarkus.example.HelloReply;
import io.quarkus.example.HelloRequest;
// Hint by Gabriel: If the imports where not found, try to reload maven. (pom.xml -> Reload Maven)

import javax.inject.Singleton;

/**
 * gRPC Service
 * More about it in the gRPC documentation:
 * https://grpc.io/docs/what-is-grpc/core-concepts/
 */
@Singleton
public class HelloService extends GreeterGrpc.GreeterImplBase {

    /**
     * sayHello - Override of the sayHello method defined in the .proto file.
     */
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName();
        String message = "Hello " + name;
        responseObserver.onNext(HelloReply.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();
    }
}