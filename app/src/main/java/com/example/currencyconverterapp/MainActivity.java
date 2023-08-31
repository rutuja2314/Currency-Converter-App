package com.example.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.EditText;
import java.util.ArrayList;
import android.app.Dialog;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.AdapterView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import org.json.JSONObject;
import org.json.JSONException;
import java.math.RoundingMode;
import java.math.BigDecimal;
import com.android.volley.VolleyError;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //created id for each of the views
    TextView convertFrom, convertTo, result;
    EditText amt;
    ArrayList<String> arraylist;
    Dialog fd, td;
    Button conversion, refresh, exit, reset;
    String convertFromval, convertToval, convertVal;

    //All Country's Currency name Stored in String varicable
    String[] currency = {"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD",
            "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR",
            "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF",
            "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP",
            "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF","IDR", "ILS", "IMP", "INR", "IQD",
            "IRR", "ISK", "JEP", "JMD", "JOD", "JPY","KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD",
            "KZT","LAK", "LBP", "LKR", "LRD", "LSL", "LVL", "LYD","MAD", "MDL", "MGA", "MKD", "MMK", "MNT",
            "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD",
            "OMR","PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG","QAR","RON", "RSD", "RUB", "RWF","SAR", "SBD",
            "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL","THB", "TJS",
            "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS","UAH", "UGX", "USD", "UYU", "UZS","VEF", "VND", "VUV",
            "WST","XAF", "XAG", "XCD", "XDR", "XOF", "XPF","YER","ZAR", "ZMK", "ZMW", "ZWL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertFrom = findViewById(R.id.convertFrom);
        convertTo = findViewById(R.id.convertTo);
        result = findViewById(R.id.result);
        conversion = findViewById(R.id.conversion);
        refresh = findViewById(R.id.refresh);
        exit = findViewById(R.id.exit);
        reset = findViewById(R.id.reset);
        amt = findViewById(R.id.amt);

        arraylist = new ArrayList<>();
        for(String i : currency) {
            arraylist.add(i);
        }

        convertFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fd = new Dialog(MainActivity.this);
                fd.setContentView(R.layout.fromspinner);
                fd.getWindow().setLayout(650, 800);
                fd.show();

                EditText edittext = fd.findViewById(R.id.edit_text);
                ListView listview = fd.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arraylist);
                listview.setAdapter(adapter);

                edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertFrom.setText(adapter.getItem(position));
                        fd.dismiss();
                        convertFromval = adapter.getItem(position);
                    }
                });
            }
        });

        convertTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                td = new Dialog(MainActivity.this);
                td.setContentView(R.layout.tospinner);
                td.getWindow().setLayout(650, 800);
                td.show();

                EditText edittext = td.findViewById(R.id.edit_text);
                ListView listview = td.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arraylist);
                listview.setAdapter(adapter);

                edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertTo.setText(adapter.getItem(position));
                        td.dismiss();
                        convertToval = adapter.getItem(position);
                    }
                });
            }
        });

        conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double amt = Double.valueOf(MainActivity.this.amt.getText().toString());
                    getConversionRate(convertFromval, convertToval, amt);
                }
                catch(Exception e) {

                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Double amt = Double.valueOf(MainActivity.this.amt.getText().toString());
                    getConversionRate(convertFromval,convertToval,amt);
                }
                catch(Exception e){

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertFrom.setText("");
                convertTo.setText("");
                result.setText("");
                amt.setText("");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Close ();
            }
        });
    }

    public void getConversionRate(String convertFromval, String convertToval, Double amt) {
        RequestQueue requestqueue = Volley.newRequestQueue(this);
        //API key for the conversion
        String url = "https://free.currconv.com/api/v7/convert?q=" + convertFromval + "_" + convertToval + "&compact=ultra&apiKey=de0f53d63de4426e9869";

        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonobject = null;
                try {
                    jsonobject = new JSONObject(response);
                    Double conversion_rate_value = round(((Double) jsonobject.get(convertFromval + "_" + convertToval)), 2);
                    convertVal = "" + round((conversion_rate_value * amt), 2);
                    result.setText(convertVal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestqueue.add(stringrequest);
    }

    public static double round(double value, int currency) {
        if(currency < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(currency, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void Close() {
        MainActivity.this.finish();
        System.exit(0);
    }
}
