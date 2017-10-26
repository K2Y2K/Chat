// IMyAidlInterface.aidl
package com.example.lidongxue.chataidl;

// Declare any non-default types here with import statements
import com.example.lidongxue.chataidl.User;
import com.example.lidongxue.chataidl.Msg;
import com.example.lidongxue.chataidl.MsgList;
import com.example.lidongxue.chataidl.Contact;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
            //所有的返回值前都不需要加任何东西，不管是什么数据类型
            List<User> getUserList();
            User getUser(String username);
             //传参时除了Java基本类型以及String，CharSequence之外的类型
               //都需要在前面加上定向tag，具体加什么量需而定
            void addUser(in User user);

            List<Msg> getMsgList(String msg_list_id);
            List<MsgList> getMsgsList(String userid);
            List<Contact> getContactList(String userid);
}
