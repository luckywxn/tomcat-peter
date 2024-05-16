package cn.strongculture;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tomcat {

    public void start() {
        System.out.println("Tomcat is running");
        //Socket连接 TCP
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new SocketProcessor(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.start();
    }
}
