package webdata;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 4.4.1 URL和URI
 *
 * URI（Uniform Resource Identifier）统一资源标识符：是个纯粹的语法结构，包含用来指定Web资源的字符串的各种组成部分
 *
 * URL（Uniform Resource Location）统一资源定位符：是URI的一个特例，包含了用于定位Web资源的足够信息；
 * 有些URI，比如 mailto:cay@horstmann.com 则不属于定位符，因为根据该标识符我们无法定位任何数据，这样的URI
 * 我们称之为URN（uniform resource name，统一资源名称）
 * Created by shucheng on 2020/9/26 18:48
 */
public class URLTest {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * URI的使用1：
     * 解析标识符并将它分解成各种不同的组成部分
     */
    @Test
    public void test() throws URISyntaxException {
        URI uri = new URI("http://google.com?q=Beach+Chalet");
        logger.info("getScheme---------{}", uri.getScheme());
        logger.info("getSchemeSpecificPart---------{}", uri.getSchemeSpecificPart());
        logger.info("getAuthority---------{}", uri.getAuthority());
        logger.info("getUserInfo---------{}", uri.getUserInfo());
        logger.info("getHost---------{}", uri.getHost());
        logger.info("getPort---------{}", uri.getPort());
        logger.info("getPath---------{}", uri.getPath());
        logger.info("getQuery---------{}", uri.getQuery());
        logger.info("getFragment---------{}", uri.getFragment());
    }

    /**
     * URI的使用2：
     * 处理绝对标识符和相对标识符
     */
    @Test
    public void test2() throws URISyntaxException {
        URI a = new URI("http://docs.mycompany.com/api/java/net/ServerSocket.html");
        URI b = new URI("../../java/net/Socket.html#Socket()");
        logger.info("a.resolve(b)---------{}", a.resolve(b)); // a和b组合出一个绝对URI
        URI c = new URI("http://docs.mycompany.com/api");
        URI d = new URI("http://docs.mycompany.com/api/java/lang/String.html");
        logger.info("c.relativize(d)---------{}", c.relativize(d)); // 获取d相对c的URI
    }
}
