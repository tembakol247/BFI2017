package tegar.daily.bdc2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import tegar.daily.bdc2017.adapter.BukaLapakProdukAdapter;
import tegar.daily.bdc2017.model.BukaLapakProduk;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardView cardView;
    BukaLapakProdukAdapter adapter;
    GridLayoutManager layoutManager;
    Toolbar toolbar;
    ArrayList<BukaLapakProduk> bukaLapakProduk = new ArrayList<>();
    String [] aidproduk = {"1", "2"};
    String [] agambar = {"https://s1.bukalapak.com/img/6809798221/m-1000-1000/Jilbab_Pashmina_Instan_Lipit_Kerudung_Hijab_Instan.jpg"
            ,"https://s1.bukalapak.com/img/1810808121/m-1000-1000/12261205_2b188b7a_432a_49f5_be76_214e948a9409_800_800.jpg"};
    String [] anamaproduk = {"Jilbab Pashmina Instan Lipit Kerudung Hijab Instan", "Hijab Pashmina Instan Ziefa Totally Black"};
    String [] anamalapak = {"Albab Shop",
            "Hijab Pashmina Instan"};
    String [] aharga = {"Rp36.000", "Rp69.900"};
    String [] afeedback = {"33 ulasan", "10 ulasan"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        setRecyclerView();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InspirationActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.rv_produk);
        cardView = (CardView) findViewById(R.id.cv_toinspiration);
    }
    protected void setRecyclerView(){
        adapter = new BukaLapakProdukAdapter(setupProduk(), getApplicationContext());
        layoutManager = new GridLayoutManager(HomeActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<BukaLapakProduk> setupProduk(){
        bukaLapakProduk = new ArrayList<>();
        for (int i = 0; i<aidproduk.length; i++){
            BukaLapakProduk item = new BukaLapakProduk();
            item.setGambar(agambar[i]);
            item.setIdproduk(aidproduk[i]);
            item.setNamaproduk(anamaproduk[i]);
            item.setNamalapak(anamalapak[i]);
            item.setHargaasli(aharga[i]);
            item.setHargadiskon(aharga[i]);
            item.setFeedback(afeedback[i]);
            bukaLapakProduk.add(item);
        }
        return (ArrayList<BukaLapakProduk>) bukaLapakProduk;
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
}
