// Login.aidl
package com.jhzl.server;
import com.jhzl.server.User;
import com.jhzl.server.Callback;
// Declare any non-default types here with import statements

interface Login {

    void login(in User stu,in Callback call);


}