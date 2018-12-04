package com.legue.axel.bakingapp.retrofit;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.legue.axel.bakingapp.BakingApplication;
import com.legue.axel.bakingapp.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class RetrofitHelper {

    private static final String TAG = RetrofitHelper.class.getSimpleName();

    public static void getRecipes(final int action, final Handler handlerMessage, final BakingApplication application) {

        application.getRetrofitManager().getRecipes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe :" + d.toString());
                    }

                    @Override
                    public void onNext(ResponseBody recipeResponse) {
                        if (recipeResponse != null) {
                            long size = 0;
                            StringBuilder builder = new StringBuilder();
                            try {
                                InputStream inputStream;
                                inputStream = recipeResponse.byteStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                                String recipejson;
                                while ((recipejson = reader.readLine()) != null) {
                                    builder.append(recipejson);
                                }
                                application.setRecipesString(builder.toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            int code = httpException.code();
                            Log.i(TAG, "Server respond with code : " + code);
                            Log.i(TAG, "Response : " + httpException.getMessage());
                        } else {
                            Log.i(TAG, e.getMessage() == null ? "unknown error" : e.getMessage());
                            e.printStackTrace();
                        }
                        // Send message for send image
                        Message msg = new Message();
                        msg.what = Constants.ACTION_ERROR;
                        msg.obj = Constants.ERROR;
                        handlerMessage.sendMessage(msg);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                        Message message = new Message();
                        message.what = action;
                        handlerMessage.sendMessage(message);
                    }
                });

    }
}
