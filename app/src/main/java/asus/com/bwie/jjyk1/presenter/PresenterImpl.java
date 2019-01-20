package asus.com.bwie.jjyk1.presenter;

import java.util.Map;

import asus.com.bwie.jjyk1.callback.MyCallBack;
import asus.com.bwie.jjyk1.model.ModelImpl;
import asus.com.bwie.jjyk1.view.IView;

public class PresenterImpl implements Ipresenter {

    private ModelImpl model;
    private IView iView;

    public PresenterImpl(IView iView) {
        this.model = new ModelImpl();
        this.iView = iView;
    }

    @Override
    public void onRequestGet(String urlData, Class clazz) {
            model.onRequestGetData(urlData, clazz, new MyCallBack() {
                @Override
                public void onSuccess(Object data) {
                    iView.onSuccessData(data);
                }

                @Override
                public void onFail(Exception e) {
                    iView.onFailData(e);
                }
            });
    }

    @Override
    public void onRequestPost(String urlData, Map<String, String> map, Class clazz) {
        model.onRequestPostData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iView.onFailData(e);
            }
        });

    }

    @Override
    public void onRequestPut(String urlData, Map<String, String> map, Class clazz) {
        model.onRequestPutData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iView.onFailData(e);
            }
        });
    }

    @Override
    public void onRequestDelete(String urlData, Map<String, String> map, Class clazz) {
        model.onRequestDeleteData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iView.onFailData(e);
            }
        });
    }
    public void detach(){
        iView=null;
        model=null;

    }
}

