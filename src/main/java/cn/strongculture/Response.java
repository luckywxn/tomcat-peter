package cn.strongculture;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response extends AbstractHttpResponse{
    private int status = 200;
    private String message = "OK";
    private byte SP = ' ';
    private byte CR = '\r';
    private byte LF = '\n';
    private Map<String, String> headers = new HashMap<>();
    private Request request;
    private OutputStream socketOutputStream ;
    private ResponseServletOutputStream responseServletOutputStream = new ResponseServletOutputStream();

    public Response(Request request) throws IOException {
        this.request = request;
        this.socketOutputStream = request.getSocket().getOutputStream();
    }

    @Override
    public void setStatus(int status,String s) {
        this.status = status;
        this.message = s;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void addHeader(String s, String s1) {
        headers.put(s, s1);
    }

    @Override
    public ResponseServletOutputStream getOutputStream() {
        return responseServletOutputStream;
    }

    public void complete() throws IOException {
        sendResponseLine();
        sendResponseHeaders();
        sendResponseBody();
    }

    private void sendResponseBody() throws IOException {
        socketOutputStream.write(getOutputStream().getBytes());
    }

    private void sendResponseHeaders() throws IOException {
        for (String key : headers.keySet()){
            socketOutputStream.write(key.getBytes());
            socketOutputStream.write(':');
            socketOutputStream.write(headers.get(key).getBytes());
            socketOutputStream.write(CR);
            socketOutputStream.write(LF);
        }
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);
    }

    private void sendResponseLine() throws IOException {
        socketOutputStream.write(request.getProtocol().getBytes());
        socketOutputStream.write(SP);
        socketOutputStream.write(status);
        socketOutputStream.write(SP);
        socketOutputStream.write(message.getBytes());
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);
    }
}
