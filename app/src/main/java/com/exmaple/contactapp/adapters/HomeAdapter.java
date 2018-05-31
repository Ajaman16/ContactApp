package com.exmaple.contactapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmaple.contactapp.ContactInfo;
import com.exmaple.contactapp.R;
import com.exmaple.contactapp.models.Users;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    RealmList<Users> list;



    public HomeAdapter(Context context, RealmList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_feed_adapter, parent, false);
        return new HomeAdapter.ViewHolderMain(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(list.size() != 0)
        {
            ViewHolderMain mHolder = (ViewHolderMain) holder;

            mHolder.userName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());

            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ContactInfo.class);
                    i.putExtra("data", list.get(position));
                    context.startActivity(i);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderMain extends RecyclerView.ViewHolder
    {

        @BindView(R.id.user_name)
        TextView userName;


        public ViewHolderMain(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
