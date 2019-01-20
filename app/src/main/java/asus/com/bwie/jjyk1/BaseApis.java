package asus.com.bwie.jjyk1;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis<T> {
    @GET
    Observable<ResponseBody> get(@Url String urlData);
    @POST
    Observable<ResponseBody> post(@Url String urlData, @QueryMap Map<String,String> map);
    @DELETE
    Observable<ResponseBody> delete(@Url String urlData,@QueryMap Map<String,String> map);
    @PUT
    Observable<ResponseBody> put(@Url String urlData,@QueryMap Map<String,String> map);



}
