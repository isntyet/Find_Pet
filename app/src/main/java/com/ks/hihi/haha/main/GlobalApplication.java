package com.ks.hihi.haha.main;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ks.hihi.haha.utill.LruBitmapCache;
import com.tsengvn.typekit.Typekit;

/**
 * Created by jo on 2017-05-07.
 */

public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumSquareR.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumSquareB.ttf"))
                .add("test", Typekit.createFromAsset(this, "NanumBrush.ttf"));


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(3);

            @Override
            public void putBitmap(String key, Bitmap value) {
                imageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return imageCache.get(key);
            }
        };

        imageLoader = new ImageLoader(requestQueue, imageCache);

    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /*
     singleton 애플리케이션 객체를 얻는다.
     @return singleton 애플리케이션 객체
     */

    public static GlobalApplication getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalArgumentException("this application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    /*
     어플리케이션 종료시 singleton 어플리케이션 객체 초기화 함.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    public ImageLoader getImageLoader() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache(cacheSize));
        }
        return this.imageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
}

