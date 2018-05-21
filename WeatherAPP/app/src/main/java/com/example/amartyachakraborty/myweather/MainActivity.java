package com.example.amartyachakraborty.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//http://api.openweathermap.org/data/2.5/weather?q=KOLKATA&appid=4c3a18c3700a234f59f34443915e2cf7
public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText loc;
    TextView showtemp,showdesc,lonlat,humidity,loading;
    String baseurl="http://api.openweathermap.org/data/2.5/weather?q=";
    String Api="&appid=4c3a18c3700a234f59f34443915e2cf7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        loc=(EditText)findViewById(R.id.editText);
        loc.setHint("Enter Your City");
        showtemp=(TextView)findViewById(R.id.textView2);
        showdesc=(TextView)findViewById(R.id.textView3);
        lonlat=(TextView)findViewById(R.id.textView4);
        humidity=(TextView)findViewById(R.id.textView5);
        loading=(TextView)findViewById(R.id.textView6);
        final ImageView img=(ImageView)findViewById(R.id.logo) ;
        showdesc.setVisibility(View.INVISIBLE);
        showtemp.setVisibility(View.INVISIBLE);
        lonlat.setVisibility(View.INVISIBLE);
        humidity.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        img.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdesc.setVisibility(View.INVISIBLE);
                showtemp.setVisibility(View.INVISIBLE);
                lonlat.setVisibility(View.INVISIBLE);
                humidity.setVisibility(View.INVISIBLE);
                img.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);

                String testnull=loc.getText().toString();
                if (testnull.isEmpty()) {
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Please Enter Your City", Toast.LENGTH_SHORT).show();
                } else
                {
                    String url = baseurl + loc.getText().toString() + Api;
                Log.i("url", "url :" + url);
                JsonObjectRequest jsb = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                JSONObject ob = null;
                                JSONObject ob2 = null;
                                JSONObject ob3 = null;

                                try {
                                    //Fetching temp
                                    ob = response.getJSONObject("main");
                                    double ktemp = ob.getDouble("temp");
                                    String Humidity = ob.getString("humidity");

                                    Float ctemp = new Float(ktemp - 273.1);
                                    Float ftemp = new Float(ktemp - 459.67);
                                    loading.setVisibility(View.INVISIBLE);
                                    showtemp.setVisibility(View.VISIBLE);
                                    showtemp.setText("Temperature: " + ctemp + "°C  / " + ftemp + "°F");
                                    humidity.setVisibility(View.VISIBLE);
                                    humidity.setText("Humidity: " + Humidity);

                                    //Fetching description
                                    JSONArray ar = response.getJSONArray("weather");
                                    ob2 = ar.getJSONObject(0);
                                    img.setVisibility(View.VISIBLE);
                                    showdesc.setVisibility(View.VISIBLE);

                                    showdesc.setText(ob2.getString("description"));
                                    //set icon
                                    String icon="my"+ob2.getString("icon");
                                    Log.i("icon","icon"+icon);
                                    switch (icon){
                                        case "my01d":
                                            img.setImageResource(R.drawable.my01d);break;
                                        case "my02d":
                                            img.setImageResource(R.drawable.my02d);break;
                                        case "my03d":
                                            img.setImageResource(R.drawable.my03d);break;
                                        case "my04d":
                                            img.setImageResource(R.drawable.my04d);break;
                                        case "my09d":
                                            img.setImageResource(R.drawable.my09d);break;
                                        case "my10d":
                                            img.setImageResource(R.drawable.my10d);break;
                                        case "my13d":
                                            img.setImageResource(R.drawable.my13d);break;
                                        case "my50d":
                                            img.setImageResource(R.drawable.my50d);break;
                                        case "my01n":
                                            img.setImageResource(R.drawable.my01n);break;
                                        case "my02n":
                                            img.setImageResource(R.drawable.my02n);break;
                                        case "my03n":
                                            img.setImageResource(R.drawable.my03n);break;
                                        case "my04n":
                                            img.setImageResource(R.drawable.my04n);break;
                                        case "my09n":
                                            img.setImageResource(R.drawable.my09n);break;
                                        case "my10n":
                                            img.setImageResource(R.drawable.my10n);break;
                                        case "my13n":
                                            img.setImageResource(R.drawable.my13n);break;
                                        case "my50n":
                                            img.setImageResource(R.drawable.my50n);break;


                                    }



                                    //Fetching Longitute & Latitude
                                    ob3 = response.getJSONObject("coord");
                                    Float longitude = new Float(ob3.getDouble("lon"));
                                    Float latitude = new Float(ob3.getDouble("lat"));
                                    //Log.i("lan","lan"+longitude);
                                    lonlat.setVisibility(View.VISIBLE);
                                    lonlat.setText("Longitude: " + longitude + " Latitude: " + latitude);



                                } catch (JSONException e) {

                                  e.printStackTrace();



                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Error", "Something Went Wrong " + error);
                            }
                        }
                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsb);
            }
            }
        });

    }
}
