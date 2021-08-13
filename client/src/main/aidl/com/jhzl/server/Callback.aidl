// Callback.aidl
package com.jhzl.server;
import com.jhzl.server.User;
// Declare any non-default types here with import statements

interface Callback {

    void onSuccess(in User user);

    void onFailed(int errorCode,String errorMsg);
}