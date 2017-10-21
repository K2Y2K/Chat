package com.example.lidongxue.chat.database.cache;

import com.example.lidongxue.chat.app.AppConst;
import com.example.lidongxue.chat.app.base.BaseApp;
import com.example.lidongxue.chat.utils.SPUtils;

/**
 * Created by lidongxue on 17-10-19.
 * 用户缓存
 */

public class UserCache {
    public static String getId() {
        return SPUtils.getInstance(BaseApp.getContext()).getString(AppConst.User.ID, "");
    }
    public static String getName() {
        return SPUtils.getInstance(BaseApp.getContext()).getString(AppConst.User.NAME, "");
    }
    public static String getPSD() {
        return SPUtils.getInstance(BaseApp.getContext()).getString(AppConst.User.PSD, "");
    }

    /*public static String getPhone() {
        return SPUtils.getInstance(BaseApp.getContext()).getString(AppConst.User.PHONE, "");
    }

    public static String getToken() {
        return SPUtils.getInstance(BaseApp.getContext()).getString(AppConst.User.TOKEN, "");
    }*/
    public static void save( String name, String psd) {
       // SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.ID, id);
        SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.NAME,name);
        SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.PSD,psd);
    }

    public static void clear() {
       // SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.ID);
        SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.NAME);
        SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.PSD);
    }

    /*public static void save(String id, String account, String token) {
        SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.ID, id);
        SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.NAME,account);
       // SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.PHONE, account);
        SPUtils.getInstance(BaseApp.getContext()).putString(AppConst.User.TOKEN, token);
    }*/

    /*public static void clear() {
        SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.ID);
        SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.NAME);
        //SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.PHONE);
        SPUtils.getInstance(BaseApp.getContext()).remove(AppConst.User.TOKEN);
        //DBManager.getInstance().deleteAllUserInfo();
    }*/
}
