package com.exmaple.contactapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmaple.contactapp.R;
import com.exmaple.contactapp.models.History;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;


public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    RealmResults<History> list;



    public HistoryAdapter(Context context, RealmResults<History> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_feed_adapter, parent, false);
        return new HistoryAdapter.ViewHolderMain(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(list.size() != 0)
        {
            ViewHolderMain mHolder = (ViewHolderMain) holder;

            mHolder.userName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());

            mHolder.otp.setText("OTP: "+list.get(position).getOtp());

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(list.get(position).getTimestamp(),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            mHolder.timestamp.setText(timeAgo);

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

        @BindView(R.id.time)
        TextView timestamp;

        @BindView(R.id.otp)
        TextView otp;


        public ViewHolderMain(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
