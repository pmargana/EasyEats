package project.mc.dal.easyeatsapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.mc.dal.easyeatsapplication.sqlitePackage.DBAdapter;
import project.mc.dal.easyeatsapplication.sqlitePackage.SingleProductWishList;

/**
 * Created by harika93 on 2017-11-11.
 */

public class ListAdapterClass  extends RecyclerView.Adapter<ListAdapterClass.ViewHolder> {

    public static boolean star_state=false;
    final boolean tagTrue = true;
    final boolean tagFalse = false;

    List<HashMap> dataSet= new ArrayList<HashMap>();
    List wishListProductIds =new ArrayList();
    Context context;
    DBAdapter adapter;
    public ListAdapterClass(Context context,List<HashMap> dataSet) {
        super();
        this.context=context;
        this.dataSet=dataSet;
        adapter = new DBAdapter(context);
        this.wishListProductIds=adapter.getIdsOfAllSelectedProduct();
    }

    @Override
    public ListAdapterClass.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterClass.ViewHolder holder, int position) {
        // EndangeredItem nature = mItems.get(i);
        HashMap gridItem=dataSet.get(position);
        holder.tvTitle.setText((String) gridItem.get("title"));
        holder.tvTitle.setTag(String.valueOf(gridItem.get("id")));
        this.wishListProductIds.clear();
        this.wishListProductIds=adapter.getIdsOfAllSelectedProduct();
        if (wishListProductIds.contains(gridItem.get("id")))
        {
            holder.img_star.setTag(true);
            holder.img_star.setBackgroundResource(R.drawable.star_fill);

        }
        else {
            holder.img_star.setTag(false);
            holder.img_star.setBackgroundResource(R.drawable.star_empty);
        }

        holder.img_star.setTag(position);//?
        holder.img_star.setId(position);
        String imgUrl = (String)gridItem.get("imageUrl");
        Uri uri = Uri.parse(imgUrl);
        Context context = holder.imgThumbnail.getContext();
        Picasso.with(context).load(uri)
                .into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public ImageView img_star;
        public TextView tvTitle;
        public TextView tvDescpt;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            img_star =  (ImageView)itemView.findViewById(R.id.img_star);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvDescpt=(TextView)itemView.findViewById(R.id.tv_Desc);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                   /* Toast.makeText(v.getContext(), "OnClick Version Whole : " + tvTitle.getTag(),
                            Toast.LENGTH_SHORT).show();
*/
                  /*  Intent intent= new Intent(v.getContext(),SelectedProductActivity.class);
                    intent.putExtra("id",(String)tvTitle.getTag());
                    v.getContext().startActivity(intent);*/

                }
            });


            img_star.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println("getTag in List:"+v.getTag());
                    if((Boolean)v.getTag().equals(tagTrue)) {
                   // if(true){
                        v.setTag(false);
                        v.setBackgroundResource(R.drawable.star_empty);
                        if(CommonListActivity.isWishListClicked)
                        {
                            //ProductActivity.isWishListClicked=false;
                            // WishListActivity.wishListCountTv.setText(String.valueOf(--ProductActivity.wishListCount));
                            /*if(ProductActivity.wishListCountTv!=null) {
                                List wishListProductIds=adapter.getIdsOfAllSelectedProduct();
                                int count= wishListProductIds.size();
                                ProductActivity.wishListCountTv.setText(String.valueOf(--count));
                            }*/
                            if(CommonListActivity.wishListCountTv!=null)
                                CommonListActivity.wishListCountTv.setText(String.valueOf(--CommonListActivity.wishListCount));
                            DBAdapter adapter = new DBAdapter(context);
                            HashMap gridItem=dataSet.get(v.getId());
                            int effetedRowNo=adapter.delete((Integer)gridItem.get("id"));//no of effected row,0 otherwise,1 for all
                            System.out.println("effetedRowNo: " + effetedRowNo);
                            dataSet.remove(img_star.getId());
                            notifyDataSetChanged();
                        }
                        else
                        {
                           /* if(ProductActivity.wishListCountTv!=null)
                            {
                                List wishListProductIds=adapter.getIdsOfAllSelectedProduct();
                                int cnt= wishListProductIds.size();
                                ProductActivity.wishListCountTv.setText(String.valueOf(--cnt));
                            }*/
                            if(CommonListActivity.wishListCountTv!=null)
                                CommonListActivity.wishListCountTv.setText(String.valueOf(--CommonListActivity.wishListCount));
                            DBAdapter adapter = new DBAdapter(context);
                            HashMap gridItem=dataSet.get(v.getId());
                            int effetedRowNo=adapter.delete((Integer)gridItem.get("id"));//no of effected row,0 otherwise,1 for all
                            System.out.println("effetedRowNo: "+effetedRowNo);
                            CommonListActivity.wishListCountTv.setText(String.valueOf(--CommonListActivity.wishListCount));

                        }
                        // WishListActivity.wishListCountTv.setText(String.valueOf(--WishListActivity.wishListCount));

                    }
                    else{
                        v.setTag(true);
                        v.setBackgroundResource(R.drawable.star_fill);
                        if(CommonListActivity.isWishListClicked)
                        {
                            //ProductActivity.isWishListClicked=false;
                            // WishListActivity.wishListCountTv.setText(String.valueOf(++ProductActivity.wishListCount));
                            CommonListActivity.wishListCountTv.setText(String.valueOf(++CommonListActivity.wishListCount));


                        }
                        else
                        {
                           /* if(ProductActivity.wishListCountTv!=null) {
                                List wishListProductIds = adapter.getIdsOfAllSelectedProduct();
                                int cnutnt = wishListProductIds.size();
                                ProductActivity.wishListCountTv.setText(String.valueOf(++cnutnt));
                            }*/
                            if(CommonListActivity.wishListCountTv!=null) {
                                CommonListActivity.wishListCountTv.setText(String.valueOf(++CommonListActivity.wishListCount));
                            }
                        }

                        //WishListActivity.wishListCountTv.setText(String.valueOf(++WishListActivity.wishListCount));
                        int id=v.getId();
                        HashMap gridItem=dataSet.get(v.getId());
                        DBAdapter adapter = new DBAdapter(context);
                        SingleProductWishList product = new SingleProductWishList();
                        product.product_ID = (String) gridItem.get("id");
                        product.imageurl = (String) gridItem.get("Image");
                        product.title = (String) gridItem.get("title");
                        product.description = (String) gridItem.get("Description");

                        int rowID=adapter.insert(product);//the row ID of the newly inserted row, or -1 if an error occurred
                        System.out.println("rowID: "+rowID);

                        dataSet=adapter.getProductList();
                        System.out.println("dataSet: " +dataSet.toString());
                        //i need tag for selsected not selected ,id for accessing the dataset and i need ?(rowid) to delete the records????????????????????
                    }

                }
            });


        }
}

}
