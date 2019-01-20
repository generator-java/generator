package com.netty.oio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by fengkai on 11/04/17.
 */
public class OIO {

    public void serve(int port) throws IOException{
        //绑定端口
        final ServerSocket socket = new ServerSocket(port);

        for(;;){
            final Socket clientSocket = socket.accept();
            System.out.println("A connetction from " +clientSocket);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OutputStream out;
                    InputStream in;
                    try{
                        in = clientSocket.getInputStream();
                        byte[] bytes = new byte[1024];
                        int bytelen = 0;
                        int bytein = 0;
                        StringBuffer sb = new StringBuffer();
                        while ((bytelen = in.read(bytes)) != -1){
                            String str = new String(bytes, bytein, bytelen);
                            sb.append(str);
                        }
                        System.out.println(sb.toString());
                        out = clientSocket.getOutputStream();
                        out.write("HelloWorld".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        clientSocket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        final int port = 8888;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new OIO().serve(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}