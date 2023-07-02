package ru.clevertec.ecl.inercetor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.util.Constant;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "cluster")
public class ClusterProperties {

    private Map<Integer, List<String>> hosts;

    public String getNumberHost(Integer port) {
        return String.valueOf(port % Constant.NUMBER_HOST);
    }


    public List<String> getHosts() {
        return Arrays.asList(getHost(Constant.FIRST_KEY),
                getHost(Constant.SECOND_KEY), getHost(Constant.THIRD_KEY));
    }

    public String getHost(Integer id) {
        return getHostsShard(id).stream()
                .findFirst()
                .get();

    }

    public List<String> getHostsShard(Integer id) {
        Integer key = id % Constant.THIRD_KEY;
        return hosts.get(key);
    }

    public List<String> getValues() {
        return hosts.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}

