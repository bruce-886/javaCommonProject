package com.example.projectCommon.Utils;

import com.example.projectCommon.CustomException.customHttpException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class OKHTTPHelper {

    // Todo : check if status == 200

    private static final Logger logger = LoggerFactory.getLogger(OKHTTPHelper.class);

    @Autowired
    SerDesHelper serDesHelper;

    public final static int CONNECTION_TIMEOUT = 5;
    /**
     * JSON格式
     */
//    public static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;
    public static final MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");

    /**
     * OkHTTP线程池最大空闲线程数
     */
    public final static int MAX_IDLE_CONNECTIONS = 100;
    /**
     * OkHTTP线程池空闲线程存活时间
     */
    public final static long KEEP_ALIVE_DURATION = 30L;

    public static String BASE64_PREFIX = "data:image/png;base64,";

    /**
     * client
     * 配置重试
     */
    private final static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .build();

    private Request buildGetRequestWithHeaders(String url, HashMap<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        return requestBuilder.url(url).build();
    }

    private Request buildPostRequest(String url, String body) {
        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(body, mediaTypeJson);

        return requestBuilder.url(url).post(requestBody).build();
    }

    private Request buildPostRequestWithHeaders(String url, String body, HashMap<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(body, mediaTypeJson);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        return requestBuilder.url(url).post(requestBody).build();
    }

    private void showErrorHeaders(HashMap<String, String> headers) {
        try {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                logger.error("Request headers : key => {}  value => {}", entry.getKey(), entry.getValue());
            }
        }
        catch (Exception e) {
            logger.error("Fail to show http request headers", e);
        }
    }

    private String executeHttp(Request request) {
        String responseString = "";
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // 200 <= statuscode <= 299 (2XX)  ===> isSuccessful return true
                throw new IOException("Unexpected code " + response.code());
            }
            logger.debug("HTTP Status code : " + response.code());

            // response.body().string() can only be executed 1 time!!!
            responseString = response.body().string();
            logger.debug("HTTP response : " + responseString);
        }
        catch (Exception e) {
            throw new customHttpException(e);
        }

        return responseString;
    }

    public String doHttpGetString(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            return executeHttp(request);

        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            throw new customHttpException(e);
        }
    }

    public <T> T doHttpGetObj(String url, Class<T> T) {
        try {
            String responseString = doHttpGetString(url);

            return serDesHelper.serializeToObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            throw new customHttpException(e);
        }
    }

    public <T> List<T> doHttpGetObjList(String url, Class<T> T) {
        try {
            String responseString = doHttpGetString(url);

            return serDesHelper.serializeToListOfObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            throw new customHttpException(e);
        }
    }


    public String doHttpGetString(String url, HashMap<String, String> headers) {
        try {
            Request request = buildGetRequestWithHeaders(url, headers);
//            throw new RuntimeException();
            return executeHttp(request);

        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            showErrorHeaders(headers);
            throw new customHttpException(e);
        }
    }

    public <T> T doHttpGetObj(String url, HashMap<String, String> headers, Class<T> T) {
        try {
            String responseString = doHttpGetString(url, headers);

            return serDesHelper.serializeToObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            showErrorHeaders(headers);
            throw new customHttpException(e);
        }
    }

    public <T> List<T> doHttpGetObjList(String url, HashMap<String, String> headers, Class<T> T) {
        try {
            String responseString = doHttpGetString(url, headers);

            return serDesHelper.serializeToListOfObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Get request");
            logger.error("Request URL : {}", url);
            showErrorHeaders(headers);
            throw new customHttpException(e);
        }
    }




    public String doHttpPostString(String url, String body) {
        try {
            Request request = buildPostRequest(url, body);

            return executeHttp(request);

        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }

    public <T> T doHttpPostObj(String url, String body, Class<T> T) {
        try {
            String responseString = doHttpPostString(url, body);

            return serDesHelper.serializeToObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }

    public <T> List<T> doHttpPostObjList(String url, String body, Class<T> T) {
        try {
            String responseString = doHttpPostString(url, body);

            return serDesHelper.serializeToListOfObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }


    public String doHttpPostString(String url, String body, HashMap<String, String> headers) {
        try {
            Request request = buildPostRequestWithHeaders(url, body, headers);

            return executeHttp(request);

        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }

    public <T> T doHttpPostObj(String url, String body, HashMap<String, String> headers, Class<T> T) {
        try {
            String responseString = doHttpPostString(url, body, headers);

            return serDesHelper.serializeToObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }

    public <T> List<T> doHttpPostObjList(String url, String body, HashMap<String, String> headers, Class<T> T) {
        try {
            String responseString = doHttpPostString(url, body, headers);

            return serDesHelper.serializeToListOfObject(responseString, T);
        }
        catch (Exception e) {
            logger.error("Fail to perform http Post request");
            logger.error("Request URL : {}", url);
            logger.error("Request Body : {}", body);
            throw new customHttpException(e);
        }
    }



//    public byte[] doHttpGetImage(String url) {
//        try {
//            Client client = Client.create(new DefaultClientConfig());
//            WebResource service = client.resource(url);
//            WebResource.Builder serviceBuilder = service.getRequestBuilder();
//
//
//            ClientResponse response = serviceBuilder.type(String.valueOf(MediaType.IMAGE_JPEG)).accept("text/plain").get(ClientResponse.class);
//
//            return response.getEntity(byte[].class);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

}
