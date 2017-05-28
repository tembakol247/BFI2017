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

import tegar.daily.bdc2017.ItemActivity;
import tegar.daily.bdc2017.R;
import tegar.daily.bdc2017.model.Hashtag;
import tegar.daily.bdc2017.model.ItemTrendsetters;

/**
 * Created by Acer on 5/28/2017.
 */

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.MyViewHolder> {

    ArrayList<Hashtag> items;
    Context context;

    public HashtagAdapter(ArrayList<Hashtag> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public HashtagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trendsetter_recommend, parent, false);
        HashtagAdapter.MyViewHolder MyViewHolder = new HashtagAdapter.MyViewHolder(itemView, context, items);
        return MyViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nama, tv_hashtag, tv_waktu;
        ArrayList<Hashtag> items = new ArrayList<Hashtag>();
        Context context;

        public MyViewHolder(View view, final Context context, final ArrayList<Hashtag> items) {
            super(view);
            this.context = context;
            this.items = items;
            tv_nama = (TextView) view.findViewById(R.id.tv_nama);
            tv_hashtag = (TextView) view.findViewById(R.id.tv_hashtag);
            tv_waktu = (TextView) view.findViewById(R.id.tv_waktu);
        }
    }

    @Override
    public void onBindViewHolder(HashtagAdapter.MyViewHolder holder, final int position) {
        final Hashtag hashtag = items.get(position);
        holder.tv_nama.setText(hashtag.getNama());
        holder.tv_hashtag.setText("#" + hashtag.getHastag());
        //holder.tv_waktu.setText(hashtag.getWaktu());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
