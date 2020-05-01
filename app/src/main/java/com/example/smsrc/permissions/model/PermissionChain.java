package com.example.smsrc.permissions.model;

import com.example.smsrc.permissions.interfaces.Handler;

public class PermissionChain {

    private Handler handler;

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    public void start(){
        this.handler.handle();
    }

    public void next(){
        this.handler = this.handler.next();
        if(this.handler == null)
            return;
        this.handler.handle();
    }
}
