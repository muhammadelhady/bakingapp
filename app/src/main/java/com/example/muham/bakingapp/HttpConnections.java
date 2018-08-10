package com.example.muham.bakingapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnections {


    public static String ConnectHttpUrl(String link) throws IOException {
        URL url = new URL(link);
            String Data;
        HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
           Data=StreamToString(inputStream);
          inputStream.close();
        return Data;
    }


    public static String StreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String text="";
        while ((line=bufferedReader.readLine())!=null)
        {
            text+=line;
        }
        bufferedReader.close();
        return text;
    }
}
