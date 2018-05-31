package com.exmaple.contactapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exmaple.contactapp.fragments.HistoryFragment;
import com.exmaple.contactapp.fragments.HomeFragment;
import com.exmaple.contactapp.models.Users;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.add_user)
    ImageView addUser;

    Context context;

    HomeFragment homeFragment = new HomeFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        context = this;

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    addUser.setVisibility(View.VISIBLE);
                } else {
                    addUser.setVisibility(View.GONE);
                    historyFragment.setupUI();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.adduser_dialog);
        dialog.setTitle("Add User...");
        dialog.setCancelable(true);

        final EditText firstName = (EditText)dialog.findViewById(R.id.first_name);
        final EditText lastName = (EditText)dialog.findViewById(R.id.last_name);
        final EditText mobile = (EditText)dialog.findViewById(R.id.number);
        Button addButton = (Button)dialog.findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.length() != 0 && lastName.length() != 0 && mobile.length() ==10)
                {

                    try {
                        // Get a Realm instance for this thread

                        Users user = new Users();
                        user.setFirstName(firstName.getText().toString());
                        user.setLastName(lastName.getText().toString());
                        user.setNumber(mobile.getText().toString());
                        user.setTimestamp(System.currentTimeMillis());

                        realm.beginTransaction();
                        realm.copyToRealm(user);
                        realm.commitTransaction();

                        homeFragment.setupUI();
                        dialog.dismiss();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(context, "Please fill all data", Toast.LENGTH_SHORT).show();
                }
            }
        });



        dialog.show();
    }


    public class PageAdapter extends FragmentPagerAdapter {

        private int numOfTabs;

        public PageAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return homeFragment;
                case 1:
                    return historyFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }


}
