package tegar.daily.bdc2017;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import tegar.daily.bdc2017.adapter.BukaLapakProdukAdapter;
import tegar.daily.bdc2017.adapter.SimiliarItemAdapter;
import tegar.daily.bdc2017.model.BukaLapakProduk;
import tegar.daily.bdc2017.model.SimiliarItem;

public class ItemActivity extends AppCompatActivity {
    RecyclerView rv_similiaritems, rv_otheritems;
    BukaLapakProdukAdapter oadapter;
    SimiliarItemAdapter sadapter;
    GridLayoutManager layoutManager;
    ArrayList<BukaLapakProduk> bukaLapakProduk = new ArrayList<>();
    ArrayList<SimiliarItem> similiarItems = new ArrayList<>();
    //otheritemsvalue
    String [] oidproduk = {"1"};
    String [] ogambar = {"https://s1.bukalapak.com/img/6809798221/m-1000-1000/Jilbab_Pashmina_Instan_Lipit_Kerudung_Hijab_Instan.jpg"};
    String [] onamaproduk = {"Jilbab Pashmina Instan Lipit Kerudung Hijab Instan"};
    String [] onamalapak = {"Albab Shop"};
    String [] oharga = {"Rp36.000"};
    String [] ofeedback = {"33 ulasan"};
    //similiaritemsvalue
    String [] sidproduk;
    String [] sgambar;
    String [] snamaproduk;
    String [] snamalapak;
    String [] sharga;
    String [] sfeedback;
    String idsimilar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idsimilar = getIntent().getStringExtra("idsimilar");
        Log.d("idsimilar", idsimilar);
        initView();
        getAllSimiliar();
        setRecyclerOther();
    }
    protected void initView(){
        rv_similiaritems = (RecyclerView)findViewById(R.id.rv_similiaritems);
        rv_otheritems = (RecyclerView)findViewById(R.id.rv_otheritems);
    }
    protected void setRecyclerView(){
        setRecyclerSimiliar();
        setRecyclerOther();
    }
    private ArrayList<SimiliarItem> setupSimiliar(){
        similiarItems = new ArrayList<>();
        for (int i = 0; i<sidproduk.length; i++){
            SimiliarItem item = new SimiliarItem();
            item.setGambar(sgambar[i]);
            item.setIdproduk(sidproduk[i]);
            item.setNamaproduk(snamaproduk[i]);
            item.setNamalapak(snamalapak[i]);
            item.setHargaasli(sharga[i]);
            item.setHargadiskon(sharga[i]);
            //item.setFeedback(sfeedback[i]);
            similiarItems.add(item);
        }
        return (ArrayList<SimiliarItem>) similiarItems;
    }
    private ArrayList<BukaLapakProduk> setupOther(){
        bukaLapakProduk = new ArrayList<>();
        for (int i = 0; i<oidproduk.length; i++){
            BukaLapakProduk item = new BukaLapakProduk();
            item.setGambar(ogambar[i]);
            item.setIdproduk(oidproduk[i]);
            item.setNamaproduk(onamaproduk[i]);
            item.setNamalapak(onamalapak[i]);
            item.setHargaasli(oharga[i]);
            item.setHargadiskon(oharga[i]);
            item.setFeedback(ofeedback[i]);
            bukaLapakProduk.add(item);
        }
        return (ArrayList<BukaLapakProduk>) bukaLapakProduk;
    }
    protected void setRecyclerSimiliar(){
        sadapter = new SimiliarItemAdapter(setupSimiliar(), getApplicationContext());
        layoutManager = new GridLayoutManager(ItemActivity.this, 2);
        rv_similiaritems.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        rv_similiaritems.setItemAnimator(new DefaultItemAnimator());
        rv_similiaritems.setItemAnimator(new DefaultItemAnimator());
        rv_similiaritems.setAdapter(sadapter);
    }
    protected void setRecyclerOther(){
        oadapter = new BukaLapakProdukAdapter(setupOther(), getApplicationContext());
        layoutManager = new GridLayoutManager(ItemActivity.this, 2);
        rv_otheritems.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        rv_otheritems.setItemAnimator(new DefaultItemAnimator());
        rv_otheritems.setItemAnimator(new DefaultItemAnimator());
        rv_otheritems.setAdapter(oadapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    //This method will get data from the web api
    public void getAllSimiliar(){
        //Showing a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(ItemActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Creating a json array request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tembakolproject.esy.es/tembakol_API/getSimilarItemsById.php",
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

                            sidproduk = new String[jsonArray.length()];
                            sgambar = new String[jsonArray.length()];
                            snamaproduk = new String[jsonArray.length()];
                            snamalapak = new String[jsonArray.length()];
                            sharga = new String[jsonArray.length()];
                            sfeedback = new String[jsonArray.length()];

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);
                                String idproduk = jsonObject1.getString("id_similaritems");
                                String gambar = jsonObject1.getString("gambar");
                                String namaproduk = jsonObject1.getString("nama");
                                String namalapak = jsonObject1.getString("nama_lapak");
                                String harga = jsonObject1.getString("harga");
                                //String feedback = jsonObject1.getString("");

                                sidproduk[i] = idproduk;
                                sgambar[i] = gambar;
                                snamaproduk[i] = namaproduk;
                                snamalapak[i] = namalapak;
                                sharga[i] = harga;
                                //sfeedback[i] = feedback;
                            }
                            setRecyclerSimiliar();
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
                params.put("id_similaritems", idsimilar);
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
