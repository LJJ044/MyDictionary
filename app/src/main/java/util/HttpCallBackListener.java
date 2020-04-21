package util;

import java.io.InputStream;
import java.net.HttpURLConnection;

public interface HttpCallBackListener {
    //访问完成
    void onFinish(InputStream inputStream);
    //访问出错
    void onError(Exception e);
}
