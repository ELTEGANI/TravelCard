package com.example.tigani.travelcard.Utilites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tigani.travelcard.CardDetailesActivity;
import com.example.tigani.travelcard.R;
import com.example.tigani.travelcard.Retrofit.CardItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigani on 11/13/2017.
 */

public class Cardadapter extends RecyclerView.Adapter<Cardadapter.MyHolder> implements Filterable {


    private List<CardItems> cardlist;
    private Context context;
    private List<CardItems> mFilteredList;

    public Cardadapter(List<CardItems> cardlists, Context context)
    {
        this.cardlist = cardlists;
        this.context = context;
        this.mFilteredList = cardlist;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position)
    {
        holder.name.setText(mFilteredList.get(position).getName());
        holder.destination.setText(mFilteredList.get(position).getDestination());
        holder.passport.setText(mFilteredList.get(position).getPassport());
        holder.idcode.setText(mFilteredList.get(position).getIdcode());
        holder.issuedate.setText(mFilteredList.get(position).getIssuedate());
        holder.status.setText(mFilteredList.get(position).getStatus());
        holder.holderphone.setText(mFilteredList.get(position).getHolder_phone());
        final String image = mFilteredList.get(position).getPersonimage();
        if(holder.status.getText().toString().equals("false"))
        {
            holder.cardView.setCardBackgroundColor(Color.RED);
        }
        else
        {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
        Glide.with(context).load(image).apply(RequestOptions.circleCropTransform()).into(holder.personImage);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent  = new Intent(context,CardDetailesActivity.class);
                intent.putExtra("identificationcode",holder.idcode.getText().toString());
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("personimage",image);
                intent.putExtra("destination",holder.destination.getText().toString());
                intent.putExtra("passport",holder.passport.getText().toString());
                intent.putExtra("issuedate",holder.issuedate.getText().toString());
                intent.putExtra("status",holder.status.getText().toString());
                intent.putExtra("holderphone",holder.holderphone.getText().toString());
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter()
    {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    mFilteredList = cardlist;
                } else {
                    List<CardItems> filteredList = new ArrayList<>();
                    for (CardItems carditems : cardlist)
                    {
                        if (carditems.getName().toLowerCase().contains(charSequence) ||carditems.getDestination().toLowerCase().contains(charSequence) || carditems.getIdcode().toLowerCase().contains(charSequence))
                        {
                            filteredList.add(carditems);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                mFilteredList = (List<CardItems>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView name,destination,passport,issuedate,idcode,status,holderphone;
        CardView cardView;
        ImageView personImage;

        public MyHolder(View itemView)
        {
            super(itemView);
            name        = (TextView) itemView.findViewById(R.id.textView_name);
            destination = (TextView) itemView.findViewById(R.id.textView_destination);
            personImage = (ImageView) itemView.findViewById(R.id.imageView_person);
            passport = (TextView) itemView.findViewById(R.id.textView_passport);
            idcode = (TextView) itemView.findViewById(R.id.textView_identificationcode);
            issuedate = (TextView) itemView.findViewById(R.id.textView_issuedate);
            status = (TextView) itemView.findViewById(R.id.textView_status);
            holderphone = (TextView) itemView.findViewById(R.id.textView_holderphone);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }


    }
}
