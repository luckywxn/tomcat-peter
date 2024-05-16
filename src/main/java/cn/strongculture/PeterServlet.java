package cn.strongculture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PeterServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getMethod());
        response.addHeader("Content-Type","text/html;charset=utf-8");
        response.addHeader("Content-Length",String.valueOf("Hello Peter".getBytes().length));
        response.getOutputStream().write("Hello Peter".getBytes());
    }
}
