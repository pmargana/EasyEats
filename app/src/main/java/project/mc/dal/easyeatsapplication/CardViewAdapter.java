package project.mc.dal.easyeatsapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by harika93 on 2017-11-12.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>  {
    public String[] mDataset;
    static Typeface font;
    Context context;
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CardViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvtinfo_text;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvtinfo_text = (TextView) itemLayoutView.findViewById(R.id.info_text);
            tvtinfo_text.setTypeface(font);
/*
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProductActivity.class);
                    intent.putExtra("slug",slugsOfCatogary[tvtinfo_text.getId()]);
                    // intent.putExtra("slug",idsOfCatogary[tvtinfo_text.getId()]);
                    v.getContext().startActivity(intent);
                }
            });
*/

        }
    }
}
