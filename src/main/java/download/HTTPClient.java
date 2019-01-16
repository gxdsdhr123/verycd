package download;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

public class HTTPClient {

    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url,String jsonString)
    {
        final ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {

                String result = null;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost post = new HttpPost(url);
                CloseableHttpResponse response = null;
                try {
                    post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
                    response = httpClient.execute(post);
                    if(response != null && response.getStatusLine().getStatusCode() == 200)
                    {
                        HttpEntity entity = response.getEntity();
                        result = entityToString(entity);
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        httpClient.close();
                        if(response != null)
                        {
                            response.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        try {
            Future<String> future = exec.submit(call);
            // set db connection timeout to 15 seconds
            String obj = future.get(1000 * 15, TimeUnit.MILLISECONDS);
            return obj;
        } catch (TimeoutException ex) {

            System.err.println("任务超时, 正在重试...");
            return postJson(url, jsonString);
        } catch (Exception e) {

            System.err.println("执行失败");
        } finally {

            exec.shutdown();
        }

        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if(entity != null)
        {
            long lenth = entity.getContentLength();
            if(lenth != -1 && lenth < 2048)
            {
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }
}
