package com.ksmrp.cache.container;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 可设置过期时间的缓存容器
 * @param <K> key
 * @param <V> val
 */
public class TTLCache<K,V> {

    /**
     * 存放数据的HashMap
     */
    private Map<K,V> dataMap = new HashMap<>();

    /**
     * 记录过期时间的HashMap
     */
    private Map<K, Date> ttlMap = new HashMap<>();

    /**
     * 超时时间(秒)
     */
    int ttl;

    /**
     * 构造方法
     * @param ttl 超时时间(秒)
     */
    public TTLCache(int ttl) {
        this.ttl = ttl;
    }

    /**
     * 获取数据
     * @param key key
     * @return val
     */
    public V get(K key){
        Date expTime = ttlMap.get(key);
        V v = dataMap.get(key);
        // 判断数据是否有缓存
        if (v != null){
            // 判断缓存是否有效
            if (expTime != null && expTime.after(new Date())){
                System.out.println("load from cache: " + key);
                return v;
            }else{
                // 缓存失效，删除数据
                System.out.println("remove exp cache:" + key);
                dataMap.remove(key);
                ttlMap.remove(key);
            }
        }
        return null;
    }

    /**
     * 放入数据
     * @param key key
     * @param val val
     */
    public void put(K key, V val){
        dataMap.put(key,val);
        ttlMap.put(key,calcExpTime());
    }

    /**
     * 计算过期时间
     * @return 过期时间
     */
    private Date calcExpTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, ttl);
        return calendar.getTime();
    }

}
