package com.group2.foodie.util;

import com.google.android.gms.tasks.Task;

public class AwaitHelper {
    public static synchronized <T> T await(Task<T> task) throws Exception
    {
        T object;
        while (true)
        {
            if (task.isComplete())
            {
                object = task.getResult();
                break;
            }
        }
        return object;
    }
}
