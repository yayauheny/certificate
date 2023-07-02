package ru.clevertec.ecl.inercetor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.healthcheck.HealthCheck;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class GenericInterceptor implements HandlerInterceptor {

    private final WebClient webClient;

    private final HealthCheck healthCheck;

    private final ClusterProperties clusterProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        CustomRequestWrapper requestWrapper = (CustomRequestWrapper) request;

        if (Constant.PORT == request.getServerPort()) {
            return true;
        }

        if (HttpMethod.DELETE.name().equals(method)) {
            doCrud(requestWrapper, clusterProperties.getValues());
        }

        if (HttpMethod.PUT.name().equals(method)) {
            doCrud(requestWrapper, clusterProperties.getValues());
        }

        if (HttpMethod.POST.name().equals(method)) {
            doCrud(requestWrapper, clusterProperties.getValues());
        }

        if (HttpMethod.PATCH.name().equals(method)) {
            doCrud(requestWrapper, clusterProperties.getValues());
        }

        return true;
    }

    private void doCrud(CustomRequestWrapper request, List<String> ports) {
        String body = request.getBody();
        ports.stream()
                .filter(healthCheck::isUpNode)
                .filter(host -> !host.contains(clusterProperties.getNumberHost(request.getServerPort())))
                .forEach(host -> CompletableFuture.runAsync(() ->
                        webClient
                                .method(HttpMethod.valueOf(request.getMethod()))
                                .uri(host + Constant.PORT + request.getRequestURI())
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(body)
                                .retrieve()
                                .toEntity(Object.class)
                                .block()));
    }

}

