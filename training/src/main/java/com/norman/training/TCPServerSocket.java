package com.norman.training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.MIN_PRIORITY;

/**
 * 姓名：马庆龙 on 2019/4/24 2:32 PM
 * 功能：基于TCP的socket服务端
 * 步骤：
 * 1、创建ServerSocket，指定监听端口
 * 2、调用accept()，从连接队列中取出一个 Socket
 * 3、使用 Socket 响应客户端请求
 * 4、需要时 close() 掉 Socket
 */
public class TCPServerSocket {
    public void open() throws IOException {
        //服务端在20006端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(20006);
        System.out.println("++服务端启动");
        Socket client;
        boolean f = true;
        while (f) {
            //调用accept()，从连接队列中取出一个 Socket
            //服务端在调用 accept（）等待客户端的连接请求时会阻塞，直到收到客户端发送的连接请求才会继续往下执行代码，
            //因此要为每个 Socket 连接开启一个线程。
            client = server.accept();
            System.out.println("++与客户端连接成功！");
            //为每个客户端连接开启一个线程
            Thread thread = new Thread(new ServerThread(client));
            thread.setPriority(MIN_PRIORITY);
            thread.start();
        }
        server.close();

    }

    /** 针对每一个请求，单独开一个线程处理 */
    private class ServerThread implements Runnable {
        private Socket client;

        ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                //获取Socket的输出流，用来向客户端发送数据
                PrintStream out = new PrintStream(client.getOutputStream());
                //获取Socket的输入流，用来接收从客户端发送过来的数据
                BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
                boolean flag = true;
                while (flag) {
                    //接收从客户端发送过来的数据
                    String str = buf.readLine();
                    System.out.println("++服务端：接收到++"+str);
                    if (str == null || "".equals(str)) {
                        flag = false;
                    } else {
                            //将接收到的字符串前面加上echo，发送到对应的客户端
                            out.println("服务端返回的数据:" + str);
                    }
                }
                out.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
