package com.harsh1310.g2c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Adapterclass adapter;
    ArrayList<Modelclass> arrayList=new ArrayList<>();
    FirebaseRecyclerOptions<Modelclass> options;
    DatabaseReference serviceref;
    RecyclerView rview;
    FloatingActionButton addbut;
EditText inputfield;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);

setSupportActionBar(toolbar);

        addbut=findViewById(R.id.addbut);
       rview=findViewById(R.id.rview);
     //  inputfield=findViewById(R.id.inputield);
        serviceref=FirebaseDatabase.getInstance().getReference().child("Servicesref");

     addbut.setOnClickListener(v->showdialog());
        options=new FirebaseRecyclerOptions.Builder<Modelclass>().setQuery(serviceref,Modelclass.class).build();
     setuprecyclerview();
    }
    private void setuprecyclerview() {

        rview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapterclass(options,getApplicationContext());
        adapter.notifyDataSetChanged();

        rview.setAdapter( adapter);
      adapter.setOnItemClickListener(new Adapterclass.OnItemClickListener() {
          @Override
          public void onclick(int pos) {
              Intent intent=new Intent(MainActivity.this,Detail_Activity.class);
              startActivity(intent);
          }


      });
    }
    private void adddata() {



        String s=inputfield.getText().toString().trim();
        if(s.length()==0)
        {
            Toast.makeText(MainActivity.this,"Input field is empty",Toast.LENGTH_SHORT).show();
            return;
        }
    Modelclass modelclass=new Modelclass(s);
     serviceref.push().setValue(modelclass).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful())
        {
            Toast.makeText(MainActivity.this,"Added",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
         }
     });
    }

    private void showdialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.add_dialog,null);
        builder.setView(view);
        AlertDialog dialog=  builder.create();
        dialog.show();


        inputfield=view.findViewById(R.id.inputield);
        Button add=view.findViewById(R.id.addcheckitem);
        add.setOnClickListener(v->{

            adddata();
            if(inputfield.getText().toString().trim().length()>0)
            dialog.dismiss();

        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}