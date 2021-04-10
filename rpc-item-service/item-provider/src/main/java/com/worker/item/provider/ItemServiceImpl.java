package com.worker.item.provider;

import org.worker.item.api.ItemService;

public class ItemServiceImpl implements ItemService {
    @Override
    public String queryItem(String name) {
        System.out.println("服务端查找商品：" + name);
        return "item: " + name;
    }
}
