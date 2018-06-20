package com.example.kevintsou.image.activity;

import android.content.Context;
import android.content.Intent;

/**
 * Created by KevinTsou on 3/29/2018.
 */

public class ActionUp {
    public static void startTopActivity(Context context, boolean newInstance) {
        Intent intent = new Intent(context, MainActivity.class);
        if (newInstance) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }
}
