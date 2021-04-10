package org.worker.user;

import org.worker.item.ItemRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteServiceHandler implements InvocationHandler {

    private String ip;
    private int port;

    public RemoteServiceHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcNetTransport transport = new RpcNetTransport(ip, port);
        ItemRequest request = new ItemRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArgs(args);
        request.setTypes(method.getParameterTypes());
        return transport.send(request);
    }
}
