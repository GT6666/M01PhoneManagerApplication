package com.cskaoyan.zhao.a04mobilemanager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cskaoyan.zhao.a04mobilemanager.bean.ProcessInfo;
import com.cskaoyan.zhao.a04mobilemanager.utils.ProcessManagerUtils;

import java.util.List;

/**
 * Created by zhao on 2016/8/13.
 */
public class MyProcessTest extends AndroidTestCase {


    public void testGetPnum(){

        int processNumber = ProcessManagerUtils.getProcessNumber(getContext());
        Log.i("TAG,","pnumber="+processNumber);

    }

    public void testGetRam(){


        long availRam = ProcessManagerUtils.getAvailRam(getContext());
        long totalRam = ProcessManagerUtils.getTotalRam(getContext());

        Log.i("TAG,","availRam="+availRam+" totalRam="+totalRam);

    }


    public void testGetAllProc(){


        List<ProcessInfo> allProcessInfo = ProcessManagerUtils.getAllProcessInfo(getContext());

        for (ProcessInfo p: allProcessInfo) {
            Log.i("test",p.toString());
        }
    }

}
