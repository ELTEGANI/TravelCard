package com.example.tigani.travelcard.Utilites;

import android.content.Context;
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
import com.example.tigani.travelcard.Admin;
import com.example.tigani.travelcard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class AdminsAdapter extends RecyclerView.Adapter<AdminsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Admin> adminList;
    private List<Admin> adminListFiltered;
    private AdminsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone,id,status,code;
        public ImageView thumbnail;

        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            id = view.findViewById(R.id.id);
            status = view.findViewById(R.id.status);
            thumbnail = view.findViewById(R.id.thumbnail);
            code     = view.findViewById(R.id.code);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onAdminSelected(adminListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public AdminsAdapter(Context context, List<Admin> adminList, AdminsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.adminList = adminList;
        this.adminListFiltered = adminList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        final Admin admin = adminListFiltered.get(position);
        holder.name.setText(admin.getName());
        holder.phone.setText("Phone:"+" "+admin.getPhone());
        holder.id.setText(admin.getId());
        holder.status.setText(admin.getStatus());
        holder.code.setText("  Code:"+" "+admin.getEntrancecode());
        Glide.with(context)
                .load(R.mipmap.ic_launcher_admin)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return adminListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    adminListFiltered = adminList;
                } else {
                    List<Admin> filteredList = new ArrayList<>();
                    for (Admin row : adminList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }

                    adminListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = adminListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                adminListFiltered = (ArrayList<Admin>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface AdminsAdapterListener {
        void onAdminSelected(Admin admin);
    }
}
