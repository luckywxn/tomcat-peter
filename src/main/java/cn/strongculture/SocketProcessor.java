package cn.strongculture;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketProcessor implements Runnable{

    private Socket socket;

    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        processSocket(socket);
    }

    private void processSocket(Socket socket){
        //处理Socket连接  读数据 写数据
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);

            //解析字节流
            int pos = 0;
            int begin = 0,end = 0;
            for (;pos < bytes.length;pos++,end++){
                if (bytes[pos] == ' ') break;
            }
            //组合空格之前的字节流，转换成字符串就是请求方法
            StringBuilder methodBuilder = new StringBuilder();
            for (int i = 0;i < end;i++){
                methodBuilder.append((char)bytes[i]);
            }
            System.out.println(methodBuilder.toString());

            //解析请求路径
            pos ++;
            begin = end + 1;
            end ++;
            for (;pos < bytes.length;pos++,end++){
                if (bytes[pos] == ' ') break;
            }
            StringBuilder pathBuilder = new StringBuilder();
            for (int i = 0;i < end-begin;i++){
                pathBuilder.append((char)bytes[i+begin]);
            }
            System.out.println(pathBuilder.toString());

            //解析协议版本
            pos ++;
            begin = end + 1;
            end ++;
            for (;pos < bytes.length;pos++,end++){
                if (bytes[pos] == '\r') break;
            }
            StringBuilder protocolBuilder = new StringBuilder();
            for (int i = 0;i < end-begin;i++){
                protocolBuilder.append((char)bytes[i+begin]);
            }
            System.out.println(protocolBuilder.toString());

            Request request = new Request(methodBuilder.toString(),protocolBuilder.toString(),protocolBuilder.toString(),socket);

            PeterServlet servlet = new PeterServlet();
            Response response = new Response(request);
            servlet.service(request,response);

            response.complete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

}
