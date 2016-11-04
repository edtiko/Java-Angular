/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Andres Lopez
 */
public class CurlService {
    
    /**
     * 
     * @param uri
     * @param params
     * @param isAuth
     * @param codeAuth
     * @return
     * @throws IOException 
     */
    public String sendPost(String uri, String params, boolean isAuth, String codeAuth) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        if(isAuth) {
            String basicAuth = "Bearer " + codeAuth;
            con.setRequestProperty ("Authorization", basicAuth);
        }
        
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);
        return sendRequest(con, params);
    }
    
    /**
     * 
     * @param uri
     * @param params
     * @param isAuth
     * @param codeAuth
     * @return
     * @throws IOException 
     */
    public String sendGet(String uri, String params, boolean isAuth, String codeAuth) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        if(isAuth) {
            String basicAuth = "Basic " + new String(new Base64().encode(codeAuth.getBytes()));
            con.setRequestProperty ("Authorization", basicAuth);
        }
        
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);
        return sendRequest(con, params);
    }
    
    /**
     * 
     * @param uri
     * @param params
     * @param method
     * @param isAuth
     * @param codeAuth
     * @return
     * @throws IOException 
     */
    public String send(String uri, String params, String method, boolean isAuth, String codeAuth) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        if(isAuth) {
            String basicAuth = "Basic " + new String(new Base64().encode(codeAuth.getBytes()));
            con.setRequestProperty ("Authorization", basicAuth);
        }
        
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);
        return sendRequest(con, params);
    }
    
    /**
     * 
     * @param con
     * @param postData
     * @return
     * @throws IOException 
     */
    private String sendRequest(HttpURLConnection con, String postData) throws IOException {
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        if(postData != null) {
            con.setRequestProperty("Content-length", String.valueOf(postData.length()));
        }
        
        con.setDoOutput(true);
        con.setDoInput(true);
        try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
            if(postData != null) {
                output.writeBytes(postData);
            }
        }
        int code = con.getResponseCode(); // 200 = HTTP_OK
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
}
