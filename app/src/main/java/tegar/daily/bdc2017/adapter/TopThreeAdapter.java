package tegar.daily.bdc2017.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tegar.daily.bdc2017.R;
import tegar.daily.bdc2017.model.Hashtag;
import tegar.daily.bdc2017.model.TopThree;

/**
 * Created by Acer on 5/28/2017.
 */

public class TopThreeAdapter extends RecyclerView.Adapter<TopThreeAdapter.MyViewHolder> {

    ArrayList<TopThree> items;
    Context context;

    public TopThreeAdapter(ArrayList<TopThree> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public TopThreeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_three, parent, false);
        TopThreeAdapter.MyViewHolder MyViewHolder = new TopThreeAdapter.MyViewHolder(itemView, context, items);
        return MyViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_hastag, tv_jumlah;
        ArrayList<TopThree> items = new ArrayList<TopThree>();
        Context context;

        public MyViewHolder(View view, final Context context, final ArrayList<TopThree> items) {
            super(view);
            this.context = context;
            this.items = items;
            tv_hastag = (TextView) view.findViewById(R.id.tv_hashtag);
            tv_jumlah = (TextView) view.findViewById(R.id.tv_jumlah);
        }
    }

    @Override
    public void onBindViewHolder(TopThreeAdapter.MyViewHolder holder, final int position) {
        final TopThree topThree = items.get(position);
        holder.tv_hastag.setText("#" + topThree.getHashtag());
        holder.tv_jumlah.setText(topThree.getJumlah());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

