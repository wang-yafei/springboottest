package com.example.demo.test.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CacheBuilderTest {


    private static void baseTest() throws  Exception{
        LoadingCache<String, String> build = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override public String load(String key) throws Exception {
                System.out.println("------加载中-----");
                Thread.sleep(1000);
                return key.concat("  :baseTest").concat("\r\n");
            }
        });
        System.out.println(build.get("好的"));
    }

    /**
     * 最后一次写入过期时间,定时过期
     * @throws Exception
     */
    public static void expireAfterWriteTest() throws Exception{

        LoadingCache<String, String> build = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MILLISECONDS)// 最后一次写入,移除时间
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("------加载中-----");
                        return key.concat("  :expireAfterWriteTest");
                    }
                });

        System.out.println(build.get("好的"));
        Thread.sleep(7);
        System.out.println(build.get("好的"));
        Thread.sleep(7);
        System.out.println(build.get("好的"));
    }

    /**
     * 最后一次访问多久后移除,只要一直有访问就不会移除
     * @throws Exception
     */
    public static void expireAfterAccessTest() throws  Exception{
        LoadingCache<String, String> build = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("------加载中-----");
                        return key.concat("  :expireAfterAccessTest");
                    }
                });
        System.out.println(build.get("好的"));
        Thread.sleep(8);
        System.out.println(build.get("好的"));
        Thread.sleep(8);
        System.out.println(build.get("好的"));
    }

    /**
     * 最大容量
     * @throws Exception
     */
    private static void maximumSizeTest() throws Exception{
        LoadingCache<Integer, String> build = CacheBuilder.newBuilder().maximumSize(2)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) {
                        System.out.println("------加载中-----");
                        return key + "  :maximumSizeTest";
                    }
                });

       check(build);
    }

    /**
     * 按照权重将数据添加到缓存,如果当前数据过大,将不入缓存
     */
    public static void maximumWeightTest(){
        LoadingCache<Integer, String> build = CacheBuilder.newBuilder().maximumWeight(55)
                .weigher((Integer key, String value) -> {
                    System.out.println("-----加载中-----");
                    System.out.println(key);
                    return key;
                }).build(new CacheLoader<Integer, String>() {
                    @Override public String load(Integer key) {
                        System.out.println("------加载中-----");
                        return key+"哈哈";
                    }
                });
        check(build);
    }

    private static void check(LoadingCache<Integer, String> build){
        IntStream.range(0, 100).forEach(i -> {
            try {
                System.out.println(build.get(i));
                System.out.println(build.size());
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }


    public static void main(String[] args) throws Exception {
        baseTest();
        //expireAfterWriteTest();
        //expireAfterAccessTest();
        //maximumSizeTest();
        //maximumWeightTest();
    }



}
