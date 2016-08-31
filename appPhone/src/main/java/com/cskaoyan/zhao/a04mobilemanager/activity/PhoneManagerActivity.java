package com.cskaoyan.zhao.a04mobilemanager.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.zhao.a04mobilemanager.R;
import com.cskaoyan.zhao.a04mobilemanager.bean.BlackNumberInfo;
import com.cskaoyan.zhao.a04mobilemanager.dao.MyBlackNumberDao;

import java.util.List;

public class PhoneManagerActivity extends AppCompatActivity {




    List<BlackNumberInfo> allBlackNumber;
    MyBlackNumberDao dao;
    private MyBlackNumberAdapter myBlackNumberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_manager);
        getSupportActionBar().hide();


        dao = new MyBlackNumberDao(this);
        allBlackNumber = dao.getAllBlackNumber();

        ListView lv_phonemanager_blacklist = (ListView) findViewById(R.id.lv_phonemanager_blacklist);

        myBlackNumberAdapter = new MyBlackNumberAdapter();
        lv_phonemanager_blacklist.setAdapter(myBlackNumberAdapter);


        //询问用户是否要删除掉当前的这黑名单，是 就删掉
        lv_phonemanager_blacklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final BlackNumberInfo blackNumberInfo = allBlackNumber.get(position);
                final String number = blackNumberInfo.number;

                new AlertDialog.Builder(PhoneManagerActivity.this)
                        .setTitle("警告")
                        .setMessage("确认删除"+number+"吗？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //把当前的号码从数据库删除

                                dao.delelteNumber(number);
                                allBlackNumber.remove(blackNumberInfo);
                                myBlackNumberAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

            }
        });

        //长按item的时候， 去修改拦截模式。 让用户去修改listview 当前的这个item值
        lv_phonemanager_blacklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final    BlackNumberInfo blackNumberInfo = allBlackNumber.get(position);

               final  int rememberPostion = position;


                String[] choices =new String[]{"拦截电话","拦截短信","拦截全部"};
                new AlertDialog.Builder(PhoneManagerActivity.this)
                        .setTitle("修改拦截模式")
                        .setSingleChoiceItems(choices,  blackNumberInfo.mode-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //去修改拦截模式
                                dao.updateMode(blackNumberInfo.number,which+1);

                                //同时去刷新UI
                                blackNumberInfo.mode=which+1;
                                //allBlackNumber.set(rememberPostion,)
                                //因为是使用引用去修改了blackNumberInfo里的mode，实际上list里面的当前item的数据已经修改了。
                                myBlackNumberAdapter.notifyDataSetChanged();

                                dialog.dismiss();
                            }
                        })
                        .show();

                return true;
            }
        });

    }

    public void addBlackNumber(View v){

        View dialogView = View.inflate(this, R.layout.dialog_addblacknumer, null);

        Button bt_dialogblacknumber_confirm = (Button) dialogView.findViewById(R.id.bt_dialogblacknumber_confirm);
        Button bt_dialogblacknumber_cancel = (Button) dialogView.findViewById(R.id.bt_dialogblacknumber_cancel);

        final EditText et_dialogaddnumber_number = (EditText) dialogView.findViewById(R.id.et_dialogaddnumber_number);

        final  RadioGroup rg_dialogaddnumber_mode = (RadioGroup) dialogView.findViewById(R.id.rg_dialogaddnumber_mode);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView)
                ;

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();



        bt_dialogblacknumber_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        bt_dialogblacknumber_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //拿到用户填写的数据，
                String number = et_dialogaddnumber_number.getText().toString();
                //正则判断号码的格式

                int checkedRadioButtonId = rg_dialogaddnumber_mode.getCheckedRadioButtonId();
                int mode=-1;
                switch (checkedRadioButtonId){
                    case R.id.rb_dialogaddnumber_phone:
                        mode=1;
                        break;
                    case R.id.rb_dialogaddnumber_sms:
                        mode=2;
                        break;
                    case R.id.rb_dialogaddnumber_all:
                        mode=3;
                        break;
                }
                if (mode==-1){
                    Toast.makeText(PhoneManagerActivity.this,"请选择拦截模式",Toast.LENGTH_SHORT).show();

                }else{
                    // 插入到我们的数据库
                    dao.insertNumber(number,mode);

                    allBlackNumber.add(new BlackNumberInfo(number,mode));
                    myBlackNumberAdapter.notifyDataSetChanged();

                    alertDialog.dismiss();
                 }



            }
        });
    }

    class MyBlackNumberAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return allBlackNumber.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BlackNumberInfo blackNumberInfo = allBlackNumber.get(position);


            View view = View.inflate(PhoneManagerActivity.this, R.layout.item_blacknumer, null);

            TextView tv_blacknumberlist_numer = (TextView) view.findViewById(R.id.tv_blacknumberlist_numer);
            TextView tv_blacknumberlist_mode= (TextView) view.findViewById(R.id.tv_blacknumberlist_mode);

            tv_blacknumberlist_numer.setText(blackNumberInfo.number);

            String modeString ="";
            switch (blackNumberInfo.mode){
                case 1:
                    modeString="拦截电话";
                    break;
                case 2:
                    modeString="拦截短信";;
                    break;
                case 3:
                    modeString="拦截全部";;
                    break;
            }

            tv_blacknumberlist_mode.setText(modeString);



            return view;
        }
    }
}
