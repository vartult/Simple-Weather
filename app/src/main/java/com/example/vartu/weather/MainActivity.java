package com.example.vartu.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button weather;
    TextView result;
    EditText city;
    //String city2;
    String URL="http://api.openweathermap.org/data/2.5/weather?q=";
    String api="&appid=588cc2a477f87d90c67e90a3d85d9d38";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weather=(Button) findViewById(R.id.getweather);
        result=(TextView) findViewById(R.id.result);
        city=(EditText) findViewById(R.id.city1);


        //myURL="http://api.openweathermap.org/data/2.5/weather?q=Noida&appid=588cc2a477f87d90c67e90a3d85d9d38";
       weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myURL=URL + city.getText().toString() + api;

                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonrequest=new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Respose", String.valueOf(response));
                                try {
                                    String info= response.getString("weather");
                                    JSONArray ar= new JSONArray(info);
                                    for(int i=0;i< ar.length();i++) {
                                        JSONObject partobj = ar.getJSONObject(i);

                                        String myWeather= partobj.getString("main");
                                        result.setText(myWeather);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i("Error","Error Found chota wala");
                                }
                                //getString("main")
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error","Error Found");
                            }
                        }

                );
                mysingleton.getInstance(MainActivity.this).addToRequestQueue(jsonrequest);

            }
        });
    }
}
