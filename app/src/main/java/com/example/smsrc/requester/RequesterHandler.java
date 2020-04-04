package com.example.smsrc.requester;

public class RequesterHandler {

    private static RequesterHandler handler;
    private RequesterCallback registeredCallback;
    private Object[] registeredArgs;

    private RequesterHandler(){ }

    public static RequesterHandler getHandler(){
        if(handler == null) {
            handler = new RequesterHandler();
        }

        return handler;
    }

    public void registerReceiver(RequesterCallback callback, Object[] args){
        this.registeredCallback = callback;
        this.registeredArgs = args;
    }

    public void callbackSuccess(){
        this.registeredCallback.onSuccess(this.registeredArgs);
    }

    public void callbackFail(){
        this.registeredCallback.onFail(this.registeredArgs);
    }
}
