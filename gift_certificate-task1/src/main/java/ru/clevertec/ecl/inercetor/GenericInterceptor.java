package ru.clevertec.ecl.inercetor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.util.Constant;
import ru.clevertec.ecl.util.Port;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class GenericInterceptor implements HandlerInterceptor {

    private final WebClient webClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        CustomRequestWrapper requestWrapper = (CustomRequestWrapper) request;

        if (Constant.LOCAL_NAME.equals(request.getLocalName())) {
            return true;
        }

        if (HttpMethod.DELETE.name().equals(method)) {
            doCrud(requestWrapper, Port.getPorts());
        }

        if (HttpMethod.PUT.name().equals(method)) {
            doCrud(requestWrapper, Port.getPorts());
        }

        if (HttpMethod.POST.name().equals(method)) {
            doCrud(requestWrapper, Port.getPorts());
        }

        if (HttpMethod.PATCH.name().equals(method)) {
            doCrud(requestWrapper, Port.getPorts());
        }

        return true;
    }

    private void doCrud(CustomRequestWrapper request, List<Integer> ports) {
        String body = request.getBody();
        ports.stream()
                .filter(port -> port != request.getLocalPort())
                .forEach(port -> CompletableFuture.runAsync(() ->
                        webClient
                                .method(HttpMethod.valueOf(request.getMethod()))
                                .uri(Constant.URL_BASE + port + request.getRequestURI())
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(body)
                                .retrieve()
                                .toEntity(Object.class)
                                .block()));
    }

}

