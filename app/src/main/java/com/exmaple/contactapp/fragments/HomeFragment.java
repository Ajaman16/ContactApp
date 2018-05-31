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
import com.exmaple.contactapp.adapters.HomeAdapter;
import com.exmaple.contactapp.models.Users;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * Created by root on 14/7/17.
 */

public class HomeFragment extends Fragment {

    View v;
    Context context;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    HomeAdapter adapter;
    RealmResults<Users> list;

    RealmList<Users> dummy = new RealmList<Users>();

    Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();

        realm = Realm.getDefaultInstance();

        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = realm.where(Users.class).findAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new HomeAdapter(context, dummy);
        recyclerView.setAdapter(adapter);

        setupUI();

    }

    public void setupUI() {

        dummy.clear();

        list = realm.where(Users.class).findAll();

        dummy.add(0, new Users("Kisan", "Networks", "9971792703", System.currentTimeMillis()));
        dummy.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
