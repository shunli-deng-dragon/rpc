package com.lagou.boot;

import com.lagou.service.UserServiceImpl;

public class ServerBoot {

    public static void main(String[] args) throws InterruptedException {
        //启动服务器
        UserServiceImpl.startServer("127.0.0.1",8999);
    }
}
