package com.lagou.handler;

import com.lagou.pojo.RpcRequest;
import com.lagou.pojo.RpcResponse;
import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 自定义的业务处理器
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //注意:  客户端将来发送请求的时候会传递一个参数:  UserService#sayHello#are you ok
         //1.判断当前的请求是否符合规则
        if(msg.toString().startsWith("UserService")){
            //2.如果符合规则,调用实现类货到一个result
            // UserServiceImpl service = new UserServiceImpl();
            //String result = service.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            //3.把调用实现类的方法获得的结果写到客户端
            // ctx.writeAndFlush(result);
            RpcRequest request = (RpcRequest) msg;
            //返回的数据结构
            RpcResponse response = new RpcResponse();
            response.setId(request.getRequestId()); //返回ID与request对应
            try {
                //执行相应的方法
                Object result = invokeMethod(request);
                response.setData(result);
                response.setStatus(1);
            } catch (Exception e) {
                e.printStackTrace();
                response.setData(null);
                response.setStatus(-1);
            }
            //返回执行结果
            ctx.writeAndFlush(response);

        }


    }

    //根据请求，通过Java反射的方式执行相应的方法
    private Object invokeMethod(RpcRequest request) throws Exception{

        String className = request.getClassName();
        // 根据暴露的接口，找到实现类
        // Class<?> clazz = ScanUtil.interfaceClassMap.get(className);

        Class<?> clazz = UserServiceImpl.class;

        //找到要执行的方法
        Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
        //执行
        Object result = method.invoke(clazz.newInstance(), request.getParameters());
        return result;
    }
}
