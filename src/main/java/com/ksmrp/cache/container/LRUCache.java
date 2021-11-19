package com.ksmrp.cache.container;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存容器
 * @param <K> 键
 * @param <V> 值
 */
public class LRUCache<K, V> {

    /**
     * 双向链表节点
     */
    class Node {
        K key;
        V val;
        Node prev;
        Node next;
    }

    /**
     * 缓存容量
     */
    private final int capacity;

    /**
     * 头节点指针
     */
    private Node first;

    /**
     * 尾结点指针
     */
    private Node last;

    /**
     * 缓存索引
     */
    private Map<K, Node> map;

    /**
     * 默认缓存大小为 10
     */
    public LRUCache() {
        this(10);
    }

    /**
     * 构造方法
     * @param capacity 容量
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
    }

    /**
     * 获取数据
     * @param key key
     * @return val
     */
    public V get(K key){
        Node node = map.get(key);
        if (node == null){
            return null;
        }
        System.out.println("Load from cache:" + last.key);
        moveToHead(node);
        return node.val;
    }

    /**
     * 增加缓存数据
     * @param key key
     * @param val val
     */
    public void put(K key, V val){
        Node node = map.get(key);
        if (node == null){
            // 不存在，则创建
            node = new Node();
            node.key = key;
            node.val = val;

            if (map.size() == capacity){
                removeLast();
            }
            addToHead(node);
            map.put(key, node);
        }else{
            // key已经存在，更新value
            node.val = val;
            moveToHead(node);
        }
    }

    /**
     * 插入新元素到链表头
     * @param node
     */
    private void addToHead(Node node) {
        if (map.isEmpty()){
            first = node;
            last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }
    }

    /**
     * 移出尾部元素（使用最少的元素）
     */
    private void removeLast() {
        System.out.println("Remove:" + last.key);
        map.remove(last.key);
        Node prevNode = last.prev;
        if (prevNode != null){
            prevNode.next = null;
            last = prevNode;
        }
    }

    /**
     * 将节点移到表头
     * @param node node
     */
    private void moveToHead(Node node) {
        // 如果本身就已经是头节点，不做处理
        if (node == first){
            return ;
            // 如果是尾节点
        }else if (node == last){
            node.prev.next = null;
            last = last.prev;
            // 如果是中间任意节点
        }else{
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = first;
        first.prev = node;
        first = node;
    }
}
