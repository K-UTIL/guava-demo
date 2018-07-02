package caches;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CacheIndex {

    /**
     * more efficient than many individual lookups
     *
     * @throws ExecutionException
     */
    @Test
    public void testCacheIndex() throws ExecutionException {
        LoadingCache<String, String> stringStringLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(new CacheLoader<String, String>() {
                    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

                    @Override
                    public String load(String key) throws Exception {
                        return key + simpleDateFormat.format(new Date());
                    }

                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                        HashMap<String, String> stringStringHashMap = Maps.newHashMap();
                        keys.forEach(e -> {
                            try {
                                stringStringHashMap.put(e, load(e));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        });
                        return stringStringHashMap;
                    }
                });
        String newDate = stringStringLoadingCache.get("newDate");
        System.out.println(newDate);

        //testCallable
        System.out.println(stringStringLoadingCache.get("bilibili", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "NONE";
            }
        }));

//        stringStringLoadingCache.put();
        stringStringLoadingCache.asMap().forEach((k, v) -> System.out.printf("key : %s ; value : %s \n", k, v));

        //test asMap
        stringStringLoadingCache.asMap().putIfAbsent("asMap", "yes");
        System.out.println(stringStringLoadingCache.get("asMap"));
    }


    @Test
    public void testEviction() {
        System.out.println("ss");
    }

    //接近上限时
    @Test
    public void testSizeBaseEviction() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(5)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key + new Date();
                    }
                });
    }

    /**
     * @see CacheBuilder#expireAfterWrite
     * @see CacheBuilder#expireAfterAccess
     */
    @Test
    public void testTimeBaseEviction() {
        Cache<String, String> build = CacheBuilder.newBuilder()
                .ticker(new Ticker() {
                    @Override
                    public long read() {
                        return 0;
                    }
                })
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Test
    public void testReferenceBaseEviction() throws InterruptedException, ExecutionException {
        Cache<String, test> build = CacheBuilder.newBuilder()
                .weakValues()
                .build();
        {
            add(build);
        }
        new Thread(() -> {
            build.put("ddd", new test("ddd"));
            System.gc();
        }).start();
        new Thread(() -> System.gc()).start();
        System.gc();
        Thread.sleep(2000);

        System.out.println(build.get("ddd", () -> new test("new")));
    }

    public void add(Cache cache) {
        cache.put("ddd", new test("ddd"));
    }

    /**
     * @see com.google.common.base.Stopwatch
     */
    @Test
    public void testStopWatch() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 1; i < 1000; i++) {

        }
        Duration elapsed = stopwatch.elapsed();
        System.out.println(elapsed.getNano());
    }

    //同步监听
    @Test
    public void testRemovalListeners (){
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100)
                .removalListener((RemovalListener<String, String>) notification -> System.out.printf("{key:%s,value:%s} has removed",notification.getKey(),notification.getValue())).build();
        cache.put("test","value");
        cache.invalidate("test");
    }

    @Test
    public void testRemovalListenerAsyn (){
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100)
                .removalListener(RemovalListeners.asynchronous(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {

                    }
                }, ));
    }


}

// TODO: 2018/7/2  
class TestTicker extends Ticker {


    @Override
    public long read() {
        return 0;
    }
}

class test{
    public test(String a) {
        this.a = a;
    }

    public String a;

    @Override
    public String toString() {
        return "test{" +
                "a='" + a + '\'' +
                '}';
    }
}