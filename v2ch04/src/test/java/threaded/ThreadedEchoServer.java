package threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 4.2.2 为多个客户端服务
 * Created by shucheng on 2020/9/25 22:19
 */
public class ThreadedEchoServer {

    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8189)) {
            int i = 1;
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ThreadedEchoHandler implements Runnable {

        private Socket incoming;

        public ThreadedEchoHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try (InputStream inStream = incoming.getInputStream();
                 OutputStream outStream = incoming.getOutputStream()) {
                Scanner in = new Scanner(inStream, "UTF-8");
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(outStream, "UTF-8"),
                        true);
                out.println("Hello! Enter BYE to exit.");
                // 回显客户端输入
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE")) {
                        done = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (incoming != null) {
                    try {
                        incoming.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
