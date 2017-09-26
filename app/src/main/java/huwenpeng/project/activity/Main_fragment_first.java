package huwenpeng.project.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import huwenpeng.project.R;
import huwenpeng.project.activity.adapter.ListViewAdapter;
import huwenpeng.project.activity.entity.User;
import huwenpeng.project.activity.entity.Users;


/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class Main_fragment_first extends Activity {

    private ListView listView;
    private Button button;
    private Context context;
    private ListViewAdapter adapter;
    List<HashMap<String,String>> list ;

    private PopupWindow pw;
    private ListView pwListView;
    private int num_pw = 3;

    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CROP_PHOTO = 2;//裁剪
    private Uri imageUri;
    private ImageView picture_iv;
    private  User user;
    private List<User> userList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhostfirst);

        context = Main_fragment_first.this;
        initDate();
        initpw();
        listView = (ListView) findViewById(R.id.tabhost_lv);
        picture_iv =(ImageView)findViewById(R.id.picture_iv);
        //DownLoad downLoad = new DownLoad(this,listView);
        //String result =
        downLoadesult();
        user = new User();
        initListener();
    }

    private void downLoadesult() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String URL_PATH = "http://192.168.155.100:8080/users/usersInfo.json";
                    URL url = new URL(URL_PATH);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if(code == 200){
                        InputStream in = conn.getInputStream();
                        InputStreamReader reader = new InputStreamReader(in);
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String data = "";
                        String result = "";
                        while ((data = bufferedReader.readLine()) != null){
                            result += data + "\n";
                        }

                        Log.e("TAG", "打印解析出来的数据======"
                                + result.toString());
                        Gson gson = new Gson();
                        Users users = gson.fromJson(result, Users.class);
                        ArrayList<User> list = users.getUserinfo();
                        adapter = new ListViewAdapter(context,list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User userInfo = (User) parent.getAdapter().getItem(position);
                Intent intent = new Intent(Main_fragment_first.this, MessageActivity.class);
                intent.putExtra("friend", userInfo.getName());
                startActivity(intent);
            }
        });
    }

    private void initDate(){
        list = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("share_key","复制");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("share_key","删除");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("share_key","修改头像");
        list.add(map);
    }

    private void initpw(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupwindow_list_item, null);
        pwListView = (ListView) layout.findViewById(R.id.popupwondow_list_item_lv);
        pw = new PopupWindow(layout);
        pw.setFocusable(true);

        pwListView.setAdapter(
                new SimpleAdapter(
                        this, list,
                        R.layout.popupwindow_list,
                        new String[]{"share_key"},
                        new int[]{R.id.pw_text}));

        pwListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(pw.isShowing()){
                    pw.dismiss();
                }
                Toast.makeText(Main_fragment_first.this,
                        list.get(position).get("share_key"),
                        Toast.LENGTH_LONG).show();
                String key = list.get(position).get("share_key");
                if("修改头像".equals(key)){
                    File outputImage = new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
                    try {
                        if(outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    imageUri = Uri.fromFile(outputImage);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,TAKE_PHOTO);
                }
            }
        });
        //measure 表示测量  MeasureSpec.UNSPECIFIED 未制定尺寸  可以动态的设置
        pwListView.measure(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //pwListView.getMeasuredWidth()  其中一行的款
        pw.setWidth(pwListView.getMeasuredWidth());
        //pw 的总高度  pwListView.getMeasuredHeight()其中一行的高
        pw.setHeight((pwListView.getMeasuredHeight() + 20) * 3);
        //表示  触摸pw外部  pw消失  设置了background才会管用
        pw.setOutsideTouchable(true);

        pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popupwindow));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                        break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture_iv.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
