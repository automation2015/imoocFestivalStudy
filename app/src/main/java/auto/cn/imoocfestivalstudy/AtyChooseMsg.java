package auto.cn.imoocfestivalstudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import auto.cn.imoocfestivalstudy.beans.FestivalLab;
import auto.cn.imoocfestivalstudy.beans.MsgBean;
import auto.cn.imoocfestivalstudy.fragments.FestivalCatigory;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AtyChooseMsg extends Activity {

    @Bind(R.id.lv_choosemsg)
    ListView lvChoosemsg;
    @Bind(R.id.fab_choosemsg)
    FloatingActionButton fabChoosemsg;
    private ArrayAdapter<MsgBean> mAdapter;
    private int mFesId;
    private List<MsgBean> mDatas;
    private int fesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_choose_msg);
        ButterKnife.bind(this);
        mFesId = getIntent().getIntExtra(FestivalCatigory.ID_FESTIVAL,-1);
        mDatas=FestivalLab.getInstance().getMsgByFesId(mFesId);
        setTitle(FestivalLab.getInstance().getFestivalById(mFesId).getName());
        fabChoosemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
AtySendMsg.toActivity(AtyChooseMsg.this,mFesId,-1);
            }
        });
        mAdapter=new ArrayAdapter<MsgBean>(this,-1,mDatas){

            @Override
            public View getView(final int position, View convertView,ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                    convertView=LayoutInflater.from(AtyChooseMsg.this).inflate(R.layout.item_msg,parent,false);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
                }else{
                    holder=(ViewHolder)convertView.getTag();
                }
                MsgBean msg = getItem(position);
                holder.tvMsgContent.setText("  "+msg.getContent());
                holder.btnToSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AtySendMsg.toActivity(AtyChooseMsg.this,mFesId,getItem(position).getId());
                    }
                });
                return  convertView;
            }
        };
        lvChoosemsg.setAdapter(mAdapter);
    }
    class ViewHolder{
        TextView tvMsgContent;
        Button btnToSend;
        ViewHolder(View view){
tvMsgContent=view.findViewById(R.id.tv_item_content);
btnToSend=view.findViewById(R.id.btn_item_tosend);
        }

    }
}
