package tegar.daily.bdc2017.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tegar.daily.bdc2017.R;
import tegar.daily.bdc2017.model.BukaLapakProduk;
import tegar.daily.bdc2017.model.SimiliarItem;

/**
 * Created by Acer on 5/24/2017.
 */

public class SimiliarItemAdapter extends RecyclerView.Adapter<SimiliarItemAdapter.MyViewHolder>{
    ArrayList<SimiliarItem> items;
    Context context;

    public SimiliarItemAdapter(ArrayList<SimiliarItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SimiliarItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bukalapak_products, parent, false);
        SimiliarItemAdapter.MyViewHolder MyViewHolder = new SimiliarItemAdapter.MyViewHolder(itemView, context, items);
        return MyViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_namaproduk, tv_namalapak, tv_feedback, tv_hargaasli, tv_hargadiskon;
        public ImageView iv_gambar;
        ArrayList<SimiliarItem> items = new ArrayList<SimiliarItem>();
        Context context;
        public MyViewHolder(View view, final Context context, final ArrayList<SimiliarItem> items) {
            super(view);
            this.context = context;
            this.items = items;
            tv_namaproduk = (TextView)view.findViewById(R.id.tv_namaproduk);
            tv_namalapak = (TextView)view.findViewById(R.id.tv_namalapak);
            tv_feedback = (TextView)view.findViewById(R.id.tv_feedback);
            tv_hargaasli = (TextView)view.findViewById(R.id.tv_hargaasli);
            tv_hargadiskon = (TextView)view.findViewById(R.id.tv_hargadiskon);
            iv_gambar = (ImageView) view.findViewById(R.id.iv_gambar);
        }
    }


    @Override
    public void onBindViewHolder(SimiliarItemAdapter.MyViewHolder holder, final int position) {
        SimiliarItem similiarItem = items.get(position);
        holder.tv_namaproduk.setText(similiarItem.getNamaproduk());
        holder.tv_namalapak.setText("oleh "+similiarItem.getNamalapak());
        //holder.tv_feedback.setText("90%"+ " "+similiarItem.getFeedback());
        holder.tv_hargaasli.setText(similiarItem.getHargaasli());
        holder.tv_hargadiskon.setText(similiarItem.getHargadiskon());
        Glide.with(context)
                .load(Uri.parse(similiarItem.getGambar()))
                .into(holder.iv_gambar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
