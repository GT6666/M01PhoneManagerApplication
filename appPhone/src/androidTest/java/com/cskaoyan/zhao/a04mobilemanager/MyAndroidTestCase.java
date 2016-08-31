package com.cskaoyan.zhao.a04mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cskaoyan.zhao.a04mobilemanager.dao.MyAppLockDao;

/**
 * Created by zhao on 2016/8/12.
 */
public class MyAndroidTestCase extends AndroidTestCase {

    public void testPrintLog(){
        Log.i("tag","this is log");
    }

    public void testAddLockApp(){

        MyAppLockDao myAppLockDao = new MyAppLockDao(getContext());
        long l = myAppLockDao.addLockedApp( "com.cskaoyan.demoapp");
        assertTrue( l!=-1);

    }

    public void testRemoveLockApp(){

        MyAppLockDao myAppLockDao = new MyAppLockDao(getContext());
        int l = myAppLockDao.removeLockedApp( "com.cskaoyan.demoapp");
        assertTrue( l==4);

    }


    public void testIsLockApp(){

        MyAppLockDao myAppLockDao = new MyAppLockDao(getContext());
        boolean locked = myAppLockDao.queryAppIsLocked( "com.example.android.apis");
        assertTrue( locked );

    }
}
