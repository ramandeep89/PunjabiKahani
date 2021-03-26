package org.raman;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PunjabiURLEncoder {
    public static final String encode(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        return url.substring(0, lastSlashIndex).concat("/").concat(URLEncoder.encode(url.substring(lastSlashIndex + 1), StandardCharsets.UTF_8));
    }
}
