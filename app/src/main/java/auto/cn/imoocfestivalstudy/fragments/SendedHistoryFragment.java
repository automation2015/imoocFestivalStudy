package auto.cn.imoocfestivalstudy.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import auto.cn.imoocfestivalstudy.R;
import auto.cn.imoocfestivalstudy.beans.SendedMsg;
import auto.cn.imoocfestivalstudy.db.SmsProvider;
import auto.cn.imoocfestivalstudy.view.FlowLayout;

public class SendedHistoryFragment extends ListFragment {
    private static final int LOAD_ID=1;
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInflater=LayoutInflater.from(getActivity());
        initLoader();
        setupListAdatper();
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOAD_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
                CursorLoader loader=new CursorLoader(getActivity(),SmsProvider.URI_SMS_ALL,null,null,null,null);
                return loader;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if(loader.getId()==LOAD_ID){
                    mCursorAdapter.swapCursor(cursor);
                }

            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
               mCursorAdapter.swapCursor(null);
            }
        });

    }

    private void setupListAdatper() {
        mCursorAdapter=new CursorAdapter(getActivity(),null,false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mInflater.inflate(R.layout.item_sendedhistroy, parent, false);

                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tvMsg=view.findViewById(R.id.tv_msg);
                FlowLayout flContacts=view.findViewById(R.id.fl_contacts);
                TextView fes=view.findViewById(R.id.tv_fes);
                TextView date=view.findViewById(R.id.tv_date);

                tvMsg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
                fes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FES_NAME)));
                long dateVal = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUMN_DATE));
                date.setText(parseDate(dateVal));
                String names=cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAMES));
                if(TextUtils.isEmpty(names)){
                    return;
                }
                for (String name:names.split(":")) {
                    addTag(name, flContacts);
                }
            }
        };
        setListAdapter(mCursorAdapter);
    }
   DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String parseDate(long dateVal) {
        String date = df.format(dateVal);
        return date;
    }

    private void addTag(String name, FlowLayout fl){
        TextView tv=(TextView)mInflater.inflate(R.layout.tag,fl,false);
        tv.setText(name);
        fl.addView(tv);
    }

}
