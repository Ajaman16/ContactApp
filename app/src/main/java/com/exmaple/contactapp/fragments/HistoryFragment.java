package com.exmaple.contactapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmaple.contactapp.R;
import com.exmaple.contactapp.adapters.HistoryAdapter;
import com.exmaple.contactapp.models.History;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by root on 14/7/17.
 */

public class HistoryFragment extends Fragment {

    View v;
    Context context;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    HistoryAdapter adapter;
    RealmResults<History> list;

    Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_history, container, false);

        context = getContext();

        realm = Realm.getDefaultInstance();


        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = realm.where(History.class).findAll().sort("timestamp", Sort.DESCENDING);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new HistoryAdapter(context, list);
        recyclerView.setAdapter(adapter);

        setupUI();

    }

    public void setupUI() {

        list = realm.where(History.class).findAll().sort("timestamp", Sort.DESCENDING);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
