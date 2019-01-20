package asus.com.bwie.jjyk1.presenter;

import java.util.Map;

public interface Ipresenter {

    void onRequestGet(String urlData,Class clazz);
    void onRequestPost(String urlData, Map<String,String> map,Class clazz);
    void onRequestPut(String urlData, Map<String,String> map,Class clazz);
    void onRequestDelete(String urlData, Map<String,String> map,Class clazz);


}
