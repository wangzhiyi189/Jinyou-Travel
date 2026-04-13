package com.jinyou.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

public class CaseUtil {
    public static String getHeaderIgnoreCase(HttpServletRequest request, String headerName) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (name.equalsIgnoreCase(headerName)) {
                return request.getHeader(name);
            }
        }
        return null;
    }
}
