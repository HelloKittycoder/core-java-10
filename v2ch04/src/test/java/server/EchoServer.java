package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 4.2 实现服务器
 * Created by shucheng on 2020/9/25 20:59
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket s = new ServerSocket(8189); // 创建ServerSocket
             Socket incoming = s.accept(); // 等待客户端连接
             InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream()) {
            try (Scanner in = new Scanner(inStream, "UTF-8")) {
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(outStream,"UTF-8"),
                        true);
                out.println("Hello! Enter BYE to exit.");
                // 回显客户端输入
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE")) done = true;
                }
            }
        }
    }
}
