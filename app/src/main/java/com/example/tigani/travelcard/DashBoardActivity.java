package com.example.tigani.travelcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.tigani.travelcard.Utilites.PreferenceManger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imageview_newCard)ImageView imageViewNewCard;
    @BindView(R.id.imageview_showallcard)ImageView imageViewShowCard;
    @BindView(R.id.imageview_newadmin)ImageView imageviewnewAdmin;


    PreferenceManger preferenceManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        preferenceManger = new PreferenceManger(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imageViewNewCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashBoardActivity.this,CreateNewCardActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.bottomtop, R.anim.none);
            }
        });

        imageViewShowCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashBoardActivity.this,ShowAllCardActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.bottomtop,R.anim.none);
            }
        });

        imageviewnewAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashBoardActivity.this,ShowAllAdminActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.bottomtop,R.anim.none);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    private void logoutUser()
    {
        preferenceManger.Clear();
        preferenceManger.setLogin(false);
        Intent intent = new Intent(DashBoardActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
