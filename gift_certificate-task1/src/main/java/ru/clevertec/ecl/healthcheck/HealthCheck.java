package ru.clevertec.ecl.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.clevertec.ecl.util.Constant;


@Component
@RequiredArgsConstructor
public class HealthCheck {

    private final WebClient webClient;

    public boolean isUpNode(String host) {
        return HttpStatus.OK.equals(checkNode(host));
    }

    public HttpStatus checkNode(String host) {
        return webClient.get()
                .uri(host + Constant.URL_HEALTH)
                .retrieve()
                .bodyToMono(Object.class)
                .map(status -> HttpStatus.OK)
                .onErrorResume(exception -> Mono.just(HttpStatus.SERVICE_UNAVAILABLE))
                .block();
    }

}
