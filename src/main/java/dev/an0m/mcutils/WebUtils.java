package dev.an0m.mcutils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static dev.an0m.mcutils.TextUtils.compactStringArray;
import static dev.an0m.mcutils.TextUtils.rsplit;

public class WebUtils {
    public static final String exampleUserAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.62";

    /**
     * Basic URL reader, for when you just want the output of a request
     * @return The response stream
     * */
    public static Stream<String> readStreamFromUrl(URL url, String userAgent) throws IOException {
        URLConnection din = url.openConnection();
        din.addRequestProperty("User-Agent", userAgent);

        return new BufferedReader(new InputStreamReader(din.getInputStream())).lines();
    }
    /**
     * Basic URL reader, for when you just want the output of a request
     * @return The response lines
     * */
    public static List<String> readLinesFromUrl(URL url, String userAgent) throws IOException {
        List<String> lines = new ArrayList<>();
        readStreamFromUrl(url, userAgent).forEachOrdered(lines::add);
        return lines;
    }
    /**
     * Basic URL reader, for when you just want the output of a request
     * @return The response lines
     * */
    public static List<String> readLinesFromUrl(String url) throws IOException {
        return readLinesFromUrl(new URL(url), exampleUserAgent);
    }
    /**
     * Basic URL reader, for when you just want the output of a request
     * @return The response
     * */
    public static String readFromUrl(URL url, String userAgent) throws IOException {
        return compactStringArray(readLinesFromUrl(url, userAgent), 0, "\n");
    }
    /**
     * Basic URL reader, for when you just want the output of a request
     * @return The response
     * */
    public static String readFromUrl(String string) throws IOException {
        return readFromUrl(new URL(string), exampleUserAgent);
    }


    /**
     * Send a basic Http request, using the HttpUrlConnection
     * @return The connection
     * */
    public static HttpURLConnection sendHttpRequest(URL url, String method, boolean doOutput, String userAgent) throws IOException {
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod(method);
        http.setDoOutput(doOutput);
        http.setRequestProperty("User-Agent", userAgent);

        return http;
    }

    /**
     * Send a basic Http GET request, using the HttpUrlConnection
     * @return The response code
     * */
    public static int getResponseCode(URL url) throws IOException {
        return sendHttpRequest(url, "GET", false, exampleUserAgent).getResponseCode();
    }
    /**
     * Send a basic Http GET request, using the HttpUrlConnection
     * @return The response code
     * */
    public static int getResponseCode(String url) throws IOException {
        return getResponseCode(new URL(url));
    }

    /**
     * Send a basic Http POST request, using the HttpUrlConnection
     * @return The connection
     * */
    public static HttpURLConnection postData(URL url, byte[] data, String contentType, String userAgent) throws IOException {
        HttpURLConnection http = sendHttpRequest(url, "POST", true, userAgent);
        http.setRequestProperty("Content-Type", contentType);

        OutputStream stream = http.getOutputStream();
        stream.write(data);

        return http;
    }
    /**
     * Send a basic Http POST request, using the HttpUrlConnection
     * @return The response code
     * */
    public static int postData(URL url, byte[] data) throws IOException {
        return postData(url, data, "text/plain", exampleUserAgent).getResponseCode();
    }
    /**
     * Send a basic Http POST request, using the HttpUrlConnection
     * @return The response code
     * */
    public static int postData(String url, byte[] data) throws IOException {
        return postData(new URL(url), data);
    }

    public static String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    /** Returns if a port is reachable using a socket */
    public static boolean testSocketConnection(SocketAddress address, int timeoutMs) {
        try {
            Socket s = new Socket();
            s.connect(address, timeoutMs);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /** Returns if a port is reachable using a socket */
    public static boolean testSocketConnection(SocketAddress address) {
        return testSocketConnection(address, 20);
    }

    /** Splits a socket address into IP and port */
    public static String[] splitSocketAddress(SocketAddress address) {
        return rsplit(address.toString().substring(1), ':');
    }
}
