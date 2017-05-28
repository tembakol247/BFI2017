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
import tegar.daily.bdc2017.model.ItemTrendsetters;
import tegar.daily.bdc2017.model.Trendsetters;

/**
 * Created by Acer on 5/23/2017.
 */

public class ItemTrendsettersAdapter extends RecyclerView.Adapter<ItemTrendsettersAdapter.MyViewHolder> {

        ArrayList<ItemTrendsetters> items;
        Context context;

public ItemTrendsettersAdapter(ArrayList<ItemTrendsetters> items, Context context) {
        this.items = items;
        this.context = context;
        }

@Override
public ItemTrendsettersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_items, parent, false);
        ItemTrendsettersAdapter.MyViewHolder MyViewHolder = new ItemTrendsettersAdapter.MyViewHolder(itemView, context, items);
        return MyViewHolder;
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_namaitem, tv_hargatermahal, tv_hargatermurah;
    public ImageView iv_gambar;
    LinearLayout ll_item_items;
    ArrayList<ItemTrendsetters> items = new ArrayList<ItemTrendsetters>();
    Context context;

    public MyViewHolder(View view, final Context context, final ArrayList<ItemTrendsetters> items) {
        super(view);
        this.context = context;
        this.items = items;
        tv_namaitem = (TextView) view.findViewById(R.id.tv_namaitem);
        tv_hargatermahal = (TextView) view.findViewById(R.id.tv_hargatermahal);
        tv_hargatermurah = (TextView) view.findViewById(R.id.tv_hargatermurah);
        iv_gambar = (ImageView) view.findViewById(R.id.iv_gambar);
        ll_item_items = (LinearLayout) view.findViewById(R.id.ll_item_items);
    }
}

    @Override
    public void onBindViewHolder(ItemTrendsettersAdapter.MyViewHolder holder, final int position) {
        final ItemTrendsetters itemTrendsetters = items.get(position);
        holder.tv_namaitem.setText(itemTrendsetters.getNamaproduk());
        holder.tv_hargatermahal.setText("Harga Tertinggi : Rp" + itemTrendsetters.getHargatermahal());
        holder.tv_hargatermurah.setText("Harga Terendah : Rp" + itemTrendsetters.getHargatermurah());
        Glide.with(context)
                .load(Uri.parse(itemTrendsetters.getGambar()))
                .into(holder.iv_gambar);
        holder.ll_item_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("idsimilar", itemTrendsetters.getIditems());
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
