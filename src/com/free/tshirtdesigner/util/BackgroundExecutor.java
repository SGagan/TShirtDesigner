package com.free.tshirtdesigner.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User: Dell
 * Date: 3/6/14
 * Time: 10:50 PM
 */
public class BackgroundExecutor
{
    private static Executor executor = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable)
    {
        executor.execute(runnable);
    }

    public static void setExecutor(Executor executor)
    {
        BackgroundExecutor.executor = executor;
    }

}
