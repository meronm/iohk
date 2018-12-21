package com.example.mery.maed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class edit_delete extends AppCompatActivity {
    EditText item_name,item_price,typ;
    Activity context;
    HttpPost httpPost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpClient;
    ProgressDialog pd;
    Customer_Adapter adapter;
    ListView listProduct;
    ArrayList<product_list> records;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        context=this;

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        String name=extras.getString("name");
        String price=extras.getString("price");
        String type=extras.getString("type");
         id=extras.getString("id");
        item_name=(EditText) findViewById(R.id.name);
        item_price=(EditText)findViewById(R.id.price);
        typ=(EditText) findViewById(R.id.type);
        Button edit=(Button) findViewById(R.id.edit);
        Button delete=(Button) findViewById(R.id.Delete);
        //item_name.setEnabled(false);
        //item_price.setEnabled(false);
        //typ.setEnabled(false);

        item_name.setText(name);
        item_price.setText(price);
        typ.setText(type);


    }
    public void edit_buton(View view){
        item_name.setEnabled(true);
        item_price.setEnabled(true);
        typ.setEnabled(true);
        String n=item_name.getText().toString();
        String p=item_price.getText().toString();
        String t=typ.getText().toString();
        String [] inputs={"id","item_name","item_price","type"};

        BackTask b=new BackTask(inputs);
        b.execute(id,n,p,t);




    }

    public void delete_button(View view){
        String [] inputs={"id"};
        onBack b=new onBack(inputs);
        b.execute(id);

    }

    private class BackTask extends AsyncTask<String,String,String> {
        // String link;
        String []inputs;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("was here2");
            pd=new ProgressDialog(context);
            pd.setTitle("Retriving data");
            pd.setMessage("please wait");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();
        }
       public BackTask(String []inputs)
        {
            this.inputs=inputs;
        }

        @Override
        protected String doInBackground(String... arg0) {
            InputStream is=null;
            String result="";
            try{
                String link="http://192.168.0.236/iohk/edit_menu.php/";
                link=link+"?"+inputs[0]+"="+arg0[0]+"&"+inputs[1]+"="+arg0[1]+"&"+inputs[2]+"="+arg0[2]+"&"+inputs[3]+"="+arg0[3];
                httpClient=new DefaultHttpClient();
                httpPost=new HttpPost(link);
                response=httpClient.execute(httpPost);
                HttpEntity entity=response.getEntity();
                is=entity.getContent();
                System.out.println("was here3");


            }catch (Exception e)
            {
                if(pd!=null)
                    pd.dismiss();
                Log.e("ERROR",e.getMessage());
                System.out.println("error1");

            }
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"),8);
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                result=sb.toString();
                System.out.println("was here4");

            }catch (Exception e)
            {
                Log.e("ERROR","Error converting result"+e.toString());
                System.out.println("error2");

            }
            try{
                result= result.substring(result.indexOf("["));
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++)
                {
                    /*JSONObject json_data=jArray.getJSONObject(i);
                    product_list p=new product_list();
                    p.setItemName(json_data.getString("item_name"));
                    p.setItemPrice(json_data.getString("item_price"));
                    p.setType(json_data.getString("type"));
                    records.add(p);*/
                    System.out.println("was here5");

                }


            }catch (Exception e)
            {
                Log.e("ERROR","Error pasting data"+e.toString());
                System.out.println("error3");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(pd!=null)
                pd.dismiss();
            //Log.e("size",records.size()+"");
            //adapter.notifyDataSetChanged();
            //System.out.println("was here6");
            //super.onPostExecute(result);
        }
    }


    private class onBack extends AsyncTask<String,String,String> {
        String []inputs;
        public onBack(String []inputs)
        {

            this.inputs=inputs;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(context);
            pd.setTitle("Inserting data");
            pd.setMessage("please wait");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            InputStream is=null;
            String result="";
            try{
                //arg0[2]=arg0[2].replaceAll("\\s+","%20");
                String link="http://192.168.0.236/iohk/delete_item.php/";
                link=link+"?"+inputs[0]+"="+arg0[0];
                httpClient=new DefaultHttpClient();
                httpPost=new HttpPost(link);
                response=httpClient.execute(httpPost);
                HttpEntity entity=response.getEntity();
                is=entity.getContent();


            }catch (Exception e)
            {
                if(pd!=null)
                    pd.dismiss();
                Log.e("ERROR",e.getMessage());
                System.out.println("error10");

            }
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"),8);
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                result=sb.toString();

            }catch (Exception e)
            {
                Log.e("ERROR","Error converting result"+e.toString());
                System.out.println("error11");

            }
            try{
                result= result.substring(result.indexOf("["));
                JSONArray jArray=new JSONArray(result);
                for(int i=0;i<jArray.length();i++)
                {
                    //JSONObject json_data=jArray.getJSONObject(i);
                    //Product p=new Product();
                    //p.setpName(json_data.getString("item"));
                    // p.setuPrice(json_data.getInt("price"));
                    // records.add(p);

                }


            }catch (Exception e)
            {
                Log.e("ERROR","Error pasting data"+e.toString());
                System.out.println("error12");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(pd!=null)
                pd.dismiss();
            //Log.e("size",records.size()+"");
            // adapter.notifyDataSetChanged();
            //super.onPostExecute(result);
        }

    }
}
