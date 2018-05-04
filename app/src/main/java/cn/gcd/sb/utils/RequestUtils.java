package cn.gcd.sb.utils;

import android.accounts.NetworkErrorException;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RequestUtils {

    public interface ResponseCallback{
        void onResponse(String response);
    }

    public static void post(final String path, final String content, final ResponseCallback callback) {
        final Handler responseHandler = new Handler();
        Thread responseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = postRequest(path, content);
                responseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(response);
                    }
                });
            }
        });
        responseThread.start();
    }

    public static String postRequest(String path, String content) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();

            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return getResponse(inputStream);
            } else {
                throw new NetworkErrorException("response status is "+ code);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static void get(final String path, final ResponseCallback callback) {
        final Handler responseHandler = new Handler();
        Thread responseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = getRequest(path);
                responseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                       callback.onResponse(response);
                    }
                });
            }
        });
        responseThread.start();
    }

    public static String getRequest(String path) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);

            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return getResponse(inputStream);
            } else {
                throw new NetworkErrorException("response status is "+ code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static String getResponse(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            inputStream.close();
            String response = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
