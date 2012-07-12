package com.qburst.eshop.supportclass;

import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URI;
	import java.util.ArrayList;
	import org.apache.http.HttpResponse;
	import org.apache.http.NameValuePair;
	import org.apache.http.client.HttpClient;
	import org.apache.http.client.entity.UrlEncodedFormEntity;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.conn.params.ConnManagerParams;
	import org.apache.http.impl.client.DefaultHttpClient;
	import org.apache.http.params.HttpConnectionParams;
	import org.apache.http.params.HttpParams;
	public class CustomHttpClient {
	    /** The time it takes for our client to timeout */
	    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
	 
	    /** Single instance of our HttpClient */
	    private static HttpClient mHttpClient;
	 
	    /**
028	     * Get our single instance of our HttpClient object.
029	     *
030	     * @return an HttpClient object with connection parameters set
031	     */
	    private static HttpClient getHttpClient() {
	        if (mHttpClient == null) {
	            mHttpClient = new DefaultHttpClient();
	            final HttpParams params = mHttpClient.getParams();
	            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
	            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
	            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
	        }
	        return mHttpClient;
	    }
	 
	    /**
044	     * Performs an HTTP Post request to the specified url with the
045	     * specified parameters.
046	     *
047	     * @param url The web address to post the request to
048	     * @param postParameters The parameters to send via the request
049	     * @return The result of the request
050	     * @throws Exception
051	     */
	    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
	        BufferedReader in = null;
	        try {
	            HttpClient client = getHttpClient();
	            HttpPost request = new HttpPost(url);
	            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
	            request.setEntity(formEntity);
	            HttpResponse response = client.execute(request);
	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	 
	            StringBuffer sb = new StringBuffer("");
	            String line = "";
            String NL = System.getProperty("line.separator");
	            while ((line = in.readLine()) != null) {
	                sb.append(line + NL);
	            }
	            in.close();
	 
	            String result = sb.toString();
	            return result;
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	 
	    /**
084	     * Performs an HTTP GET request to the specified url.
085	     *
086	     * @param url The web address to post the request to
087	     * @return The result of the request
088	     * @throws Exception
089	     */
	    public static String executeHttpGet(String url) throws Exception {
	        BufferedReader in = null;
	        try {
	            HttpClient client = getHttpClient();
	            HttpGet request = new HttpGet();
	            request.setURI(new URI(url));
	            HttpResponse response = client.execute(request);
	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	 
	            StringBuffer sb = new StringBuffer("");
	            String line = "";
	            String NL = System.getProperty("line.separator");
	            while ((line = in.readLine()) != null) {
	                sb.append(line + NL);
	            }
	            in.close();
	 
	            String result = sb.toString();
	            return result;
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
}