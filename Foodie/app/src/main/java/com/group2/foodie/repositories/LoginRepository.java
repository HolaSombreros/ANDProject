package com.group2.foodie.repositories;

public class LoginRepository {

    private static LoginRepository instance;
    private static Object lock = new Object();

    public static LoginRepository getInstance(){
        if(instance ==null) {
            synchronized (lock) {
                if(instance == null)
                    instance = new LoginRepository();
            }
        }
        return instance;
    }
}
