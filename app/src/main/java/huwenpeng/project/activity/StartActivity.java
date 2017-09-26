package huwenpeng.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import huwenpeng.project.R;


/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, PagerActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
