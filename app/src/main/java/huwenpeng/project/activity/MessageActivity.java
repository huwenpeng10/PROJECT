package huwenpeng.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import huwenpeng.project.R;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class MessageActivity extends Activity {
    private TextView message_user_name;
    private TextView message_text;
    private EditText message_user_edit;
    private Button message_btn_send;
    private String friend = "nzhlw";
    private EMMessageListener msgListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        message_user_name = (TextView) findViewById(R.id.message_user_name);
        message_text = (TextView) findViewById(R.id.message_text);
        message_user_edit = (EditText) findViewById(R.id.message_user_edit);
        message_btn_send = (Button) findViewById(R.id.message_btn_send);

        Intent intent =getIntent();
        String f = intent.getStringExtra("friend");
        if("千与千寻".equals(f)){
            friend = "qyqx";
            message_user_name.setText("正在与千与千寻聊天");
        }else{
            message_user_name.setText("正在与奶嘴葫芦娃聊天");
        }
        message_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = message_user_edit.getText().toString().trim();
//                if(){
//
//                }
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, friend);
                //如果是群聊，设置chattype，默认是单聊
               //if (chatType == CHATTYPE_GROUP)
                    //message.setChatType(EMMessage.ChatType.GroupChat);
                message.setChatType(EMMessage.ChatType.Chat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                //发完消息后要清空文本框
                message_user_edit.setText("");

                message_text.setText(message_text.getText()+"\r\n"+content);

            }
        });

        initListener();
    }
//注册消息监听
    private void initListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(final List<EMMessage> list) {
                //收到消息
                Log.e("TAG", Thread.currentThread().getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(EMMessage message : list){
                            Log.e("TAG", "message.getBody().toString()" +
                                    " ==="+message.getBody().toString());
                            //设置内容
//                            String lodStr = message_text.getText()+"\r\n";
//                            message_text.setText(lodStr+message.getBody().toString()+"\r\n");
                            EMTextMessageBody txtBosy = (EMTextMessageBody) message.getBody();
                            Spannable spannable = Spannable.Factory.getInstance()
                                    .newSpannable(txtBosy.getMessage());
                            message_text.setText(spannable, TextView.BufferType.SPANNABLE);
                        }
                    }
                });
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {
                //消息状态变动
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override//一处监听
    protected void onDestroy() {
        super.onDestroy();
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
