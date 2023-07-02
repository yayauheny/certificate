package ru.clevertec.ecl.healthcheck;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.inercetor.ClusterProperties;
import ru.clevertec.ecl.util.Constant;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerHealthCheck {

    private final HealthCheck healthCheck;

    private final ClusterProperties clusterProperties;


    @Scheduled(cron = Constant.CRON)
    private void logHealthCheck() {
        clusterProperties.getValues()
                .forEach(host -> {
                    HttpStatus httpStatus = healthCheck.checkNode(host);
                    log.info("Node: {}, status: {}", host, httpStatus);
                });
    }

}
