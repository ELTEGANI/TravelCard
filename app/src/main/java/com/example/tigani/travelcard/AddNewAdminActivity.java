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
import com.example.tigani.travelcard.Retrofit.ApiServiceRegisteration;
import com.example.tigani.travelcard.Retrofit.ResgisterationPojo;
import com.example.tigani.travelcard.Utilites.PreferenceManger;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tigani.travelcard.Retrofit.Client.BASE_URL;

public class AddNewAdminActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)ProgressBar progressBar;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.EditText_name)AppCompatEditText EditTextname;
    @BindView(R.id.EditText_phone)AppCompatEditText EditTextphone;
    @BindView(R.id.switchcomat)SwitchCompat switchCompatAutherization;
    @BindView(R.id.addadmin_button)AppCompatButton appCompatButtonAddAdmin;
    @BindView(R.id.constraint)ConstraintLayout constraintLayout;
    @BindView(R.id.ccp)CountryCodePicker ccp;
    String Name, CheckAdminPhone,AdminPhone;
    boolean autherizationstatus;
    PreferenceManger preferenceManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_admin);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ccp.setCountryForNameCode("SD");
        constraintLayout.setOnClickListener(null);

        preferenceManger = new PreferenceManger(this);

        appCompatButtonAddAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Name                 = EditTextname.getText().toString().trim();
                autherizationstatus  = switchCompatAutherization.isChecked();
                AdminPhone           = preferenceManger.GetPhone();

                ccp.registerCarrierNumberEditText(EditTextphone);
                CheckAdminPhone = ccp.getFullNumberWithPlus();

                if(Name.isEmpty() || CheckAdminPhone.isEmpty())
                {
                    Snackbar snackbar = Snackbar.make(constraintLayout, "Please Fill Empty Field", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#f20707"));
                    textView.setTextSize(18);
                    snackbar.show();
                    return;
                }
                if(CheckAdminPhone.length() < 13)
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
        ApiAddCheckAdmin apiAddCheckAdmin = retrofit.create(ApiAddCheckAdmin.class);
        Call<AddCheckAdminPojo> call = apiAddCheckAdmin.getResponse(Name,CheckAdminPhone,AdminPhone,String.valueOf(autherizationstatus));
        call.enqueue(new Callback<AddCheckAdminPojo>()
        {
            @Override
            public void onResponse(Call<AddCheckAdminPojo> call, retrofit2.Response<AddCheckAdminPojo> response)
            {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", "onResponse: "+response.body().getErrorMsg());

                if(response.body().isError())
                {
                    EditTextname.setText("");
                    EditTextphone.setText("");
                    Toast.makeText(AddNewAdminActivity.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AddNewAdminActivity.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCheckAdminPojo> call, Throwable t)
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
        Intent intent = new Intent(AddNewAdminActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(AddNewAdminActivity.this,ShowAllAdminActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftrightin,R.anim.leftrightout);
    }

}
