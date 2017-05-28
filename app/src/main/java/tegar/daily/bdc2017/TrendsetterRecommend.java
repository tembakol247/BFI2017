package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import tegar.daily.bdc2017.adapter.HashtagAdapter;
import tegar.daily.bdc2017.adapter.TopThreeAdapter;
import tegar.daily.bdc2017.adapter.TrendsettersAdapter;
import tegar.daily.bdc2017.model.Hashtag;
import tegar.daily.bdc2017.model.TopThree;
import tegar.daily.bdc2017.model.Trendsetters;

public class TrendsetterRecommend extends AppCompatActivity {
    HashtagAdapter hadapter;
    TopThreeAdapter tadapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView rv_top3, rv_hastag;
    EditText et_hastag;
    ImageButton btn_kirim;
    String hastag;
    ArrayList<Hashtag> hashtags = new ArrayList<>();
    ArrayList<TopThree> topThrees = new ArrayList<>();
    String [] aiduser;
    String [] anama;
    String [] ahashtag;
    String [] awaktu;
    String [] atopthreehashtag;
    String [] ajumlah;
    String iduser, namauser;
    SessionLogin sessionLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendsetter_recommend);
        initView();
        sessionLogin = new SessionLogin(getApplicationContext());
        HashMap<String, String> user = sessionLogin.getUserDetails();
        iduser = user.get(SessionLogin.KEY_ID);
        namauser = user.get(SessionLogin.KEY_NAME);
        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hastag = getHastag(et_hastag.getText().toString());
                setTrendsettersRecommend();
            }
        });
        getTopThree();
    }
    private void initView(){
        et_hastag = (EditText)findViewById(R.id.et_hastag);
        btn_kirim = (ImageButton) findViewById(R.id.btn_kirim);
        rv_hastag = (RecyclerView) findViewById(R.id.rv_hastag);
        rv_top3 = (RecyclerView) findViewById(R.id.rv_top3);
    }
    protected void setRecyclerView(){

    }
    protected void setRecyclerHastag(){
        hadapter = new HashtagAdapter(setupHashtag(), getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_hastag.setLayoutManager(layoutManager);
        rv_hastag.setItemAnimator(new DefaultItemAnimator());
        rv_hastag.setAdapter(hadapter);
    }
    protected void setRecyclerTopThree(){
        tadapter = new TopThreeAdapter(setupTopThree(), getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_top3.setLayoutManager(layoutManager);
        rv_top3.setItemAnimator(new DefaultItemAnimator());
        rv_top3.setAdapter(tadapter);
    }
    private ArrayList<Hashtag> setupHashtag(){
        hashtags = new ArrayList<>();
        for (int i = 0; i<aiduser.length; i++){
            Hashtag item = new Hashtag();
            item.setNama(anama[i]);
            item.setHastag(ahashtag[i]);
            item.setWaktu(awaktu[i]);
            hashtags.add(item);
        }
        return (ArrayList<Hashtag>) hashtags;
    }
    private ArrayList<TopThree> setupTopThree(){
        topThrees = new ArrayList<>();
        for (int i = 0; i<atopthreehashtag.length; i++){
            TopThree item = new TopThree();
            item.setHashtag(atopthreehashtag[i]);
            item.setJumlah(ajumlah[i]);
            topThrees.add(item);
        }
        return (ArrayList<TopThree>) topThrees;
    }
    private String getHastag(String data){
        Log.d("data", data);
        String requiredString = "";
        if (data.contains("#")){
            if (data.contains(" ")){
                requiredString = data.substring(data.indexOf("#") + 1, data.indexOf(" "));
            }else {
                requiredString = data.substring(data.indexOf("#") + 1);
            }
        }
        return requiredString;
    }
    //This method will get data from the web api
    public void setTrendsettersRecommend(){
        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(TrendsetterRecommend.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tembakolproject.esy.es/tembakol_API/addComment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        progressDialog.dismiss();
                        Log.d("response", response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //calling method to parse json array
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")){

                                aiduser = new String[jsonArray.length()];
                                anama = new String[jsonArray.length()];
                                ahashtag = new String[jsonArray.length()];
                                awaktu = new String[jsonArray.length()];

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                                    String iduser = jsonObject1.getString("id_user");
                                    Log.d("iduser", iduser);
                                    String namauser = jsonObject1.getString("nama_user");
                                    String waktu = jsonObject1.getString("waktu");
                                    String hashtag = jsonObject1.getString("hashtag_trendsetters");

                                    aiduser[i] = iduser;
                                    anama[i] = namauser;
                                    awaktu[i] = waktu;
                                    ahashtag[i] = hashtag;
                                }
                            }
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            setRecyclerHastag();
                            getTopThree();
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
                params.put("id_user", iduser);
                params.put("nama_user", namauser);
                params.put("komentar", "");
                params.put("hashtag_trendsetters", getHastag(et_hastag.getText().toString()));
                return params;
            }
        };
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        //VolleyController.getInstance().addToRequestQueue(stringRequest, "edit_komunitas_info");
    }

    //This method will get data from the web api
    public void getTopThree(){
        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(TrendsetterRecommend.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tembakolproject.esy.es/tembakol_API/getCountHashtag.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        progressDialog.dismiss();
                        Log.d("response", response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //calling method to parse json array
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")) {
                                atopthreehashtag = new String[jsonArray.length()];
                                ajumlah = new String[jsonArray.length()];
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                                    String jumlah = jsonObject1.getString("jumlah");
                                    Log.d("jumlah", jumlah);
                                    String idhashtag = jsonObject1.getString("hashtag_trendsetters");
                                    Log.d("hastag", idhashtag);


                                    atopthreehashtag[i] = idhashtag;
                                    ajumlah[i] = jumlah;
                                }
                            }
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            setRecyclerTopThree();
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
