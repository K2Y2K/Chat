package com.example.lidongxue.chat.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lidongxue.chat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidongxue on 17-10-20.
 */

public class SearchPhoneContactActivity extends BaseActivity {
    @BindView(R.id.search_phoneuser)
    SearchView msearch_phoneuser;
    @BindView(R.id.search_phonematch)
    ListView msearch_phonematch;

    final int PICK_CONTACT=0;
    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone);
        ButterKnife.bind(this);
        initToolBar(true,"搜索手机联系人");


        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        msearch_phonematch.setAdapter(adapter);
             //设置ListView启动过滤
        msearch_phonematch.setTextFilterEnabled(true);
        //判断是否获取了读联系人的额权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }

        //设置sv默认是否自动缩小为图标
        msearch_phoneuser.setIconifiedByDefault(false);
        //设置显示搜索提交按钮
        msearch_phoneuser.setSubmitButtonEnabled(true);

        //设置sv默认显示的提示文本
        msearch_phoneuser.setQueryHint("搜索");

        //为该sv设置监听
        msearch_phoneuser.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            //用户点击输入时触发
            @Override
            public boolean onQueryTextSubmit(String query) {

                System.out.println("选择的是---" + query);
                // 一般实际应用中可在这里做逻辑处理

                return false;
            }

            // 用户输入时触发
            @Override
            public boolean onQueryTextChange(String newText) {

                //如果newText不为0
                if (newText.isEmpty()) {
                    //
                    msearch_phonematch.clearTextFilter();

                } else {
                    //使用用户输入内容对lv进行过滤
                    msearch_phonematch.setFilterText(newText);
                }
                return false;
            }
        });

    }


    /*
    bug出在android6之后获取通讯录的权限和之前的不一样，需要做申请权限判断，该问题已解决
     */
    private  void readContacts(){
        Cursor cursor=null;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,
                    null,null);
            if (cursor!=null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor!=null){
                cursor.close();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }


}
