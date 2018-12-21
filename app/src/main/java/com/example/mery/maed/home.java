package com.example.mery.maed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class home extends AppCompatActivity {

    Activity context;
    HttpPost httpPost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpClient;
    ProgressDialog pd;
    Customer_Adapter adapter;
    ListView listProduct;
    ArrayList<product_list> records;
    //userInRole us;
    TextView item_name;
    TextView item_price;
    TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        item_name=(TextView) findViewById(R.id.item_name);
        item_price=(TextView) findViewById(R.id.Item_price);
        type=(TextView) findViewById(R.id.item_type);
        context=this;
        records=new ArrayList<product_list>();
        listProduct=(ListView)findViewById(R.id.menu_list);
        adapter=new Customer_Adapter(context,R.layout.list_item,R.id.item_name,records);
        listProduct.setAdapter(adapter);
        listProduct.setClickable(true);
        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name=(TextView) view.findViewById(R.id.item_name);
                TextView price=(TextView) view.findViewById(R.id.item_price);
                TextView t=(TextView) view.findViewById(R.id.type);
                TextView j=(TextView) view.findViewById(R.id.id);
                String n=name.getText().toString();
                String p=price.getText().toString();
                String ty=t.getText().toString();
                String ji=j.getText().toString();
                Intent i= new Intent(context,edit_delete.class);
                Bundle extras=new Bundle();
                extras.putString("name",n);
                extras.putString("price",p);
                extras.putString("type",ty);
                extras.putString("id",ji);
                i.putExtras(extras);
                startActivity(i);
            }
        });

    }
    public void show_menu(View view){

        BackTask bt=new BackTask();
        bt.execute();
        System.out.println("was here1");
    }

    public void add_new_item(View view){
        String [] inputs={"item_name","item_price","type"};
        onBack bs=new onBack(inputs);
        String i_name=item_name.getText().toString();
        String i_price=item_price.getText().toString();
        String typ=type.getText().toString();
        //int i_pric=item_price.getInputType();


        bs.execute(i_name,i_price,typ);

        //BackTask bt=new BackTask();
        //bt.execute();



    }
    private class BackTask extends AsyncTask<String,String,String> {
        // String link;
        String inputs;
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
       /* public BackTask(String inputs)
        {
            this.inputs=inputs;
        }*/

        @Override
        protected String doInBackground(String... arg0) {
            InputStream is=null;
            String result="";
            try{
                String link="http://192.168.0.236/iohk/get_menu_food.php/";
                //link+=link;
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
                    JSONObject json_data=jArray.getJSONObject(i);
                    product_list p=new product_list();
                    p.setId(json_data.getString("id"));
                    p.setItemName(json_data.getString("item_name"));
                    p.setItemPrice(json_data.getString("item_price"));
                    p.setType(json_data.getString("type"));
                    records.add(p);
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
            Log.e("size",records.size()+"");
            adapter.notifyDataSetChanged();
            System.out.println("was here6");
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
            String link="http://192.168.0.236/iohk/insert_into_menus.php/";
            link=link+"?"+inputs[0]+"="+arg0[0]+"&"+inputs[1]+"="+arg0[1]+"&"+inputs[2]+"="+arg0[2];
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



