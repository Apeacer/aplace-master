package com.aplace.core.net;


import java.util.Map;

/**
 * The bean for Http request
 * @author apeace
 * @version 2017/7/13.
 */
public class HttpRequest {

    /**
     * Default time out 30s
     */
    private static int DEFAULT_TIME_OUT = 30000;

    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONNECTION_KEY = "connection";// default "Keep-Alive"
    public static final String ACCEPT_KEY = "accept"; // default "*/*"
    public static final String USER_AGENT_KEY = "user-agent"; // default "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"
    public static final String COOKIE_KEY = "Cookie"; // default nothing thing
//    public static final String CONTENT_TYPE_KEY = "Content-Type";

    private String line;
    private Map<String,String> header;
    private String body;


    public String getLine() {
        return line;
    }

    public HttpRequest setLine(String line) {
        this.line = line;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HttpRequest setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public HttpResponse sendRequest(){

//        StringUtils.isBlank("aaa");

        return null;
    }
}
