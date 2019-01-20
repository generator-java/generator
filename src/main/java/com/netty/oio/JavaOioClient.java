package com.netty.oio;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class JavaOioClient {

    public void client(Integer integer) {
        Socket client = null;
        BufferedReader is = null;
        Writer writer = null;
        try {
            client = new Socket("127.0.0.1", 8898);

            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(",Hello Server." + integer);
            writer.flush();

            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            // logger.info(sb.toString());
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            JavaOioClient sc = new JavaOioClient();
            sc.client(i);
        }
    }

}
