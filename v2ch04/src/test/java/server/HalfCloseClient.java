package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 4.2.3 半关闭测试
 * Created by shucheng on 2020/9/26 9:59
 */
public class HalfCloseClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8189)) {
            Scanner in = new Scanner(socket.getInputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            // 发送请求数据
            writer.println("hahaha");
            socket.shutdownOutput();
            // 目前socket处于半关闭状态
            // 读取响应数据
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("server response: " + line);
            }
        }
    }
}
