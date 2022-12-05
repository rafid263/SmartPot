package com.s_rafid.smartpot.Controller;

public interface ILoginController {
    void OnLogin(String email,String Password);
    boolean loginStatus();
    void OnReset(String email);
}