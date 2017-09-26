package huwenpeng.project.activity;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import huwenpeng.project.R;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingmenu);

        TabHost tabHost = getTabHost();
        Intent intent = new Intent(this,Main_fragment_first.class);
        TabHost.TabSpec spec = tabHost.newTabSpec("消息");
        spec.setIndicator("消息",getResources().getDrawable(R.drawable.actionbar_icon));
        spec.setContent(intent);
        tabHost.addTab(spec);

        Intent intent2 = new Intent(this,Main_fragment_second.class);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("联系人");
        spec2.setIndicator("联系人",getResources().getDrawable(R.drawable.actionbar_icon));
        spec2.setContent(intent2);
        tabHost.addTab(spec2);

        Intent intent3 = new Intent(this,Main_fragment_three.class);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("动态");
        spec3.setIndicator("动态",getResources().getDrawable(R.drawable.actionbar_icon));
        spec3.setContent(intent3);
        tabHost.addTab(spec3);
    }
}
