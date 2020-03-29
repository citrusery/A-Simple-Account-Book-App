package com.example.tryfirst;

public class LoginState {
    public static boolean login_state = false;

    public static void setLogin_state(boolean state){
        login_state = state;
    }
    public static boolean getLoginState(){
        return login_state;
    }
}
