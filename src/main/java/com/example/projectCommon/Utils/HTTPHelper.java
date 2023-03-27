package com.example.projectCommon.Utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPHelper {

    // Todo : check if status == 200

    private static final Logger logger = LoggerFactory.getLogger(HTTPHelper.class);

    @Autowired
    SerDesHelper serDesHelper;

    public byte[] doHttpGetImage(String url) {
        try {
            Client client = Client.create(new DefaultClientConfig());
            WebResource service = client.resource(url);
            WebResource.Builder serviceBuilder = service.getRequestBuilder();


            ClientResponse response = serviceBuilder.type(String.valueOf(MediaType.IMAGE_JPEG)).accept("text/plain").get(ClientResponse.class);

            return response.getEntity(byte[].class);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String doHttpPostString(String url, String body, HashMap<String, String> headers) {
        try {
            Client client = Client.create(new DefaultClientConfig());
            WebResource service = client.resource(url);
            WebResource.Builder serviceBuilder = service.getRequestBuilder();

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                serviceBuilder.header(entry.getKey(), entry.getValue());
            }

            ClientResponse response = serviceBuilder.type(String.valueOf(MediaType.APPLICATION_JSON)).accept("text/plain").post(ClientResponse.class, body);

            return response.getEntity(String.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public <T> List<T> doHttpPostObjList(String url, String body, Class<T> T) {
        try {
            Client client = Client.create(new DefaultClientConfig());
            WebResource service = client.resource(url);
            WebResource.Builder serviceBuilder = service.getRequestBuilder();

            ClientResponse response = serviceBuilder.type(String.valueOf(MediaType.APPLICATION_JSON)).accept("*/*").post(ClientResponse.class, body);

            String responseString = response.getEntity(String.class);

            return serDesHelper.serializeToListOfObject(responseString, T);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
