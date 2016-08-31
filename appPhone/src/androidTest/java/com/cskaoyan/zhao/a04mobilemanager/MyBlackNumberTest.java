package com.cskaoyan.zhao.a04mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cskaoyan.zhao.a04mobilemanager.bean.BlackNumberInfo;
import com.cskaoyan.zhao.a04mobilemanager.dao.MyBlackNumberDao;

import java.util.List;

/**
 * Created by zhao on 2016/8/16.
 */
public class MyBlackNumberTest extends AndroidTestCase {

    private static final String TAG = "MyBlackNumberTest";

    public void testInsert(){


        String phone ="13745677891";
        int mode=3;

        MyBlackNumberDao dao = new MyBlackNumberDao(getContext());
        long l = dao.insertNumber(phone, mode);

        assertTrue(l!=-1);

    }

    public void testQuery(){
        MyBlackNumberDao dao = new MyBlackNumberDao(getContext());

        List<BlackNumberInfo> allBlackNumber = dao.getAllBlackNumber();

        for (BlackNumberInfo info:allBlackNumber
             ) {

            Log.i(TAG,info.number+info.mode);
        }


    }

    public  void testDelete(){
        MyBlackNumberDao dao = new MyBlackNumberDao(getContext());

        int i = dao.delelteNumber("1234");

        assertEquals(1,i);

    }

    public  void testUpdate(){
        MyBlackNumberDao dao = new MyBlackNumberDao(getContext());

        int i = dao.updateMode("5566",3);

        assertEquals(1,i);

    }

    public void testQueryMOde(){

        MyBlackNumberDao dao = new MyBlackNumberDao(getContext());

        int i = dao.queryMode("5566");

        assertEquals(1,i);

    }
}
