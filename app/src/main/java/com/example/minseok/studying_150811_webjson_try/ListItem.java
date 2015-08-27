package com.example.minseok.studying_150811_webjson_try;

/**
 * Created by minseok on 2015-08-11.
 */
public class ListItem {

    private String[] mData;

    public ListItem(String[] data) {

        mData = data;
    }
    public ListItem(String str1, String str2, String str3) {

        mData = new String[3];
        mData[0] = str1;
        mData[1] = str2;
        mData[2] = str3;
    }

    public String[] getData() { return mData;}
    public String getData(int index) { return mData[index];}
    public void setData(String[] data)  { mData = data;}
}
