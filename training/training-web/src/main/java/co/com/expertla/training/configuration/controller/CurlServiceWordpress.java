/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.configuration.controller;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.SignatureType;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
//import java.util.Base64;

/**
 *
 * @author Andres Lopez
 */
public class CurlServiceWordpress {
    
    private String urlService;

    public String sendData() throws IOException {
        // curl_init and url
        URL url = new URL("http://181.143.227.220:8081/cpt/wp-json/wc/v1/products/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //  CURLOPT_POST
        con.setRequestMethod("POST");

        // CURLOPT_FOLLOWLOCATION
        con.setInstanceFollowRedirects(true);
        
        String postData = "{\n" +
"  \"name\": \"Premium Quality\",\n" +
"  \"type\": \"simple\",\n" +
"  \"regular_price\": \"21.99\",\n" +
"  \"description\": \"Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.\",\n" +
"  \"short_description\": \"Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\",\n" +
"  \"categories\": [\n" +
"    {\n" +
"      \"id\": 1\n" +
"    }\n" +
"  ]\n" +
"}";        
        String userpass = "ck_6ea5053d15f3652bf690cb5b13398e1141431284:cs_fec92202d7c8a19f7265f4009f5185e4cbe3bc1b";
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
        con.setRequestProperty ("Authorization", basicAuth);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-length", String.valueOf(postData.length()));
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        output.writeBytes(postData);
        output.close();

        // "Post data send ... waiting for reply");
        int code = con.getResponseCode(); // 200 = HTTP_OK
        System.out.println("Response    (Code):" + code);
        System.out.println("Response (Message):" + con.getResponseMessage());

        // read the response
        DataInputStream input = new DataInputStream(con.getInputStream());
        int c;
        StringBuilder resultBuf = new StringBuilder();
        while ((c = input.read()) != -1) {
            resultBuf.append((char) c);
        }
        input.close();

        return resultBuf.toString();
    }
    
    public void send() throws IOException {
        final OAuth10aService service = new ServiceBuilder()
                           .apiKey("ck_6ea5053d15f3652bf690cb5b13398e1141431284")
                           .apiSecret("cs_fec92202d7c8a19f7265f4009f5185e4cbe3bc1b")
                           .build(TwitterApi.instance());
        
        OAuthRequest request = new OAuthRequest(Verb.POST, "http://181.143.227.220:8081/cpt/wp-json/wc/v1/products", service);
        
        request.addBodyParameter("name", "Premium Quality");
//        service.signRequest(accessToken, request); // the access token from step 4
        final Response response = request.send();
        System.out.println(response.getBody());
        
    }
    
    public static void main(String[] args) throws IOException {
        CurlServiceWordpress curlServiceWordpress = new CurlServiceWordpress();
        String data = curlServiceWordpress.sendData();
//        curlServiceWordpress.send();
        System.out.println("data " + data);
    }
}
