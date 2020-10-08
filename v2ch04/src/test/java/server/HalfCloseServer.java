package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 4.2.3 半关闭
 * 服务端不是半关闭的，客户端是半关闭的
 * 测试时，先开server，然后再开client
 * Created by shucheng on 2020/9/26 9:58
 */
public class HalfCloseServer {

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8189);
             Socket incoming = serverSocket.accept();
             InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream()) {
            try (Scanner in = new Scanner(inStream, "UTF-8");
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);) {
                // 读取客户端输入
                String line = in.nextLine();
                System.out.println("client request: " + line);
                out.println("Echo: " + line);
            }
        }
    }
}
