package ru.clevertec.ecl.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Port {

    private static final Integer PORT_NOD_FIRST = 9001;
    private static final Integer PORT_NOD_SECOND = 9002;
    private static final Integer PORT_NOD_THIRD = 9003;

    public static List<Integer> getPorts() {
        return Arrays.asList(PORT_NOD_THIRD, PORT_NOD_FIRST, PORT_NOD_SECOND);
    }

    public static Integer getPort(Integer seq){
        int index = seq % 3;
        return getPorts().get(index);
    }

}
