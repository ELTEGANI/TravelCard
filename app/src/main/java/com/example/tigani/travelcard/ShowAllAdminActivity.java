package com.example.tigani.travelcard;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tigani.travelcard.Retrofit.AdminPojo;
import com.example.tigani.travelcard.Retrofit.AdminsResponse;
import com.example.tigani.travelcard.Retrofit.ApiRequestedAdmin;
import com.example.tigani.travelcard.Utilites.AdminsAdapter;
import com.example.tigani.travelcard.Utilites.PreferenceManger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tigani.travelcard.Retrofit.Client.BASE_URL;

public class ShowAllAdminActivity extends AppCompatActivity implements AdminsAdapter.AdminsAdapterListener {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.recycler_view)RecyclerView recycler_view;
    @BindView(R.id.floatingActionButton2)FloatingActionButton floatingActionButton;
    PreferenceManger preferenceManger;
    private List<AdminPojo> adminpojo;
    private AdminsAdapter mAdapter;
    private SearchView searchView;
    String Id,Name,Phone,Status,entrance_code;
    private List<Admin> adminlist;
    Admin admins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_admin);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        preferenceManger = new PreferenceManger(this);

        adminlist = new ArrayList<>();
        mAdapter = new AdminsAdapter(this, adminlist,this);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recycler_view.setAdapter(mAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAllAdminActivity.this,AddNewAdminActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.bottomtop,R.anim.none);
            }
        });


        RequestedAdmin(preferenceManger.GetPhone());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchview2, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query)
            {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }
        if (id == R.id.menu_signout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        Intent intent = new Intent(ShowAllAdminActivity.this,DashBoardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftrightin,R.anim.leftrightout);
        super.onBackPressed();
    }

    private void logoutUser()
    {
        preferenceManger.Clear();
        preferenceManger.setLogin(false);
        Intent intent = new Intent(ShowAllAdminActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onResume()
    {
        super.onResume();
        if(adminlist.size() > 0)
        {   adminlist.clear();
            RequestedAdmin(preferenceManger.GetPhone());
        }
    }


    @Override
    public void onAdminSelected(Admin admin)
    {
        Intent intent  = new Intent(this,UpdateAdminActivity.class);
        intent.putExtra("id",admin.getId());
        intent.putExtra("updatedname",admin.getName());
        intent.putExtra("checkadminphone",admin.getPhone());
        intent.putExtra("autherization",admin.getStatus());
        startActivity(intent);
    }


    public void RequestedAdmin(String phone)
    {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequestedAdmin apiRequestedAdmin = retrofit.create(ApiRequestedAdmin.class);
        Call<AdminsResponse> call = apiRequestedAdmin.getRequestedAdmins(phone);
        call.enqueue(new Callback<AdminsResponse>()
        {
            @Override
            public void onResponse(Call<AdminsResponse>call, Response<AdminsResponse> response)
            {
                progressBar.setVisibility(View.GONE);
                adminpojo = response.body().getAdmins();
                if(response.body().getAdmins() != null)
                {
                    for (int i = 0; i < adminpojo.size(); i++)
                    {
                        admins = new Admin();
                        Id       = adminpojo.get(i).getId();
                        Name     = adminpojo.get(i).getName();
                        Phone    = adminpojo.get(i).getCheckadminphone();
                        Status   = adminpojo.get(i).getStatus();
                        entrance_code  = adminpojo.get(i).getEntrance_code();
                        admins.setId(Id);
                        admins.setName(Name);
                        admins.setPhone(Phone);
                        admins.setStatus(Status);
                        admins.setEntrancecode(entrance_code);
                        adminlist.add(admins);
                    }
                }
                else
                {
                    Toast.makeText(ShowAllAdminActivity.this, "No Admin Added", Toast.LENGTH_SHORT).show();

                }
                // refreshing recycler view
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AdminsResponse> call, Throwable t)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onFailure: "+t.toString());
            }

        });
    }


}
