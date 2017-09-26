package huwenpeng.myaddcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import huwenpeng.myaddcontact.R;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class ChatListAdapter extends BaseAdapter {

    Context mContext;
    List<ChatListData> mListData;

    public ChatListAdapter(Context mContext, List<ChatListData> mListData) {
        super();
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int index, View cView, ViewGroup arg2) {
        // TODO Auto-generated method stub

        Holder holder;
        if (cView == null) {
            holder = new Holder();
            cView = LayoutInflater.from(mContext).inflate(
                    R.layout.chat_listview_item, null);
            holder.rAvatar = (Button) cView
                    .findViewById(R.id.listview_item_receive_avatar);
            holder.rContent = (TextView) cView
                    .findViewById(R.id.listview_item_receive_content);
            holder.chatTime = (TextView) cView
                    .findViewById(R.id.listview_item_time);
            holder.sContent = (TextView) cView
                    .findViewById(R.id.listview_item_send_content);
            holder.sAvatar = (Button) cView
                    .findViewById(R.id.listview_item_send_avatar);
            holder.sName = (TextView) cView.findViewById(R.id.name1);
            holder.sName1 = (TextView) cView.findViewById(R.id.name2);
            cView.setTag(holder);

        } else {
            holder = (Holder) cView.getTag();
        }

        holder.chatTime.setVisibility(View.GONE);

        if (mListData.get(index).getType() == 2) {
            holder.rAvatar.setVisibility(View.VISIBLE);
            holder.rContent.setVisibility(View.VISIBLE);
            holder.sName.setVisibility(View.VISIBLE);
            holder.sName.setText("您的朋友说：");
            holder.sContent.setVisibility(View.GONE);
            holder.sAvatar.setVisibility(View.GONE);
            holder.sName1.setVisibility(View.GONE);

        } else if (mListData.get(index).getType() == 1) {
            holder.rAvatar.setVisibility(View.GONE);
            holder.sName.setVisibility(View.GONE);
            holder.rContent.setVisibility(View.GONE);
            holder.sContent.setVisibility(View.VISIBLE);
            holder.sAvatar.setVisibility(View.VISIBLE);
            holder.sName1.setVisibility(View.VISIBLE);
            holder.sName1.setText("我");
        }
        holder.chatTime.setText(mListData.get(index).getChatTime());
        holder.rContent.setText(mListData.get(index).getReceiveContent());
        holder.sContent.setText(mListData.get(index).getSendContent());

        return cView;
    }

    class Holder {
        Button rAvatar;
        TextView rContent;
        TextView chatTime;
        TextView sContent;
        TextView sName;
        TextView sName1;
        Button sAvatar;
    }
}