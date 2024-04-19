package com.ruoyi.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Map;

public class PaymentUtil {

    public static String sign(Map<String, Object> nvps, String priKey) {
        nvps.remove("sign");
        Object[] key = nvps.keySet().toArray();
        StringBuilder buf = new StringBuilder();
        String signatureStr = "";
        for (int i = 0; i < key.length; i++) {
            if (nvps.get(key[i]) == null || "".equals(nvps.get(key[i]))) {
                continue;
            }
            buf.append(key[i]).append("=").append(nvps.get(key[i]).toString()).append("&");
        }
        signatureStr = buf.substring(0, buf.length() - 1) + priKey;
        return DigestUtils.md5Hex(signatureStr);
    }

    public static String sortSign(Map<String, ? extends Object> nvps, String priKey) {
        nvps.remove("sign");
        Object[] key = nvps.keySet().toArray();
        Arrays.sort(key);
        StringBuilder buf = new StringBuilder();
        String signatureStr = "";
        for (int i = 0; i < key.length; i++) {
            if (nvps.get(key[i]) == null || "".equals(nvps.get(key[i]))) {
                continue;
            }
            buf.append(key[i]).append("=").append(nvps.get(key[i]).toString()).append("&");
        }
        signatureStr = buf.append("key=").append(priKey).toString();
        return StringUtils.upperCase(DigestUtils.md5Hex(signatureStr));
    }

    public static String sortSign(Map<String, Object> nvps) {
        nvps.remove("sign");
        Object[] key = nvps.keySet().toArray();
        Arrays.sort(key);
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < key.length; i++) {
            if (nvps.get(key[i]) == null || "".equals(nvps.get(key[i]))) {
                continue;
            }
            buf.append(key[i]).append("=").append(nvps.get(key[i]).toString()).append("&");
        }
        String resut = buf.toString().substring(0, buf.length() - 1);
        return DigestUtils.md5Hex(resut);
    }
}
