package auto.cn.imoocfestivalstudy;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.ColorLong;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

import auto.cn.imoocfestivalstudy.beans.FestivalBean;
import auto.cn.imoocfestivalstudy.beans.FestivalLab;
import auto.cn.imoocfestivalstudy.beans.MsgBean;
import auto.cn.imoocfestivalstudy.beans.SendedMsg;
import auto.cn.imoocfestivalstudy.biz.SmsBiz;
import auto.cn.imoocfestivalstudy.view.FlowLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AtySendMsg extends AppCompatActivity {

    public static final String KEY_FESID = "fesId";
    public static final String KEY_MSGID = "msgId";
    private static final int CODE_REQUEST = 1;
    @Bind(R.id.et_send_content)
    EditText etSendContent;
    @Bind(R.id.btn_add)
    Button btnAdd;
    @Bind(R.id.fl_send_contacts)
    FlowLayout flSendContacts;
    @Bind(R.id.fab_sendmsg)
    FloatingActionButton fabSendmsg;
    @Bind(R.id.fl_send_loading)
    FrameLayout flSendLoading;
    private int mFesId;
    private int mMsgId;
    private FestivalBean mFestival;
    private MsgBean mMsg;
    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();
    private LayoutInflater inflater;
    public static final String ACTION_SEND_MSG="ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG="ACTION_DELIVER_MSG";
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private SmsBiz smsBiz;
    private int mMsgSendCount;
    private int mTotalCount;
    //启动Activity并传递参数
    public static void toActivity(Context context, int fesId, int msgId) {
        Intent intent = new Intent(context, AtySendMsg.class);
        intent.putExtra(KEY_FESID, fesId);
        intent.putExtra(KEY_MSGID, msgId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_send_msg);
        ButterKnife.bind(this);
        initViews();
        initDatas();
        initEnents();
        initRecivers();
    }
    private void initViews() {
        //设置ProgressBar隐藏
        flSendLoading.setVisibility(View.GONE);
    }

    private void initDatas() {
        smsBiz=new SmsBiz(this);
        inflater=LayoutInflater.from(this);
        mFesId = getIntent().getIntExtra(KEY_FESID, -1);
        mMsgId = getIntent().getIntExtra(KEY_MSGID, -1);
        if (mMsgId != -1) {
            mMsg = FestivalLab.getInstance().getMsgByMsgId(mMsgId);
            etSendContent.setText(mMsg.getContent());
        }
        mFestival = FestivalLab.getInstance().getFestivalById(mFesId);
        setTitle(mFestival.getName());
    }

    private void initEnents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String number = getContactNumber(cursor);
                if (number != null) {
                    mContactNums.add(number);
                    mContactNames.add(contactName);
                    addTag(contactName);
                }
            }
        }
    }
    //获取联系人的电话号码
    private String getContactNumber(Cursor cursor) {
        String number = null;
        if (cursor != null) {
            int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (numberCount > 0) {
                int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                phoneCursor.moveToFirst();
                number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneCursor.close();
            }
        }
        cursor.close();
        return number;
    }
    //添加联系人
    private void addTag(String contactName) {
        TextView view=(TextView) inflater.inflate(R.layout.tag,flSendContacts,false);
        view.setText(contactName);
        flSendContacts.addView(view);
    }
    //初始化广播
    private void initRecivers() {
        Intent sendIntent=new Intent(ACTION_SEND_MSG);
        mSendPi=PendingIntent.getBroadcast(this,0,sendIntent,0);
        Intent deliverIntent=new Intent(ACTION_DELIVER_MSG);
        mDeliverPi=PendingIntent.getBroadcast(this,0,deliverIntent,0);
        //注册广播
        registerReceiver(mSendBroadcastReceiver=new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
           mMsgSendCount++;
           if(getResultCode()==RESULT_OK) {
               Log.e("tag", "短信发送成功了"+(mMsgSendCount+"/"+mTotalCount));
           }else{
               Log.e("tag", "短信发送失败了");
           }
           Toast.makeText(AtySendMsg.this,
                   (mMsgSendCount+"/"+mTotalCount)+"短信发送成功了",Toast.LENGTH_SHORT).show();

           if(mMsgSendCount==mTotalCount){
               finish();
           }
       }
   },new IntentFilter(ACTION_SEND_MSG));
   //注册广播
   registerReceiver(mDeliverBroadcastReceiver=new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
           Log.e("tag", "联系人已经成功接受到我们的短信");

       }
   },new IntentFilter(ACTION_DELIVER_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }





   //发送短信
    @OnClick({R.id.fab_sendmsg})
    public void sendMsg(View view){
        String msg = etSendContent.getText().toString();
        if(mContactNums.size()==0){
            Toast.makeText(AtySendMsg.this,"请先选择联系人",Toast.LENGTH_SHORT).show();
            return;
        }
        if(msg==null) {
            Toast.makeText(AtySendMsg.this,"短信内容不能为空",Toast.LENGTH_SHORT).show();
            return;

        }
        flSendLoading.setVisibility(View.VISIBLE);
        mTotalCount = smsBiz.sendMsg(mContactNums, buildSendedMsg(msg), mSendPi, mDeliverPi);
        mMsgSendCount=0;
    }

    private SendedMsg buildSendedMsg(String msg) {
        SendedMsg sendedMsg=new SendedMsg();
        sendedMsg.setMsg(msg);
        sendedMsg.setFestivalName(mFestival.getName());
        String names="";
        for(String name:mContactNames){
            names+=name+":";//使用":"分割
        }
        String numbers="";
        for(String number:mContactNums){
            numbers+=number+":";
        }
        sendedMsg.setNames(names.substring(0,names.length()-1));
        sendedMsg.setNumbers(numbers.substring(0,numbers.length()-1));
        return sendedMsg;
    }

}
