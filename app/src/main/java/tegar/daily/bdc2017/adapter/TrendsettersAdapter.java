package tegar.daily.bdc2017.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tegar.daily.bdc2017.DetailInspirationActivity;
import tegar.daily.bdc2017.R;
import tegar.daily.bdc2017.model.BukaLapakProduk;
import tegar.daily.bdc2017.model.Trendsetters;

/**
 * Created by Acer on 5/21/2017.
 */

public class TrendsettersAdapter extends RecyclerView.Adapter<TrendsettersAdapter.MyViewHolder> {

    ArrayList<Trendsetters> items;
    Context context;

    public TrendsettersAdapter(ArrayList<Trendsetters> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public TrendsettersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspiration, parent, false);
        TrendsettersAdapter.MyViewHolder MyViewHolder = new TrendsettersAdapter.MyViewHolder(itemView, context, items);
        return MyViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_namainspired, tv_deskripsi, tv_professi, tv_sumber;
        public ImageView iv_gambar;
        public LinearLayout ll_item_inspiration;
        ArrayList<Trendsetters> items = new ArrayList<Trendsetters>();
        Context context;

        public MyViewHolder(View view, final Context context, final ArrayList<Trendsetters> items) {
            super(view);
            this.context = context;
            this.items = items;
            tv_namainspired = (TextView) view.findViewById(R.id.tv_namainspired);
            tv_deskripsi = (TextView) view.findViewById(R.id.tv_deskripsi);
            tv_professi = (TextView) view.findViewById(R.id.tv_profesi);
            tv_sumber = (TextView) view.findViewById(R.id.tv_sumber);
            iv_gambar = (ImageView) view.findViewById(R.id.iv_gambar);
            ll_item_inspiration = (LinearLayout) view.findViewById(R.id.ll_item_inspiration);
        }
    }

    @Override
    public void onBindViewHolder(TrendsettersAdapter.MyViewHolder holder, final int position) {
        final Trendsetters trendsetters = items.get(position);
        holder.tv_namainspired.setText(trendsetters.getNama());
        holder.tv_deskripsi.setText(trendsetters.getDeskripsi());
        holder.tv_professi.setText(trendsetters.getProfessi());
        holder.tv_sumber.setText(trendsetters.getSumber());
        Glide.with(context)
                .load(Uri.parse(trendsetters.getGambar()))
                .into(holder.iv_gambar);
        holder.ll_item_inspiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailInspirationActivity.class);
                intent.putExtra("idtrendsetters", trendsetters.getIdtrendsetter());
                intent.putExtra("namainspired", trendsetters.getNama());
                intent.putExtra("professi", trendsetters.getProfessi());
                intent.putExtra("sumber", trendsetters.getSumber());
                intent.putExtra("gambar", trendsetters.getGambar());
                intent.putExtra("deskripsi", trendsetters.getDeskripsi());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}