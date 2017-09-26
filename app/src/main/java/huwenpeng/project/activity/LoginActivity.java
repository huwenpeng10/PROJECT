package huwenpeng.project.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import huwenpeng.project.R;

/**
 * 登录界面
 * Created by Administrator on 2017/1/10 0010.
 */
public class LoginActivity extends Activity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private boolean progressShow;

    private String currentUsername;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login);

        usernameEditText = (EditText) findViewById(R.id.user_name_text);
        passwordEditText = (EditText) findViewById(R.id.user_password_text);

        // if 用户名改变 清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * login
     *
     * @param view
     */
    //button 的点击事件
    public void login(View view) {
//        if (!EaseCommonUtils.isNetWorkConnected(this)) {
//            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
//            return;
//        }
        currentUsername = usernameEditText.getText().toString().trim();
        currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d("TAG", "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
//        DemoDBManager.getInstance().closeDB();
//
//        // reset current user name before login
//        DemoHelper.getInstance().setCurrentUserName(currentUsername);
//
//        final long start = System.currentTimeMillis();
        // 调用sdk登录服务器
        Log.d("TAG", "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override//登录成功
            public void onSuccess() {
                Log.d("TAG", "登录成功");
                if(!LoginActivity.this.isFinishing() && pd.isShowing()){
                    pd.dismiss();
                }

                //第一次登录或之前logout后再登录  加载所有本地群和会话
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
               // DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                //进入主页
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("TAG", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d("TAG", "登录失败 错误代码是: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


//    /**
//     * register
//     *
//     * @param view
//     */
//    public void register(View view) {
//        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
//    }
}
