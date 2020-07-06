package auto.cn.imoocfestivalstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import auto.cn.imoocfestivalstudy.fragments.FestivalCatigory;
import auto.cn.imoocfestivalstudy.fragments.SendedHistoryFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_tablayout)
    TabLayout mainTablayout;
    @Bind(R.id.main_vp)
    ViewPager mainVp;
    String[] mTitles=new String[]{"计划下载","计划查询"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
               if(position==1) return new SendedHistoryFragment();
                return new FestivalCatigory();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mainTablayout.setupWithViewPager(mainVp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
