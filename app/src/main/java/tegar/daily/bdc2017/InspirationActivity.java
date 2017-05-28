package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tegar.daily.bdc2017.adapter.TrendsettersAdapter;
import tegar.daily.bdc2017.model.Trendsetters;

public class InspirationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardView cv_torecommend;
    TrendsettersAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Trendsetters> trendsetters = new ArrayList<>();
    String [] aidproduk;
    String [] agambar;
    String [] anamainspired;
    String [] aprofessi;
    String [] adeskripsi;
    String [] asumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getAllTrendsetters();
        //setRecyclerView();
        cv_torecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InspirationActivity.this, TrendsetterRecommend.class);
                startActivity(intent);
            }
        });
    }
    protected void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.rv_inspirasi);
        cv_torecommend = (CardView)findViewById(R.id.cv_torecommend);
    }
    protected void setRecyclerView(){
        adapter = new TrendsettersAdapter(setupInspired(), getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Trendsetters> setupInspired(){
        trendsetters = new ArrayList<>();
        for (int i = 0; i<aidproduk.length; i++){
            Trendsetters item = new Trendsetters();
            item.setIdtrendsetter(aidproduk[i]);
            item.setGambar(agambar[i]);
            item.setNama(anamainspired[i]);
            item.setProfessi(aprofessi[i]);
            item.setSumber(asumber[i]);
            item.setDeskripsi(adeskripsi[i]);
            trendsetters.add(item);
        }
        return (ArrayList<Trendsetters>) trendsetters;
    }
    //This method will get data from the web api
    public void getAllTrendsetters(){
        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(InspirationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tembakolproject.esy.es/tembakol_API/getAllTrendsetters.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        progressDialog.dismiss();
                        Log.d("response", response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //calling method to parse json array
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            aidproduk = new String[jsonArray.length()];
                            agambar = new String[jsonArray.length()];
                            anamainspired = new String[jsonArray.length()];
                            aprofessi = new String[jsonArray.length()];
                            adeskripsi = new String[jsonArray.length()];
                            asumber = new String[jsonArray.length()];

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                                String idproduk = jsonObject1.getString("id_trendsetters");
                                Log.d("idproduk", idproduk);
                                String gambar = jsonObject1.getString("gambar");
                                String namainspired = jsonObject1.getString("nama");
                                String profesi = jsonObject1.getString("profesi");
                                String deskripsi = jsonObject1.getString("deskripsi");
                                String sumber = jsonObject1.getString("sumber");

                                aidproduk[i] = idproduk;
                                agambar[i] = gambar;
                                anamainspired[i] = namainspired;
                                aprofessi[i] = profesi;
                                adeskripsi[i] = deskripsi;
                                asumber[i] = sumber;
                            }
                            setRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan Pada Saat Pengambilan Data", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        //VolleyController.getInstance().addToRequestQueue(stringRequest, "edit_komunitas_info");
    }
}
