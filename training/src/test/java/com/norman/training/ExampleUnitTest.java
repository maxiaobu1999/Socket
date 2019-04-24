package com.norman.training;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /** tcp socket服务端 */
    @Test
    public void testTCPServerSocket() throws IOException {
        TCPServerSocket serverSocket = new TCPServerSocket();
        serverSocket.open();
    }

    @Test
    public void testTCPSocket() throws IOException{
        TCPSocket socket = new TCPSocket();
        socket.request("1234");
    }
}