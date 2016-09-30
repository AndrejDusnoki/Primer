package com.example.andrej.primerprojekta;

import android.support.annotation.Nullable;

/**
 * Created by andrej on 28.9.16..
 */
public interface CompletionHandler {
    public void onCompletionFailed();
    public void onCompletionSuccsess(@Nullable String s);
}
