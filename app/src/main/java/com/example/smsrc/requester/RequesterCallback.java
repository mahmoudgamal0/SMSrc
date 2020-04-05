package com.example.smsrc.requester;

public abstract class RequesterCallback {
    public abstract void onSuccess(Object[] args);

    public void onFail(Object[] args){

    }
}
