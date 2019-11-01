package com.example.pay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText etName,etPwd;
    String uname,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.editText4);
        etPwd=findViewById(R.id.editText5);
    }

    public void login(View view) {
        uname=etName.getText().toString();

        pwd = etPwd.getText().toString();
        String link="https://srecwt.000webhostapp.com/login.php";
        new Login(this).execute(link);
    }
    class Login extends AsyncTask<String, Void, String>{

        Activity ctx;
        ProgressDialog pd;


        public Login(Activity ctx) {
            this.ctx = ctx;

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(ctx);
            pd.setTitle("Loading");
            pd.setMessage("Logging in.....Please wait");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String login =  strings[0];
            try {
                URL url = new URL(login);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String link = URLEncoder.encode("uname", "UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&"+
                        URLEncoder.encode("pwd", "UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8");
                bw.write(link);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = connection.getInputStream();
                BufferedReader br= new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String ln = "";
                String res = "";
                if((ln = br.readLine())!=null)
                    res += ln;
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try {
                JSONObject o = new JSONObject(s);
                JSONArray j = o.getJSONArray("details");
                for (int i=0; i<j.length(); i++){
                    JSONObject c = j.getJSONObject(i);
                    if(c.getString("status").equalsIgnoreCase("success")){
                        String name = c.getString("un");
                        String id = c.getString("id");
                        Toast.makeText(ctx, "Hello Mr. "+name+"\nYour id is: "+id, Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(MainActivity.this,button.class);
                        startActivity(registerIntent);


                    }
                    else
                    {
                        Toast.makeText(ctx, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void signup(View view) {
        Intent registerIntent = new Intent(MainActivity.this,signup.class);
        startActivity(registerIntent);

    }
}
