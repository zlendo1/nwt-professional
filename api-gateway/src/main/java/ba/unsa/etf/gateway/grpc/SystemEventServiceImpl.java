package ba.unsa.etf.gateway.grpc;

import ba.unsa.etf.gateway.SystemEventServiceGrpc;
import ba.unsa.etf.gateway.SystemEventServiceOuterClass;
import ba.unsa.etf.gateway.grpc.model.SystemEvent;
import ba.unsa.etf.gateway.repository.SystemEventRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@GrpcService
@RequiredArgsConstructor
@Service
public class SystemEventServiceImpl extends SystemEventServiceGrpc.SystemEventServiceImplBase {
    private final SystemEventRepository systemEventRepository;

    public void logSystemEvent(SystemEventServiceOuterClass.SystemEvent request, StreamObserver<SystemEventServiceOuterClass.SystemEventResponse> responseObserver) {
        // Extract the data from the request
        String timestamp = request.getTimestamp();
        String microserviceName = request.getMicroserviceName();
        String user = request.getUser();
        String actionType = request.getActionType();
        String resourceName = request.getResourceName();
        String responseType = request.getResponseType();

        System.out.println("Received event: " + timestamp + " " + microserviceName + " " + user + " " + actionType + " " + resourceName + " " + responseType);

        var systemEvent = new SystemEvent();
        systemEvent.setTimestamp(timestamp);
        systemEvent.setMicroserviceName(microserviceName);
        systemEvent.setUser(user);
        systemEvent.setActionType(actionType);
        systemEvent.setResourceName(resourceName);
        systemEvent.setResponseType(responseType);

        System.out.println("Attempting to save event");
        SystemEvent savedEvent = systemEventRepository.save(systemEvent);
        System.out.println("Event saved: " + savedEvent);

        // Create a response
        SystemEventServiceOuterClass.SystemEventResponse response = SystemEventServiceOuterClass.SystemEventResponse.newBuilder()
                .setMessage("Event received and processed")
                .build();

        System.out.println("Event saved");

        // Send the response
        responseObserver.onNext(response);

        // Complete the RPC call
        responseObserver.onCompleted();
    }
}