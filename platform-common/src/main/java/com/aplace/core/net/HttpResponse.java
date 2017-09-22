package com.aplace.core.net;

import java.util.HashMap;
import java.util.List;
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

    public HttpResponse setHeaderOrgin(Map<String, List<String>> headerOrgin) {
        for (Map.Entry<String,List<String>> entry : headerOrgin.entrySet()) {
            if (null != entry.getValue() && entry.getValue().size() == 1) {
                if (null == this.header)
                    this.header = new HashMap<String,String>();
                this.header.put(entry.getKey(),entry.getValue().get(0));
            } else {
                if (null == this.header)
                    this.header = new HashMap<String,String>();
                this.header.put(entry.getKey(),entry.getValue().toString());
            }
        }
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
