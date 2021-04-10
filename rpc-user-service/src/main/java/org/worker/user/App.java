package org.worker.user;

import org.worker.item.api.ItemService;

public class App {
    public static void main(String[] args) {
        ItemService itemService = RpcProxyClient.get(ItemService.class, "localhost", 8080);
        String apple = itemService.queryItem("apple");
        System.out.println(apple);
    }
}
