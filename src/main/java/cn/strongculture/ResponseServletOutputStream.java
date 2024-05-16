package cn.strongculture;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class ResponseServletOutputStream extends ServletOutputStream {

    private byte[] bytes = new byte[1024];
    private int index = 0;
    @Override
    public void write(int b) throws IOException {
        //响应体
        bytes[index] = (byte) b;
        index++;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
