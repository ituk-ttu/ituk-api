package ee.ituk.api.common;

import java.util.UUID;

public class GlobalUtil {

    private GlobalUtil() {

    }

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
