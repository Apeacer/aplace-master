package com.aplace.core.net;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The bean for Http request
 *
 * @author apeace
 * @version 2017/7/13.
 */
public class HttpRequest {

    private static Logger LOGGER = Logger.getLogger(HttpUtil.class);
    /**
     * Default time out 30s
     */
    private static int DEFAULT_TIME_OUT = 30000;

    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CHARSET_KEY = "charset";
    public static final String CONNECTION_KEY = "connection";// default "Keep-Alive"
    public static final String ACCEPT_KEY = "accept"; // default "*/*"
    public static final String USER_AGENT_KEY = "user-agent"; // default "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"
    public static final String COOKIE_KEY = "Cookie"; // default nothing thing


    //    private String line;
    private String url;
    private int timeOut = DEFAULT_TIME_OUT;
    private CharSetEnum charset = CharSetEnum.UTF8;

    private Map<String, String> header;
    private String body;

    public static HttpRequest getNesInstance() {
        return new HttpRequest();
    }


    /**
     * 设置url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url
     */
    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    /**
     * 设置链接超时时间
     *
     * @param timeOut
     */
    public HttpRequest setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    // HTTP BODY ---------------------------------------

    /**
     * 得到 Http 请求的 body
     *
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置 HTTP 请求的 body
     *
     * @param body
     * @return
     */
    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    // HTTP HEADER -----------------------------------------------

    /**
     * map 的方式获取Header
     *
     * @return
     */
    public Map<String, String> getHeader() {
        return header;
    }

    /**
     * map 的方式设置Header
     *
     * @param header
     * @return
     */
    public HttpRequest setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    /**
     * 设置contentType
     *
     * @param contentType http请求体的枚举值
     * @return
     */
    public HttpRequest setContentType(ContentTypeEnum contentType) {
        if (null == contentType)
            throw new NullPointerException();
        setHeandValue(CONTENT_TYPE_KEY, contentType.getName());
        return this;
    }

    /**
     * 设置charset
     *
     * @param charset http字符集的枚举值
     * @return
     */
    public HttpRequest setCharset(CharSetEnum charset) {
        if (null == charset)
            throw new NullPointerException();

        this.charset = charset;
        setHeandValue(CHARSET_KEY, charset.getName());
        return this;
    }

    /**
     * 设置contentType
     *
     * @param key   http head 属性的 key
     * @param value http head 属性的值
     * @return
     */
    public HttpRequest setHeandValue(String key, String value) {
        if (null == key || null == value)
            throw new NullPointerException();

        if (null == this.header)
            header = new HashMap<String, String>();
        header.put(key, value);
        return this;
    }

    /**
     * JDK原生post请求
     *
     * @return 响应体string
     */
    public HttpResponse sendPost() throws IOException {

        // 设置通用的请求头属性,设置必要的默认的属性
        Map<String, String> requestPropertys = buildRequestPropertys(this.header);

        // Content Type
        if (!requestPropertys.containsKey(CONTENT_TYPE_KEY)) {
            requestPropertys.put(CONTENT_TYPE_KEY,
                    ContentTypeEnum.FORM_URLENCODE.getName()); // default form
        }
        // 合并contentType 与 charSet
        String contentType = requestPropertys.get(CONTENT_TYPE_KEY);
        String charset;
        if (!requestPropertys.containsKey(CHARSET_KEY)) {
            charset = this.charset.getName();// default utf-8
        } else {
            charset = requestPropertys.remove(CHARSET_KEY);
        }
        contentType = String.format("%s;charset=%s", contentType, charset);
        requestPropertys.put(CONTENT_TYPE_KEY, contentType);


        URL connUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = connUrl.openConnection();
//        conn.setRequestProperty("Content-Type", String.format("%s;charset=%s", contentType.getName(), charSet.getName()));
//        conn.setRequestProperty("accept", "*/*");
//        conn.setRequestProperty("connection", "Keep-Alive");
//        conn.setRequestProperty("user-agent",
//                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return sendRequest(conn, requestPropertys);
    }


    /**
     * 发送GET请求
     *
     * @return 远程响应结果response Body
     */
    public HttpResponse sendGet() throws IOException {
        return sendGet(null);
    }

    /**
     * 发送GET请求
     * @param params 入参
     * @return
     * @throws IOException
     */
    public HttpResponse sendGet(Map<String, String> params) throws IOException {

        String fullUrl = this.url;

        String paramStr = buildFormParam(params); // build form param;
        if (paramStr.length() > 0) {
            if (this.url.indexOf('?') != -1) { // url 已经有了一些参数
                fullUrl = url + '&' + paramStr;
            } else {
                fullUrl = url + '?' + paramStr;
            }
        }

        // 设置通用属性
        Map<String, String> requestPropertys = buildRequestPropertys(this.header);

        URLConnection conn = null;

        LOGGER.debug(fullUrl);
        // 创建URL对象
        URL connURL = new URL(fullUrl);
        // 打开URL连接
        conn = connURL.openConnection();

        return sendRequest(conn, requestPropertys);
    }


    private HttpResponse sendRequest(URLConnection conn, Map<String, String> requestPropertys) throws IOException {
        HttpResponse httpResponse;
        // 保存,构建结果
        StringBuilder stringBuilder = null;
        // 发送请求的载体
        OutputStreamWriter outputStreamWriter;
        PrintWriter printWriter = null;
        // 读入响应的载体
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader = null;// 读取响应输入流

        try {

            for (Map.Entry<String, String> entry : requestPropertys.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);
            // 建立实际的连接
            conn.connect();

            // 发送请求body
            if (body != null) {
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), this.charset.getName());
                printWriter = new PrintWriter(outputStreamWriter, true);
                printWriter.print(body);
                // flush输出流的缓冲
                printWriter.flush();
            }

            // 构建响应，获得响应头
            httpResponse = new HttpResponse();
            httpResponse.setHeaderOrgin(conn.getHeaderFields());

            // 定义BufferedReader输入流来读取URL的响应
            inputStreamReader = new InputStreamReader(conn.getInputStream(), this.charset.getName());
            bufferedReader = new BufferedReader(inputStreamReader);

            stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) { // 持续读入响应body
                stringBuilder.append(line);
            }

        } catch (IOException ioException) {
            throw ioException;

        } finally { //使用finally块来关闭输出流、输入流
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception exception) {
                throw exception;
            }
        }

        if (null != httpResponse && null != stringBuilder) {
            httpResponse.setBody(stringBuilder.toString());
        }
        return httpResponse;
    }

    /**
     * 整理一下属性，为必要属性设置默认值
     *
     * @param header
     */
    private Map<String, String> buildRequestPropertys(Map<String, String> header) {

        Map<String, String> requestPropertys = new HashMap<String, String>(); // 用于发送到属性
        if (null != header) {
            for (Map.Entry<String, String> entry : header.entrySet()) { // 深copy
                requestPropertys.put(entry.getKey(), entry.getValue());
            }
        }

        // set default value ----------------------------------
        // Accept
        if (!requestPropertys.containsKey(ACCEPT_KEY)) {
            requestPropertys.put(ACCEPT_KEY, "*/*");
        }

        // Conntetion
        if (!requestPropertys.containsKey(CONNECTION_KEY)) {
            requestPropertys.put(CONNECTION_KEY, "Keep-Alive");
        }

        // User-Agent
        if (!requestPropertys.containsKey(USER_AGENT_KEY)) {
            requestPropertys.put(USER_AGENT_KEY, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        }

        return requestPropertys;

    }

    /**
     * 构建入参，将参数构建为 key1=value1&key2=value2&...keyn=valuen 的形式,
     * 同时进行urlEncode
     *
     * @param param 将map改造成 k1＝v1&k2=v2&k3=v3....kn=vn(n为map的size)
     * @return 构造好的字符串，如果map为null则返回空字符串
     */
    private String buildFormParam(Map<String, String> param) throws UnsupportedEncodingException {
        if (null == param || param.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(URLEncoder
                    .encode(entry.getKey(), this.charset.getName()));
            builder.append('=');
            builder.append(URLEncoder
                    .encode(entry.getValue(), this.charset.getName()));
            builder.append('&');
        }
        builder.deleteCharAt(builder.length() - 1);// delete the last "&"
        return builder.toString();
    }


    public static void main(String args[]) throws IOException {
        HttpRequest httpRequest = HttpRequest.getNesInstance().setUrl("http://www.baidu.com");
        System.out.println(httpRequest.sendPost().getBody());
    }


}
