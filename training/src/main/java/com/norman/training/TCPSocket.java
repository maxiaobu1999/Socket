package com.norman.training;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 姓名：马庆龙 on 2019/4/24 2:32 PM
 * 功能：基于TCP的socket客户端
 * 步骤：
 * 1、创建Socket 连接服务端
 * 2、getOutputStream() 发送数据
 * 3、getInputStream() 接收数据
 * 4、需要时 close() 掉 Socket
 */
public class TCPSocket {
    public void request(String requestString) throws IOException {
        //客户端请求与本机在20006端口建立TCP连接
        Socket client = new Socket("127.0.0.1", 20006);
        client.setSoTimeout(10000);
        //获取请求内容
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        //发送数据：获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        String str = input.readLine();
        //发送数据到服务端
        out.println(str);
        System.out.println("++客户端：发送数据++"+str);

        //接收数据：获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        try {
            while (true) {
                //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                String echo = buf.readLine();
                System.out.println("++客户端：接收数据++"+echo);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Time out, No response");
        }
        input.close();
        //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
        client.close(); //只关闭socket，其关联的输入输出流也会被关闭

    }
}
