package com.a4nesia.whatsappclone;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactActivity extends AppCompatActivity {
    AlertDialog addContactDialog;
    EditText editSearch;
    CircleImageView imgSearch;
    TextView textSearch;
    RelativeLayout layoutSearch;
    ProgressBar loading;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View alertView = getLayoutInflater().inflate(R.layout.dialog_add_contact,null);
        editSearch = (EditText)alertView.findViewById(R.id.search_edit);
        imgSearch = (CircleImageView)alertView.findViewById(R.id.search_img);
        textSearch = (TextView)alertView.findViewById(R.id.search_nama);
        loading = (ProgressBar)alertView.findViewById(R.id.loading);
        layoutSearch = (RelativeLayout)alertView.findViewById(R.id.search_layout);
        btnSearch = (Button)alertView.findViewById(R.id.search_add);
        addContactDialog = new AlertDialog.Builder(this)
                .setTitle("Add New Contact")
                .setView(alertView)
                .create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_contact){
            editSearch.setText("");
            layoutSearch.setVisibility(View.GONE);
            addContactDialog.show();
        }
        else if(id==android.R.id.home){
            super.onBackPressed();
        }
        return true;
    }
}
