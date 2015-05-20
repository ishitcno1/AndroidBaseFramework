package com.edinstudio.android.baseframework.server;

import android.content.Context;

import com.edinstudio.android.baseframework.BuildConfig;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by albert on 15-5-20.
 */
public class Server {
    private static API mApi;

    public static API api(Context context) {
        if (mApi == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(getApiServer())
                    .setClient(new OkClient());

            if (BuildConfig.DEBUG) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            } else {
                builder.setLogLevel(RestAdapter.LogLevel.NONE);
            }

            mApi = builder.build().create(API.class);
        }

        return mApi;
    }

    public static String getApiServer() {
        if (BuildConfig.DEBUG) {
            return API.DEV_SERVER_URL;
        } else {
            return API.SERVER_URL;
        }
    }
}
