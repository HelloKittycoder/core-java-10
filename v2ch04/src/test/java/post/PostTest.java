package post;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * 4.4.3 提交表单数据
 * 验证未成功，暂时跳过
 *
 * 使用URLConnection类发送POST请求
 * Created by shucheng on 2020/9/27 9:20
 */
public class PostTest {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String propsFilename = args.length > 0 ? args[0] : "post/post.properties";
        URL resource = ClassLoader.getSystemResource(propsFilename);
        Properties props = new Properties();
        Path resourcePath = Paths.get(resource.toURI());
        try (InputStream in = Files.newInputStream(resourcePath)) {
            props.load(in);
        }
        String urlString = props.remove("url").toString();
        Object userAgent = props.remove("User-Agent");
        Object redirects = props.remove("redirects");
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        String result = doPost(new URL(urlString), props,
                userAgent == null ? null : userAgent.toString(),
                redirects == null ? -1 : Integer.parseInt(redirects.toString()));
        System.out.println(result);
    }

    /**
     * 发送POST请求
     * @param url 需要请求的URL
     * @param nameValuePairs 请求参数
     * @param userAgent 需要用的user agent，如果是默认的user agent则为null
     * @param redirects 需要手动重定向的次数，如果为-1表示要自动重定向
     * @return 从服务器返回的数据
     */
    public static String doPost(URL url, Map<Object, Object> nameValuePairs, String userAgent,
                                int redirects) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (redirects >= 0)
            connection.setInstanceFollowRedirects(false);

        connection.setDoOutput(true);

        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            boolean first = true;
            for (Map.Entry<Object, Object> pair : nameValuePairs.entrySet()) {
                if (first) first = false;
                else out.print('&');

                String name = pair.getKey().toString();
                String value = pair.getValue().toString();
                out.print(name);
                out.print('=');
                out.print(URLEncoder.encode(value, "UTF-8"));
            }
        }
        String encoding = connection.getContentEncoding();
        if (encoding == null) encoding = "UTF-8";

        if (redirects > 0) {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
                || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                String location = connection.getHeaderField("Location");
                if (location != null) {
                    URL base = connection.getURL();
                    connection.disconnect();
                    return doPost(new URL(base, location),  nameValuePairs, userAgent, redirects - 1);
                }
            }
        } else if (redirects == 0) {
            throw new IOException("Too many redirects");
        }

        StringBuilder response = new StringBuilder();
        try (Scanner in = new Scanner(connection.getInputStream(), encoding)) {
            while (in.hasNextLine()) {
                response.append(in.nextLine());
                response.append("\n");
            }
            return response.toString();
        }
    }
}