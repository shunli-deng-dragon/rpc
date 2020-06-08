package com.lagou.pojo;

import java.io.Serializable;

public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 1147496058100401100L;

    private String id; //消息体ID
    private Object data; //方法执行返回值
    // 0=success -1=fail
    private int status; //执行状态

    public String getId() {
        return id;
    }

    public RpcResponse setId(String id) {
        this.id = id;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RpcResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public RpcResponse setStatus(int status) {
        this.status = status;
        return this;
    }
}
