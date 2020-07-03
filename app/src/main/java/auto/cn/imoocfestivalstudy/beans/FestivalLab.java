package auto.cn.imoocfestivalstudy.beans;

import java.util.ArrayList;
import java.util.List;

public class FestivalLab {
    private List<FestivalBean> mFestivals=new ArrayList<>();
    private List<MsgBean> mMsgs=new ArrayList<>();
    private static FestivalLab mInstance;
    private FestivalLab(){
        mFestivals.add(new FestivalBean(1,"春节"));
        mFestivals.add(new FestivalBean(2,"元宵节"));
        mFestivals.add(new FestivalBean(3,"清明节"));
        mFestivals.add(new FestivalBean(4,"劳动节"));
        mFestivals.add(new FestivalBean(5,"端午节"));
        mFestivals.add(new FestivalBean(6,"国庆节"));
        mFestivals.add(new FestivalBean(7,"儿童节"));
        mFestivals.add(new FestivalBean(8,"元旦"));

        mMsgs.add(new MsgBean(1,1,"春节，新年快乐"));
        mMsgs.add(new MsgBean(2,1,"春节，万事如意"));
        mMsgs.add(new MsgBean(3,1,"春节，鼠年大吉"));
        mMsgs.add(new MsgBean(4,1,"春节，阖家幸福"));
        mMsgs.add(new MsgBean(1,7,"儿童节，节日快乐"));
        mMsgs.add(new MsgBean(2,7,"儿童节，幸福一生"));
        mMsgs.add(new MsgBean(3,7,"儿童节，快乐成长"));
        mMsgs.add(new MsgBean(4,7,"儿童节，万事顺利"));
    }
    //返回所有的数据
    public List<FestivalBean> getFestivals(){
        return new ArrayList<FestivalBean>(mFestivals);
    }
    //根据节日id返回数据
    public FestivalBean getFestivalById(int fesId){
        for (FestivalBean festival :mFestivals) {
            if(festival.getId()==fesId){
                return  festival;
            }
        }
        return null;
    }
    //根据节日返回所有的节日短信
    public List<MsgBean> getMsgByFesId(int fesId){
        List<MsgBean> msgs=new ArrayList<>();
        for (MsgBean msg:mMsgs) {
            if(msg.getFesId()==fesId){
                msgs.add(msg);
            }
        }
        return msgs;
    }
    //根据节日返回特定id的节日短信
  public MsgBean getMsgByMsgId(int msgId){
      for (MsgBean msg:mMsgs) {
          if(msg.getId()==msgId){
              return msg;
          }
      }
      return null;
  }

    public static FestivalLab getInstance(){
        if(mInstance==null){
            synchronized (FestivalLab.class){
                if(mInstance==null){
                    mInstance=new FestivalLab();

                }
            }
        }
        return  mInstance;
    }
}
