package com.example.minseok.studying_150811_webjson_try;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends Activity {

    TextView txtView;
    getJsonByPhp task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asyncTask �غ�.
        task = new getJsonByPhp();

        txtView = (TextView) findViewById(R.id.txtView);

        //asyncTask ����.


        //task.execute("http://10.0.2.2/studying/150814_android_post_php/appdata.php");
        task.execute("http://54.64.160.105/appdata4.php");

        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailMainActivity.class);
                startActivity(intent);
            }
        });

    }


    private class getJsonByPhp extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder jsonHtml = new StringBuilder();
            try{
                //url ����
                URL url = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                //����Ǿ��ٸ�
                if(conn != null)    {
                    conn.setConnectTimeout(10000);


                    /* post 하는 부분 */
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setDefaultUseCaches(false);

                    conn.connect();

                    String string2post = "a=1&b=1&c=0";
                    byte[] bytes2post = string2post.getBytes();

                    //����Ǿ���. �ڵ尡 ���ϵǸ�,
                    //
                        //먼저 보내기. outputStream을 이용함.
                    OutputStream outputstream = conn.getOutputStream();
                    outputstream.write(bytes2post);
                    outputstream.flush();
                    outputstream.close();

                        //���� bytes �ε� stream �ް�
                    if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                        InputStream inputstream_0 = conn.getInputStream();
                        //InputStreamReader   bytes stream �� character stream ���� �ٲٰ�.
                        InputStreamReader inputstreamreader_0 = new InputStreamReader(inputstream_0, "UTF-8");
                        //inputstreamreader   reader�� wrap�ϰ� buffered ������༭ �̿��ϱ� ���� ��. (�ð����̵�)
                        BufferedReader buffered = new BufferedReader(inputstreamreader_0);

                        //buffered data�� ó����
                        for (; ; ) {
                            //�ؽ�Ʈ ������ �о� ����
                            String line = buffered.readLine();

                            //Toast.makeText(getApplicationContext(), ""+line, Toast.LENGTH_SHORT).show();
                            if (line == null) break;
                            // ����� �ؽ�Ʈ ������ jsonHtml stringbuilder�� ����.
                            jsonHtml.append(line + "\n");
                        }
                        //bufferedReader �ݰ�.
                        buffered.close();
                        inputstreamreader_0.close();
                        inputstream_0.close();

                        //HttpURLConnection �� �ݰ�.
                        conn.disconnect();
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }

            return jsonHtml.toString();

        }

        //AsyinTask doInBackground return �����͸� ������.
        protected void onPostExecute(String str){

            String id = null;
            String name;
            String regdate;

            ArrayList<ListItem> lastDatas = new ArrayList<ListItem>();
            StringBuilder tempLastString = new StringBuilder();

            //string�� �ִ� �����͸� json ���� �ް� ó���� �� �ְ�.
            try{

                //JSONObject �����ϰ� �ȿ� result ��̸� ����.
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i=0; i<jsonArray.length(); i++) {
                    //JSONObject �� ���� ���� �ִ� JSONArray���� i��° JSONObject ��.
                    JSONObject nodeData = jsonArray.getJSONObject(i);
                    //�� ��忡�� �ش� Ű�� ���� �ް�.
                    id = nodeData.getString("id");
                    name = nodeData.getString("name");
                    regdate = nodeData.getString("regdate");

                    //��ü �ν��Ͻ� ����.
                    //////////////////���⼭ ���Ͱ� �ȵ�.
                    ListItem data = new ListItem(id, name, regdate);
                    //ArrayList�� ��ü ����.
                    lastDatas.add(i, data);

                }


                //ArrayList�� ��ü �ް�.
                Iterator<ListItem> iterator = lastDatas.iterator();
                while(iterator.hasNext()) {
                    ListItem temp = iterator.next();
                    String[] tempStr = temp.getData();
                    tempLastString.append("id : "+tempStr[0]+"\t"+"name : "+tempStr[1]+"\t"+"regdate : "+tempStr[2]+"\n");
                }

                txtView.setText(tempLastString.toString());

            }
            catch(Exception ex) {

            }
        }

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
