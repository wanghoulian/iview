package cn.gcd.sb;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.gcd.sb.widget.ScaleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {
    private TextView result;
    private ScaleImageView image;
    private Thread requestThread;
    private static final String TAG = "SBGCD";
    private Disposable disposable;
    private String basePath = "sdcard/DCIM/Camera";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        result = findViewById(R.id.result);
        image = findViewById(R.id.image);
        rxJava2();
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/IMG_20170415_100823.jpg";
        Log.d(TAG, "file://" + filePath);
        Glide.with(this).load(filePath).into(image);
    }

    public void rxJava2() {
        /*Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("第一部");
                emitter.onNext("第二部");
                emitter.onNext("第三部");
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept:" + s);
                    }
                });*/
                /*.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        Log.d(TAG, "onSubscribe:" + disposable);
                    }

                    @Override
                    public void onNext(String o) {
                        Log.d(TAG, "onNext:" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:");
                    }
                });*/


        /*Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) {
                Request.Builder builder = new Request.Builder().url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512").get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                try {
                    Response response = call.execute();
                    String result = response.body().string();
                    if (result != null) {
                        emitter.onSuccess(result.toString());
                    } else {
                        emitter.onError(new Throwable(new Exception("hhhh")));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
                .map(new Function<String, ResponseModel>() {
                    @Override
                    public ResponseModel apply(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ResponseModel responseModel = new ResponseModel();
                            responseModel.setCode(jsonObject.getInt("error_code"));
                            responseModel.setResult(jsonObject.getString("reason"));
                            return responseModel;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe:" + d);
                    }

                    @Override
                    public void onSuccess(ResponseModel s) {
                        Log.d(TAG, "onSuccess:" + s);
                        result.setText("onSuccess:" + s.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e.toString());
                        result.setText("onError:" + e.toString());
                    }
                });*/


        /*String article = "fkjdsalijfofldaJFOIEjfldanlJR2OnfldajilwafkndaIUPO32,LFKjlijuJFLMA";

        final char[] chars = article.toCharArray();
        Observable.create(new ObservableOnSubscribe<Character>() {
            @Override
            public void subscribe(ObservableEmitter<Character> e) {
                for (int i = 0; i < chars.length; i++) {
                    e.onNext(chars[i]);
                }
            }
        })
                //事件类型转换
                .map(new Function<Character, String>() {
                    @Override
                    public String apply(Character s) throws Exception {
                        if (s >= 'a' && s <= 'z') {
                            return s.toString().toUpperCase();
                        } else {
                            return s.toString();
                        }
                    }
                })
                //线程调度
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.e(TAG, s);
                        result.setText("accept:" + result.getText() + s);
                    }
                });*/


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e){
                e.onNext(15);
                e.onNext(25);
                e.onNext(35);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer){
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 5; i++) {
                    list.add("我是变换过的:" + integer);
                }
                return Observable.fromIterable(list);
                /*String result = "你大爷:" + integer;
                Map<Integer, String> map = new HashMap<>();
                map.put(integer, result);
                return Observable.fromIterable(map);*/
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s){
                Log.d(TAG, s);
                result.setText(result.getText() + s);
            }
        });
    }
}
