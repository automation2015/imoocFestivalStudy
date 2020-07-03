package auto.cn.imoocfestivalstudy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import auto.cn.imoocfestivalstudy.AtyChooseMsg;
import auto.cn.imoocfestivalstudy.R;
import auto.cn.imoocfestivalstudy.beans.FestivalBean;
import auto.cn.imoocfestivalstudy.beans.FestivalLab;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FestivalCatigory extends Fragment {
    @Bind(R.id.gv_fragment_festival)
    GridView gvFragmentFestival;
private ArrayAdapter<FestivalBean> mAdapter;
private List<FestivalBean> mDatas;
public static final String ID_FESTIVAL ="fes_id";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_festival, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mDatas=FestivalLab.getInstance().getFestivals();
        mAdapter=new ArrayAdapter<FestivalBean>(getActivity(),-1,mDatas){

            @Override
            public View getView(int position,  View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                convertView=LayoutInflater.from(getActivity()).inflate(R.layout.item_fragment_festival_gv,parent,false);
                    holder=new ViewHolder(convertView);
                    convertView.setTag(holder);
                }else {
                   holder= (ViewHolder) convertView.getTag();
                }
                FestivalBean festivalBean = mDatas.get(position);
                holder.tvName.setText(festivalBean.getName());
                return convertView;
            }
        };
        gvFragmentFestival.setAdapter(mAdapter);
        gvFragmentFestival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int fesId = mAdapter.getItem(position).getId();
                Intent intent=new Intent(getActivity(),AtyChooseMsg.class);
                intent.putExtra(ID_FESTIVAL,fesId);
                startActivity(intent);

            }
        });

    }
    class ViewHolder{
        TextView tvName;
        ViewHolder(View view){
            tvName=view.findViewById(R.id.tv_item_festival_gv)  ;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
