package asus.com.bwie.jjyk1.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import asus.com.bwie.jjyk1.BaseApis;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    public static RetrofitUtils retrofitUtils;
    public static String BASE_URL="http://www.zhaoapi.cn/product/";
    private final BaseApis baseApis;

    public static RetrofitUtils getRetrofitUtils() {
        if (retrofitUtils==null){
            synchronized (RetrofitUtils.class){
                if (null==retrofitUtils){
                    retrofitUtils=new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }


    public RetrofitUtils(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mClient=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        baseApis = retrofit.create(BaseApis.class);


    }
    public void get(String urlData,HttpListener listener){

        baseApis.get(urlData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObsetver(listener));
    }
    public void post(String urlData, Map<String,String> map, HttpListener listener){

        if (map==null){
            map=new HashMap<>();
        }
        baseApis.post(urlData,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObsetver(listener));
    }
    public void put(String urlData, Map<String,String> map, HttpListener listener){

        baseApis.put(urlData,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObsetver(listener));
    }
    public void delete(String urlData, Map<String,String> map, HttpListener listener){

        baseApis.delete(urlData,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObsetver(listener));
    }
    public Observer getObsetver(final HttpListener listener){
         Observer observer= new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            e.printStackTrace();
            if (listener!=null){
                listener.onFail(e.getMessage());
            }
            }

             @Override
             public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    if (listener!=null){
                        listener.onSuccess(string);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (listener!=null){
                        listener.onFail(e.getMessage());
                    }
                }

             }
        };
        return observer;
    };





    public HttpListener listener;

    public void setListener(HttpListener httplistener) {
        this. listener = httplistener;
    }

    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String error);
    }


}
