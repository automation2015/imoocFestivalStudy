package auto.cn.imoocfestivalstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import auto.cn.imoocfestivalstudy.beans.FestivalBean;
import auto.cn.imoocfestivalstudy.beans.FestivalLab;
import auto.cn.imoocfestivalstudy.beans.MsgBean;
import auto.cn.imoocfestivalstudy.view.FlowLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AtySendMsg extends AppCompatActivity {
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
public static final String  KEY_FESID="fesId";
    public static final String  KEY_MSGID="msgId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_send_msg);
        ButterKnife.bind(this);
        initViews();
        initDatas();
    }

    private void initViews() {
        flSendLoading.setVisibility(View.GONE);
    }

    private void initDatas() {
        mFesId=getIntent().getIntExtra(KEY_FESID,-1);
        mMsgId=getIntent().getIntExtra(KEY_MSGID,-1);
        if(mMsgId!=-1){
            mMsg=FestivalLab.getInstance().getMsgByMsgId(mMsgId);
            etSendContent.setText(mMsg.getContent());
        }
        mFestival=FestivalLab.getInstance().getFestivalById(mFesId);
        setTitle(mFestival.getName());
    }

    public static void toActivity(Context context,int fesId,int msgId){
        Intent intent=new Intent(context,AtySendMsg.class);
        intent.putExtra(KEY_FESID,fesId);
        intent.putExtra(KEY_MSGID,msgId);
        context.startActivity(intent);
    }
}
