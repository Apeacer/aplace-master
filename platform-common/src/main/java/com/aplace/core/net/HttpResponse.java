package com.aplace.core.net;

import java.util.Map;

/**
 * @author apeace
 * @version 2017/6/10.
 */
public class HttpResponse {

    private String line;
    private Map<String,String> header;
    private String body;


    public String getLine() {
        return line;
    }

    public HttpResponse setLine(String line) {
        this.line = line;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HttpResponse setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        return this;
    }
}
