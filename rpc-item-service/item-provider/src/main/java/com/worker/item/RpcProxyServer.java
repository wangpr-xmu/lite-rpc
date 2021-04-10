package com.worker.item;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RpcProxyServer {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void publishService(Object service, int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            while(true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new RpcProcessHandler(service, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
