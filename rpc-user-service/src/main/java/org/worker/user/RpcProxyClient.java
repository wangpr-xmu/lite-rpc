package org.worker.user;

import java.lang.reflect.Proxy;

public class RpcProxyClient {
    public static <T> T get(Class<T> clazz, String ip, int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new RemoteServiceHandler(ip, port));
    }
}
