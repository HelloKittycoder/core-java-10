package socket;

import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by shucheng on 2020/9/25 18:07
 */
public class SocketTest {

    // 4.1.2 用Java连接到服务器
    @Test
    public void test() throws IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
            Scanner in = new Scanner(s.getInputStream(), "UTF-8")) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }

    // 4.1.3 套接字超时
    // Connect time out
    @Test(expected = ConnectException.class)
    public void test2() throws IOException {
        try (Socket s = new Socket("www.google.com", 80);) {
            s.setSoTimeout(2000);
        }
    }

    // Socket time out
    @Test(expected = SocketTimeoutException.class)
    public void test3() throws IOException {
        try (Socket s = new Socket();) {
            s.connect(new InetSocketAddress("www.google.com", 80), 1000);
        }
    }

    // 4.1.4 因特网地址
    @Test
    public void test4() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("time-a.nist.gov");
        System.out.println(address);
        // byte[] addressBytes = address.getAddress();

        // 打印该主机的所有因特网地址
        InetAddress[] allAddresses = InetAddress.getAllByName("baidu.com");
        System.out.println(Arrays.toString(allAddresses));

        // 打印本地主机的因特网地址
        InetAddress address2 = InetAddress.getLocalHost();
        System.out.println(address2);
    }
}