package com.aplace.core.net;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


/**
 * 原生java库封装http请求，包含post与get
 *
 * @author ningning.wei
 * @version 17/5/11.
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 默认超时时间为30秒
     */
    private static int DEFAULT_TIME_OUT = 30000;


//

    /**
     * content type 为form时（入参为Key－Value）的请求方式，默认超时时间为30秒
     *
     * @return respone的body
     */
    public static String sendPost(String url, Map<String, String> params, CharSetEnum charSet) {
        String requestBody = buildParam(params);
        return sendPost(url, requestBody, ContentTypeEnum.FORM_URLENCODE, charSet);
    }

    /**
     * jdk 原生 http post 请求，默认超时时间为30秒
     *
     * @param url         请求地址
     * @param body        入参String
     * @param contentType 内容类型
     * @param charSet     编码格式
     * @return respone的body
     */
    public static String sendPost(String url, String body, ContentTypeEnum contentType, CharSetEnum charSet) {
        return sendPost(url, body, contentType, charSet, DEFAULT_TIME_OUT);
    }

    /**
     * JDK原生post请求
     *
     * @param url         访问地址
     * @param body        request请求体
     * @param contentType 请求类型
     * @param charSet     编码格式
     * @param timeOut     超时时间，单位毫秒
     * @return 响应体string
     */
    public static String sendPost(String url, String body, ContentTypeEnum contentType, CharSetEnum charSet, int timeOut) {
        URLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = realUrl.openConnection();
            // 设置通用的请求头属性
            conn.setRequestProperty("Content-Type", String.format("%s;charset=%s", contentType.getName(), charSet.getName()));
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendRequest(conn, body, charSet);
    }
//    public static String sendPost(String url, String body, ContentTypeEnum contentType, CharSetEnum charSet, int timeOut) {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        StringBuilder stringBuilder = new StringBuilder();
//
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            // 设置通用的请求头属性
//            conn.setRequestProperty("Content-Type", String.format("%s;charset=%s", contentType.getName(), charSet.getName()));
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setConnectTimeout(timeOut);
//            conn.setReadTimeout(timeOut);
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
////	            out = new PrintWriter(conn.getOutputStream());
//            OutputStreamWriter ow = new OutputStreamWriter(conn.getOutputStream(), charSet.getName());
//            out = new PrintWriter(ow, true);
//            // 发送请求参数
//            out.print(body);
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            InputStreamReader ir = new InputStreamReader(conn.getInputStream(), charSet.getName());
//            in = new BufferedReader(ir);
//            String line;
//            while ((line = in.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //使用finally块来关闭输出流、输入流
//        finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return stringBuilder.toString();
//    }


    /**
     * 发送GET请求
     *
     * @param url     访问地址
     * @param params  k-v的形式
     * @param charSet 字符集枚举型
     * @return 远程响应结果response Body
     */
    public static String sendGet(String url, Map<String, String> params, CharSetEnum charSet) {
        return sendGet(url, params, charSet, DEFAULT_TIME_OUT);
    }

    /**
     * 发送GET请求
     *
     * @param url     目的地址
     * @param params  k-v形式，请求参数，Map类型。
     * @param charSet 服务段字符编码格式
     * @param timeOut 超时时间，单位毫秒
     * @return 远程响应结果response Body
     */
    public static String sendGet(String url, Map<String, String> params, CharSetEnum charSet, int timeOut) {
        StringBuffer stringBuffer = new StringBuffer();// 存储参数
        String paramUrl = "";// 编码之后的参数
        HttpURLConnection httpConn = null;
        try {
            if (params != null) {
                // 编码请求参数
                if (params.size() == 1) {
                    for (String name : params.keySet()) {
                        stringBuffer.append(name).append("=").append(
                                java.net.URLEncoder.encode(params.get(name),
                                        charSet.getName()));
                    }
                    paramUrl = stringBuffer.toString();
                } else {
                    for (String name : params.keySet()) {
                        stringBuffer.append(name).append("=").append(
                                java.net.URLEncoder.encode(params.get(name),
                                        charSet.getName())).append("&");
                    }
                    String temp_params = stringBuffer.toString();
                    paramUrl = temp_params.substring(0, temp_params.length() - 1);
                }

            }
            String full_url = url;
            if(paramUrl.length()>0)
                full_url = full_url + "?" + paramUrl;

            logger.debug(full_url);
//          System.out.println(full_url);

            // 创建URL对象
            URL connURL = new URL(full_url);
            // 打开URL连接
            httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            httpConn.setConnectTimeout(timeOut);
            httpConn.setReadTimeout(timeOut);
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取


        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendRequest(httpConn, null, charSet);
    }
//    public static String sendGet(String url, Map<String, String> parameters, CharSetEnum charSet, int timeOut) {
////        String result = "";// 返回的结果
//        BufferedReader in = null;// 读取响应输入流
//        StringBuffer stringBuffer = new StringBuffer();// 存储参数
//        String params = "";// 编码之后的参数
//
//        try {
//            if (parameters != null) {
//                // 编码请求参数
//                if (parameters.size() == 1) {
//                    for (String name : parameters.keySet()) {
//                        stringBuffer.append(name).append("=").append(
//                                java.net.URLEncoder.encode(parameters.get(name),
//                                        charSet.getName()));
//                    }
//                    params = stringBuffer.toString();
//                } else {
//                    for (String name : parameters.keySet()) {
//                        stringBuffer.append(name).append("=").append(
//                                java.net.URLEncoder.encode(parameters.get(name),
//                                        charSet.getName())).append("&");
//                    }
//                    String temp_params = stringBuffer.toString();
//                    params = temp_params.substring(0, temp_params.length() - 1);
//                }
//
//            }
//            String full_url = url + "?" + params;
//            System.out.println(full_url);
//            // 创建URL对象
//            java.net.URL connURL = new java.net.URL(full_url);
//            // 打开URL连接
//            HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
//                    .openConnection();
//            // 设置通用属性
//            httpConn.setRequestProperty("Accept", "*/*");
//            httpConn.setRequestProperty("Connection", "Keep-Alive");
//            httpConn.setRequestProperty("User-Agent",
//                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
//            httpConn.setConnectTimeout(timeOut);
//            httpConn.setReadTimeout(timeOut);
//            // 建立实际的连接
//            httpConn.connect();
//            // 响应头部获取
//            Map<String, List<String>> headers = httpConn.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : headers.keySet()) {
//                System.out.println(key + "\t：\t" + headers.get(key));
//            }
//            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
//            in = new BufferedReader(new InputStreamReader(httpConn
//                    .getInputStream(), charSet.getName()));
//            String line;
//            // 读取返回的内容
//            stringBuffer.delete(0, stringBuffer.length());
//            while ((line = in.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return stringBuffer.toString();
//    }

    private static String sendRequest(URLConnection conn, String body, CharSetEnum charSet) {
        OutputStreamWriter ow ;
        PrintWriter out = null;
        InputStreamReader ir ;
        BufferedReader in = null;// 读取响应输入流
        StringBuilder stringBuilder = null;

        try {
            if (body != null) {
                ow = new OutputStreamWriter(conn.getOutputStream(), charSet.getName());
                out = new PrintWriter(ow, true);
                // 发送请求参数
                out.print(body);
                // flush输出流的缓冲
                out.flush();

            }
            // 定义BufferedReader输入流来读取URL的响应
            Map<String, List<String>> headers = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
//                System.out.println(key + "\t：\t" + headers.get(key));
                logger.debug(String.format("\t%s : \t%s", key, headers.get(key)));
            }

            ir = new InputStreamReader(conn.getInputStream(), charSet.getName());
            in = new BufferedReader(ir);
            String line;
            stringBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (stringBuilder == null)
            return null;
        return stringBuilder.toString();
    }

    /**
     * 构建入参，将参数构建为 key1=value1&key2=value2&...keyn=valuen 的形式
     *
     * @param param 将map改造成 k1＝v1&k2=v2&k3=v3....kn=vn(n为map的size)
     * @return 构造好的字符串，如果map为null则返回空字符串
     */
    public static String buildParam(Map<String, String> param) {
        if (param == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        builder.deleteCharAt(builder.length() - 1);// delete the last "&"
        return builder.toString();
    }


//    /**
//     //     * 编码格式
//     //     */
//    public enum CharSet {
//        UTF_8("UTF-8" ),
//        GBK("GBK" );
//
//        private String value;
//        CharSet(String value) {
//            this.value = value;
//        }
//        public String Value() {
//            return value;
//        }
//        @Override
//        public String toString(){
//            return value;
//        }
//    }
//
//    /**
//     * http内容类型的枚举
//     */
//    public enum ContentType { // 不够加
//        FORM_URLENCODE("application/x-www-form-urlencoded" ),
//        JSON("application/json" ),
//        XML("text/xml" ),
//        HTML("text/html" );
//
//        private String value;
//        ContentType(String value) {
//            this.value = value;
//        }
//        public String Value() {
//            return value;
//        }
//        @Override
//        public String toString(){
//            return value;
//        }
//    }


//
//
//    /**
//     * 创建 HTTPS 链接客户端,默认信任证书,不跟随重定向
//     *
//     * @return HTTP 连接
//     */
//    private static CloseableHttpClient createHttpsClient()
//            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, new TrustStrategy() {
//                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        return true;
//                    }
//                }).build();
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//        return HttpClients.custom()
//                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
//                .setSSLSocketFactory(sslConnectionSocketFactory)
//                .build();
//    }
//
//    /**
//     * 发送 get 请求
//     *
//     * @param url     请求 url
//     * @param headers 请求头数组
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String get(String url, Header[] headers)
//            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
//        CloseableHttpClient httpClient = createHttpsClient();
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.setHeaders(headers);
//        HttpResponse httpResponse = httpClient.execute(httpGet);
//        String result = entity2String(httpResponse.getEntity());
//
//        // 关闭资源
//        httpClient.close();
//        httpGet.releaseConnection();
//        return result;
//    }
//
//    /**
//     * 发送 get 请求
//     *
//     * @param url 请求 url
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String get(String url)
//            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
//        return get(url, null);
//    }
//
//    /**
//     * 发送 post 请求
//     *
//     * @param url     请求 url
//     * @param headers 请求头
//     * @param entity  请求实体
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String post(String url, Header[] headers, HttpEntity entity)
//            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
//        CloseableHttpClient httpClient = createHttpsClient();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeaders(headers);
//        httpPost.setEntity(entity);
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        String result = entity2String(httpResponse.getEntity());
//
//        // 关闭资源
//        httpClient.close();
//        httpPost.releaseConnection();
//        return result;
//    }
//
//    /**
//     * 发送 post 请求
//     *
//     * @param url 请求 url
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String post(String url)
//            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        return post(url, null, null);
//    }
//
//    /**
//     * 发送 post 请求
//     *
//     * @param url     请求 url
//     * @param headers 请求头
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String post(String url, Header[] headers)
//            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        return post(url, headers, null);
//    }
//
//    /**
//     * 发送 post 请求
//     *
//     * @param url    请求 url
//     * @param entity 请求体
//     * @return 响应结果字符串
//     * @throws NoSuchAlgorithmException
//     * @throws KeyStoreException
//     * @throws KeyManagementException
//     * @throws IOException
//     */
//    public static String post(String url, HttpEntity entity)
//            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        return post(url, null, entity);
//    }
//
//    /**
//     * 将响应实体拼接成字符串返回
//     *
//     * @param entity 响应实体
//     * @return 实体字符串
//     */
//    private static String entity2String(HttpEntity entity) {
//        StringBuilder content = new StringBuilder();
//        try (InputStream inputStream = entity.getContent();
//             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
//            // 读取数据
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }
//
//
//
//


}
