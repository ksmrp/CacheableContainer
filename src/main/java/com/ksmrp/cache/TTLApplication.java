package com.ksmrp.cache;

import com.ksmrp.cache.container.TTLCache;

import java.util.concurrent.TimeUnit;

public class TTLApplication {

    public static void main(String[] args) throws InterruptedException {
        TTLCache<String, String> cache = new TTLCache<String,String>(10);
        cache.put("张三","zhangsan");
        cache.put("李四","lisi");
        cache.put("王五","wangwu");
        cache.put("赵六","zhaoliu");

        System.out.println("张三的英文名是：" + cache.get("张三"));
        System.out.println("李四的英文名是：" + cache.get("李四"));
        TimeUnit.SECONDS.sleep(10);
        System.out.println("王五的英文名是：" + cache.get("王五"));
        System.out.println("赵六的英文名是：" + cache.get("赵六"));

    }

}
