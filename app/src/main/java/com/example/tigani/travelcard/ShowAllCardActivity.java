package com.example.tigani.travelcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tigani.travelcard.Retrofit.ApiRequestCards;
import com.example.tigani.travelcard.Retrofit.Card;
import com.example.tigani.travelcard.Retrofit.CardItems;
import com.example.tigani.travelcard.Retrofit.Cardsresponse;
import com.example.tigani.travelcard.Utilites.Cardadapter;
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


public class ShowAllCardActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.toolbar)Toolbar toolbar;

    CardItems cardItems;
    String Id,name,destination,Passport,issuedate,personimage,idcode,status,holderphone;
    List<CardItems> cardlist;
    private Cardadapter cardadapter;
    PreferenceManger preferenceManger;
    List<Card> cards;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_card);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        preferenceManger = new PreferenceManger(this);

        cardlist = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



            RequestedCard(preferenceManger.GetPhone());








    }


    public void RequestedCard(String phone)
    {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequestCards apiRequestCards = retrofit.create(ApiRequestCards.class);
        Call<Cardsresponse> call = apiRequestCards.getRequestedCards(phone);
        call.enqueue(new Callback<Cardsresponse>()
        {
            @Override
            public void onResponse(Call<Cardsresponse>call, Response <Cardsresponse> response)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onResponse: "+response.code());
                cards = response.body().getCards();

                if(response.body().getCards() != null)
                {
                    for (int i = 0; i < cards.size() ; i++)
                    {
                        cardItems = new CardItems();
                        name = cards.get(i).getName();
                        destination = cards.get(i).getDestination();
                        Passport  = cards.get(i).getPassportNo();
                        Id  = cards.get(i).getCardId();
                        issuedate  = cards.get(i).getIssuedate();
                        idcode     = cards.get(i).getIdentificationCode();
                        personimage  = cards.get(i).getPersonImage();
                        status       = cards.get(i).getStatus();
                        holderphone  = cards.get(i).getHolder_phone();
                        cardItems.setName(name);
                        cardItems.setDestination(destination);
                        cardItems.setPassport(Passport);
                        cardItems.setId(Id);
                        cardItems.setIssuedate(issuedate);
                        cardItems.setIdcode(idcode);
                        cardItems.setPersonimage(personimage);
                        cardItems.setStatus(status);
                        cardItems.setHolder_phone(holderphone);
                        cardlist.add(cardItems);
                    }
                }
                else
                {
                    Toast.makeText(ShowAllCardActivity.this, "No Cards Found", Toast.LENGTH_SHORT).show();
                }
                cardadapter = new Cardadapter(cardlist,getApplicationContext());
                 RecyclerView.LayoutManager recyce = new LinearLayoutManager(ShowAllCardActivity.this);
                 recyclerView.setAdapter(cardadapter);

            }

            @Override
            public void onFailure(Call<Cardsresponse> call, Throwable t)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onFailure: "+t.toString());
                AlertMsg("Check Your Internet Connection..and Try Again");
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchview, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_signout:
                logoutUser();
                break;

            default:
                return false;


        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                cardadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                cardadapter.getFilter().filter(query);
                return true;
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ShowAllCardActivity.this,DashBoardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftrightin,R.anim.leftrightout);
    }

    protected void onResume()
    {
            super.onResume();
            if(cardlist.size() > 0)
            {   cardlist.clear();
                RequestedCard(preferenceManger.GetPhone());
            }
    }

    private void logoutUser()
    {
        preferenceManger.Clear();
        preferenceManger.setLogin(false);
        Intent intent = new Intent(ShowAllCardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void AlertMsg(String msg)
    {
        builder = new AlertDialog.Builder(ShowAllCardActivity.this);
        builder.setMessage(msg);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Intent intent = new Intent(ShowAllCardActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.bottomtop,R.anim.none);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}