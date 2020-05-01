package com.example.smsrc.permissions.interfaces;

public interface Handler {
    Handler next();
    void handle();
    void setNext(Handler handler);
}
