package com.worker.item;

import org.worker.item.ItemRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcProcessHandler implements Runnable {
    private Object service;
    private Socket socket;

    public RpcProcessHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            ItemRequest request = (ItemRequest) objectInputStream.readObject();
            Object result = invoke(request);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            if(null != objectInputStream) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            if(null != socket) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    private Object invoke(ItemRequest request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String className = request.getClassName();
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getMethod(request.getMethodName(), request.getTypes());
        return method.invoke(service, request.getArgs());
    }
}
