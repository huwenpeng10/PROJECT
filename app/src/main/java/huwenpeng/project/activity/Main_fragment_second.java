package huwenpeng.project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import huwenpeng.project.R;
import huwenpeng.project.activity.adapter.ListViewAdapter;


/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class Main_fragment_second extends Activity {
    private ListView mListView;
    private Button mAddBtn;
    private View addView;
    private EditText mIdET;
    private EditText mReasonET;
    private TextView mUserTV;
    private TextView mGoTV;
    private ListViewAdapter mAdapter;
    private List<String> userList = new ArrayList<String>();

    /* 常量 */
    private final int CODE_ADD_FRIEND = 0x00001;
    private Handler mHandler;

    private String idStr;
    private String reasonStr;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_friends);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CODE_ADD_FRIEND:
                        Toast.makeText(getApplicationContext(), "请求发送成功，等待对方验证",
                                Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }

        };

//        EMContactManager.getInstance().setContactListener(
//                new MyContactListener());
//        EMChat.getInstance().setAppInited();

        mListView = (ListView) findViewById(R.id.chat_listview);
        mAddBtn = (Button) findViewById(R.id.chat_add_btn);
        mGoTV = (TextView) findViewById(R.id.friend_list_go);

        listener();
    }


    public void addFriends(View v){

                addView = LayoutInflater.from(Main_fragment_second.this).inflate(
                        R.layout.char_add_friends, null);
                mIdET = (EditText) addView.findViewById(R.id.chat_add_friend_id);
                mReasonET = (EditText) addView
                        .findViewById(R.id.chat_add_friend_reason);
                new AlertDialog.Builder(Main_fragment_second.this)
                        .setTitle("添加好友")
                        .setView(addView)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        idStr = mIdET.getText()
                                                .toString().trim();
                                        reasonStr = mReasonET.getText()
                                                .toString().trim();
                                        try {
                                            //参数为要添加的好友的username和添加理由
                                            EMClient.getInstance().contactManager().addContact(idStr, reasonStr);
                                            mHandler.sendEmptyMessage(CODE_ADD_FRIEND);
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        dialog.dismiss();
                                    }
                                }).create().show();

    }

    private void showAgreedDialog(final String user, String reason) {
        new AlertDialog.Builder(Main_fragment_second.this)
                .setTitle("应用提示")
                .setMessage(
                        "用户 " + user + " 想要添加您为好友，是否同意？\n" + "验证信息：" + reason)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            EMClient.getInstance().contactManager().declineInvitation(user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
    }

//    private void showDeleteDialog(final User user) {
//        new AlertDialog.Builder(Main_fragment_second.this)
//                .setTitle("应用提示")
//                .setMessage("确定删除好友  " + user + " 吗？\n")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            EMClient.getInstance().contactManager().deleteContact(user.getName());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.dismiss();
//                    }
//                }).show();
//    }


    public void listener(){//EMContactListener
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String username) {
                // 好友请求被同意
                Log.i("TAG", "onContactAgreed==>" + username);
                // 提示有新消息
                showAgreedDialog(idStr, reasonStr);
                Toast.makeText(getApplicationContext(), username + "同意了你的好友请求",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContactDeleted(String s) {
                //被删除时回调此方法
            }

            @Override
            public void onContactInvited(String username, String reason) {
                // 收到好友添加请求
//            Log.i("TAG", username + "onContactInvited==>" + reason);
//            showAgreedDialog(username, reason);
//            EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                //增加了联系人时回调此方法
                try {
                    userList = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                //好友请求被拒绝
            }
        });

    }
}
