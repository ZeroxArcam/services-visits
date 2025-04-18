package com.pragma.hogar360.servicesvisits.domain.utils.constants;

import java.util.Arrays;
import java.util.List;

public class DomainConstants {
    private DomainConstants() {}

    public static int WEEKS_NUMBER = 3;
    public static int MIN_ID_NUMBER = 0;
    public static int MAX_ID_NUMBER = 10000;

    public static int MAX_PAGE_SIZE = 100;
    public static int MIN_PAGE_SIZE = 1;
    public static int MIN_PAGE_NUMBER = 0;
    public static List<String> VALID_SORT_BY_VALUES = Arrays.asList("startTime", "endTime");
    public static String SORT_ASC="ASC";
    public static String SORT_DESC="DESC";
}
