package com.example.tigani.travelcard;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tigani.travelcard.Retrofit.AddCheckAdminPojo;
import com.example.tigani.travelcard.Retrofit.ApiAddCheckAdmin;
import com.example.tigani.travelcard.Retrofit.ApiUpdateAdminChecked;
import com.example.tigani.travelcard.Retrofit.UpdateCheckAdminResponse;
import com.example.tigani.travelcard.Utilites.PreferenceManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tigani.travelcard.Retrofit.Client.BASE_URL;

public class UpdateAdminActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.EditText_name)AppCompatEditText EditTextname;
    @BindView(R.id.EditText_phone)AppCompatEditText EditTextphone;
    @BindView(R.id.switchcomat)SwitchCompat switchCompatAutherization;
    @BindView(R.id.addadmin_button)AppCompatButton appCompatButtonAddAdmin;
    @BindView(R.id.constraint)ConstraintLayout constraintLayout;
    String AdminName,CheckAdminPhone, updatedname, updateadminPhone,id;
    boolean autherization, Updatedautherizationstatus;
    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        constraintLayout.setOnClickListener(null);
        preferenceManger = new PreferenceManger(this);


        Intent intent                               = getIntent();
        id                                          = intent.getStringExtra("id");
        AdminName                                   = intent.getStringExtra("updatedname");
        CheckAdminPhone                             = intent.getStringExtra("checkadminphone");
        autherization                               = Boolean.parseBoolean(intent.getStringExtra("autherization"));

        EditTextname.setText(AdminName);
        EditTextphone.setText(CheckAdminPhone);
        switchCompatAutherization.setChecked(autherization);


        appCompatButtonAddAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 updatedname = EditTextname.getText().toString().trim();
                 Updatedautherizationstatus = switchCompatAutherization.isChecked();
                 updateadminPhone = EditTextphone.getText().toString().trim();



                if(updatedname.isEmpty() || updateadminPhone.isEmpty())
                {
                    Snackbar snackbar = Snackbar.make(constraintLayout, "Please Fill Empty Field", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#f20707"));
                    textView.setTextSize(18);
                    snackbar.show();
                    return;
                }
                if(updateadminPhone.length() < 13)
                {
                    EditTextphone.setError("CheckAdminPhone Number Less than 13 number");
                }
                else
                {
                    AddCheckAdmin();
                }
            }
        });

    }

    public void AddCheckAdmin()
    {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUpdateAdminChecked apiUpdateAdminChecked = retrofit.create(ApiUpdateAdminChecked.class);
        Call<UpdateCheckAdminResponse> call = apiUpdateAdminChecked.getResponse(id,updatedname,updateadminPhone,String.valueOf(Updatedautherizationstatus));
        call.enqueue(new Callback<UpdateCheckAdminResponse>()
        {
            @Override
            public void onResponse(Call<UpdateCheckAdminResponse> call, retrofit2.Response<UpdateCheckAdminResponse> response)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onResponse: "+response.body().isError());
                if(response.body().isError())
                {
                    Toast.makeText(UpdateAdminActivity.this,"Done Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpdateAdminActivity.this,"Failed Updated...Try Later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateCheckAdminResponse> call, Throwable t)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onFailure: "+t.toString());
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
        Intent intent = new Intent(UpdateAdminActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
