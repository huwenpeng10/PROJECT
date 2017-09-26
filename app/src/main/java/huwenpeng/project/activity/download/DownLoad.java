package huwenpeng.project.activity.download;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DownLoad {
    private static String result = "";
    public static String downLoadesult() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String URL_PATH = "http://192.168.155.108:8080/users/usersInfo.json";
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

                        while ((data = bufferedReader.readLine()) != null){
                            result += data + "\n";
                        }

                        Log.e("TAG", "打印解析出来的数据======"
                                + result.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return result;
    }
}
