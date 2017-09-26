package huwenpeng.project.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

import huwenpeng.project.R;
import huwenpeng.project.activity.entity.User;


/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> list;

    public  ListViewAdapter(Context context,ArrayList<User> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView = convertView.inflate(context, R.layout.listitem, null);
            holder = new Holder();
            holder.tabhost_im = (SmartImageView) convertView.findViewById(R.id.tabhost_iv);
            holder.tabhost_tv_name = (TextView) convertView.findViewById(R.id.tabhost_tv_name);
            holder.tabhost_tv_sing = (TextView) convertView.findViewById(R.id.tabhost_tv_sing);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        User user = list.get(position);
        holder.tabhost_im.setImageUrl(user.getHead());
        holder.tabhost_tv_name.setText(list.get(position).getName());
        holder.tabhost_tv_sing.setText(list.get(position).getSingature());
        return convertView;
    }

    private class Holder{
        SmartImageView tabhost_im;
        TextView tabhost_tv_name;
        TextView tabhost_tv_sing;
    }
}
