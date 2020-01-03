package cu.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtils {
    private static SimpleDateFormat sdf;
    static {
        sdf = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static String getHttpFormatDate(Calendar calendar) {
        return sdf.format(calendar.getTime());
    }

    public static String getUriFromRequestUri(String requestUri) {
        if (requestUri.length() == 1 && requestUri.equals("/")) {
            return "";
        }
        int index = requestUri.indexOf('?');
        if (index == -1) {
            return requestUri.substring(1);
        }
        return requestUri.substring(1, index);
    }

    public static Map<String, String> getParamsFromRequestUri(String requestUri) {
        Map<String, String> params = new HashMap<>();
        int index = requestUri.indexOf('?');
        if (index == -1)
            return params;
        setPairStrings(requestUri.substring(index + 1), "&", "=", params);
        return params;
    }

    public static Map<String, String> getParamsFromRequestBody(String requestBody) {
        Map<String, String> params = new HashMap<>();
        setPairStrings(requestBody, "&", "=", params);
        return params;
    }

    public static void setPairString(String pairString, String delimiter, Map<String, String> map) {
        String[] pair = pairString.split(delimiter);
        map.put(pair[0], pair[1]);
    }

    public static void setPairStrings(String pairStrings, String delimiter, String pairDelimiter, Map<String, String> map) {
        String[] stringPairs = pairStrings.split(delimiter);
        for (String stringPair : stringPairs) {
            setPairString(stringPair, pairDelimiter, map);
        }
    }

}
