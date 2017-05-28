package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tegar.daily.bdc2017.adapter.ItemTrendsettersAdapter;
import tegar.daily.bdc2017.adapter.TrendsettersAdapter;
import tegar.daily.bdc2017.model.ItemTrendsetters;
import tegar.daily.bdc2017.model.Trendsetters;

public class DetailInspirationActivity extends AppCompatActivity {
    ItemTrendsettersAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    TextView tv_namainspired, tv_deskripsi, tv_professi, tv_sumber;
    ImageView iv_gambar;
    ArrayList<ItemTrendsetters> itemTrendsetters = new ArrayList<>();
    String idtrendsetters;
    String [] aiditems;
    String [] agambar;
    String [] anamaproduk;
    String [] ahargatermahal;
    String [] ahargatermurah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inspiration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getDataIntent();
        getTrendsettersItems();
    }
    protected void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.rv_items);
        tv_namainspired = (TextView)findViewById(R.id.tv_namainspired);
        tv_deskripsi = (TextView)findViewById(R.id.tv_deskripsi);
        tv_professi = (TextView)findViewById(R.id.tv_profesi);
        tv_sumber = (TextView)findViewById(R.id.tv_sumber);
        iv_gambar = (ImageView)findViewById(R.id.iv_gambar);
    }
    protected void getDataIntent(){
        idtrendsetters = getIntent().getStringExtra("idtrendsetters");
        Log.d("id1", idtrendsetters);
        tv_namainspired.setText(getIntent().getStringExtra("namainspired"));
        Glide.with(DetailInspirationActivity.this)
                .load(Uri.parse(getIntent().getStringExtra("gambar")))
                .into(iv_gambar);
        tv_deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        tv_professi.setText(getIntent().getStringExtra("professi"));
        tv_sumber.setText(getIntent().getStringExtra("sumber"));
    }
    protected void setRecyclerView(){
        adapter = new ItemTrendsettersAdapter(setupItems(), getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<ItemTrendsetters> setupItems(){
        itemTrendsetters = new ArrayList<>();
        for (int i = 0; i<aiditems.length; i++){
            ItemTrendsetters item = new ItemTrendsetters();
            item.setIditems(aiditems[i]);
            item.setGambar(agambar[i]);
            item.setNamaproduk(anamaproduk[i]);
            Log.d("items", anamaproduk[i]);
            item.setHargatermahal(ahargatermahal[i]);
            item.setHargatermurah(ahargatermurah[i]);
            itemTrendsetters.add(item);
        }
        return (ArrayList<ItemTrendsetters>) itemTrendsetters;
    }
    //This method will get data from the web api
    public void getTrendsettersItems(){
        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(DetailInspirationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tembakolproject.esy.es/tembakol_API/getTrendsettersItemsById.php",
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
                            aiditems = new String[jsonArray.length()];
                            agambar = new String[jsonArray.length()];
                            anamaproduk = new String[jsonArray.length()];
                            ahargatermurah = new String[jsonArray.length()];
                            ahargatermahal = new String[jsonArray.length()];

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                                String iditem = jsonObject1.getString("id_similarItems");
                                Log.d("iditems", iditem);
                                String gambar = jsonObject1.getString("gambar_items");
                                String namaproduk = jsonObject1.getString("nama_items");
                                String hargamin = jsonObject1.getString("harga_terendah");
                                String hargamax = jsonObject1.getString("harga_tertinggi");

                                aiditems[i] = iditem;
                                agambar[i] = gambar;
                                anamaproduk[i] = namaproduk;
                                ahargatermurah[i] = hargamin;
                                ahargatermahal[i] = hargamax;
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
                        Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_trendsetters", idtrendsetters);
                Log.d("id", idtrendsetters);
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
