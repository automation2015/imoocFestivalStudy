package auto.cn.imoocfestivalstudy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import auto.cn.imoocfestivalstudy.beans.SendedMsg;

public class SmsDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="sms.db";
    private static final int DB_VERSION=1;
    private SmsDbOpenHelper( Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }
    private static SmsDbOpenHelper mInstance;
    public static SmsDbOpenHelper getmInstance(Context context){
        if(mInstance==null){
            synchronized (SmsDbOpenHelper.class){
                if (mInstance==null){
                    mInstance=new SmsDbOpenHelper(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+SendedMsg.TABLE_NAME +"("+SendedMsg.COLUMN_DATE+" integer ,"
                +SendedMsg.COLUMN_FES_NAME+" text ,"+
                SendedMsg.COLUMN_MSG+" text ,"+
                SendedMsg.COLUMN_NAMES+" text ,"+
                SendedMsg.COLUMN_NAMES+" text "+
                ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
