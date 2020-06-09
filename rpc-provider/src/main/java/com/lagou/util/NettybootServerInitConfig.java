package com.lagou.util;

import com.lagou.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *  netty configuration for startup
 */
@Component
public class NettybootServerInitConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(event.getApplicationContext().getParent() == null){
            try {
                // start netty server
                userServiceImpl.startServer("127.0.0.1",8999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
