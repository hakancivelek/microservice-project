package com.hakancivelek.order.user;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Component
public class UserClientFacade {

    private final UserClientService userClientService;

    public UserClientFacade() {
        RestClient restClient = RestClient.builder().baseUrl("http://user-service:8080/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        userClientService = factory.createClient(UserClientService.class);
    }

    @CircuitBreaker(name = "get-user-CB", fallbackMethod = "fallbackRuntimeException")
    public UserResponse getUser(Long userId) {
        simulateFailure();
        return userClientService.getUser(userId);
    }

    public List<UserResponse> getAllUsers() {
        return userClientService.getAllUsers();
    }

    private void simulateFailure() {
        if (Math.random() > 0.7) {
            System.out.println("Simulating service failure");
            throw new RuntimeException("Service unavailable");
        }
    }

    private UserResponse fallbackRuntimeException(Long userId, RuntimeException runtimeException) {
        System.out.println("UserResponse Circuit breaker fallback activated");
        System.out.println("UserResponse Fallback RuntimeException :" + runtimeException);
        return new UserResponse("0", "", "", "");
    }

    private UserResponse fallbackRuntimeException(Long userId, Throwable throwable) {
        System.out.println("UserResponse Circuit breaker fallback activated");
        System.out.println("UserResponse Fallback Throwable :" + throwable);
        return new UserResponse("-1", "", "", "");
    }
}