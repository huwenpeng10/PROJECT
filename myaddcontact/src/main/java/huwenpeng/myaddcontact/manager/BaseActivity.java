package huwenpeng.myaddcontact.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public abstract class BaseActivity extends Activity {

    protected Context context = null;
    protected BaseApplication mApplication;
    protected Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        AppManager.getInstance().addActivity(this);
//      check netwotk
        context = this;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

}
