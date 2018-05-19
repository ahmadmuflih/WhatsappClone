package com.a4nesia.whatsappclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a4nesia.whatsappclone.models.Data;
import com.a4nesia.whatsappclone.models.User;
import com.a4nesia.whatsappclone.services.APIService;
import com.a4nesia.whatsappclone.tools.Preferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView textNama, textStatus, textNomor;
    CircleImageView imgFoto;
    AlertDialog editNameDialog;
    String TAG = "WA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNama = (TextView)findViewById(R.id.txt_nama);
        textStatus = (TextView)findViewById(R.id.txt_status);
        textNomor = (TextView) findViewById(R.id.txt_telpon);
        imgFoto = (CircleImageView)findViewById(R.id.foto);

        String nama = Preferences.getStringPreference("nama",getApplicationContext());
        String status = Preferences.getStringPreference("status",getApplicationContext());
        String no_telp = Preferences.getStringPreference("no_telp",getApplicationContext());
        textNama.setText(nama);
        textStatus.setText(status);
        textNomor.setText(no_telp);
        String username = Preferences.getStringPreference("username",getApplicationContext());
        final Call<User> userCall = APIService.service.getUser(username);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("WA","Berhasil");
                    User userResponse = response.body();
                    if(userResponse.getStatus().equals("success")){
                        Data user = userResponse.getData();
                        textNama.setText(user.getNama());
                        textStatus.setText(user.getStatus());
                        textNomor.setText(user.getNoTelp());

                        Preferences.setStringPreference("nama",user.getNama(),getApplicationContext());
                        Preferences.setStringPreference("status",user.getStatus(),getApplicationContext());
                        Preferences.setStringPreference("no_telp",user.getNoTelp(),getApplicationContext());
                        Picasso.get()
                                .load(user.getImg())
                                .into(imgFoto);
                    }
                }
                else{
                    Log.e("WA",response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("WA",t.getMessage());
            }
        });



//        Call<User> userCall = APIService.service.getUser("baso");
//        userCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(response.isSuccessful()){
//                    User user= response.body();
//                    Toast.makeText(MainActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
//                    if(user.getStatus().equals("success")){
//                        txtNama.setText(user.getData().getNama());
//                        txtStatus.setText(user.getData().getStatus());
//                        txtPhone.setText(user.getData().getNoTelp());
//                        Picasso.get()
//                                .load(user.getData().getImg())
//                                .into(img);
//                    }
//                    Log.e(TAG,"success");
//                }else{
//                    Log.e(TAG,"failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.e(TAG,"ERROR");
//                Log.e(TAG,call.toString());
//                Log.e(TAG,t.getMessage());
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.show_contact){
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
        }
        else if(id==R.id.logout){
            Preferences.setBooleanPreference("login",false,getApplicationContext());
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        return true;
    }
    public void editName(View view){
        View alertView = getLayoutInflater().inflate(R.layout.dialog_edit_name,null);
        final EditText editNama = (EditText)alertView.findViewById(R.id.edit_name);
        String nama = Preferences.getStringPreference("nama",getApplicationContext());
        editNama.setText(nama);
        editNameDialog = new AlertDialog.Builder(this)
                .setTitle("Edit Name")
                .setView(alertView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = editNama.getText().toString().trim();
                        if(newName.equals("")){
                            Toast.makeText(MainActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Preferences.setStringPreference("nama", newName, getApplicationContext());
                            textNama.setText(newName);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        editNameDialog.show();
    }
}
