package com.worker.item;

import com.worker.item.provider.ItemServiceImpl;
import org.worker.item.api.ItemService;

public class BootStrap {
    public static void main(String[] args) {
        ItemService itemService = new ItemServiceImpl();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publishService(itemService, 8080);
    }
}
