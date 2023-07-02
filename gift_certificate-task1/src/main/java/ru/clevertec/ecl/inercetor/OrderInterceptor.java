package ru.clevertec.ecl.inercetor;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.HandlerMapping;
import reactor.core.publisher.Mono;
import ru.clevertec.ecl.dto.ErrorDto;
import ru.clevertec.ecl.dto.OrderReadDto;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.healthcheck.HealthCheck;
import ru.clevertec.ecl.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class OrderInterceptor implements HandlerInterceptor {

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

        if (HttpMethod.GET.name().equals(method)) {
            Map<String, String> attribute = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String id = attribute.get(Constant.FIELD_NAME_ID);

            if (id == null) {
                String size = request.getParameter(Constant.SIZE);
                String page = request.getParameter(Constant.PAGE);
                String sort = request.getParameter(Constant.SORT);

                List<OrderReadDto> orders = doFindAll(request, PageResponse.of(size, page, sort));
                getResp(orders, response, HttpStatus.OK);
            } else {
                ResponseEntity<OrderReadDto> resp = doFindById(request, Integer.parseInt(id));
                getResp(resp.getBody(), response, HttpStatus.OK);
            }
        }

        if (HttpMethod.POST.name().equals(method)) {
            Integer seq = getSeq();
            List<String> hosts = clusterProperties.getHostsShard(seq + 1);
            setSeq(hosts, seq);

            ResponseEntity<OrderReadDto> resp = doPost(requestWrapper, hosts);
            getResp(resp.getBody(), response, HttpStatus.CREATED);
        }

        return false;
    }

    private List<OrderReadDto> doFindAll(HttpServletRequest request, PageResponse pageResponse) {
        return clusterProperties.getHosts().stream()
                .map(host -> webClient
                        .get()
                        .uri(host + Constant.PORT + request.getRequestURI())
                        .retrieve()
                        .bodyToMono(OrderReadDto[].class)
                        .block())
                .flatMap(Stream::of)
                .sorted(pageResponse.getComparing())
                .skip(pageResponse.getPage())
                .limit(pageResponse.getSize())
                .collect(toList());

    }

    private ResponseEntity<OrderReadDto> doFindById(HttpServletRequest request, Integer id) {
        return webClient
                .get()
                .uri(clusterProperties.getHost(id) + Constant.PORT + request.getRequestURI())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, resp ->
                        resp.bodyToMono(ErrorDto.class)
                                .flatMap(error ->
                                        Mono.error(new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE))))
                .toEntity(OrderReadDto.class)
                .block();
    }

    private ResponseEntity<OrderReadDto> doPost(CustomRequestWrapper request, List<String> hosts) {
        String body = request.getBody();
        return hosts.stream()
                .filter(healthCheck::isUpNode)
                .map(host -> CompletableFuture.supplyAsync(() ->
                        webClient.post()
                                .uri(host + Constant.PORT + request.getRequestURI())
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(body)
                                .retrieve()
                                .toEntity(OrderReadDto.class)
                                .block()))
                .map(CompletableFuture::join)
                .collect(toList())
                .get(Constant.FIRST_ELEMENT);
    }

    private void setSeq(List<String> hosts, Integer seq) {
        hosts.stream()
                .filter(healthCheck::isUpNode)
                .forEach(host -> CompletableFuture.runAsync(() ->
                        webClient.put()
                                .uri(host + Constant.PORT + Constant.URL_SEQ)
                                .bodyValue(seq)
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block()));
    }

    private Integer getSeq() throws Exception {
        return clusterProperties.getValues().stream()
                .filter(healthCheck::isUpNode)
                .map(host -> webClient.get()
                        .uri(host + Constant.PORT + Constant.URL_SEQ)
                        .retrieve()
                        .bodyToMono(Integer.class)
                        .block())
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .get();
    }

    private void getResp(Object dto, HttpServletResponse response, HttpStatus status) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            String json = new Gson().toJson(dto);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(status.value());
            writer.write(json);
        }
    }

}

