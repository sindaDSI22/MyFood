package com.example.myfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ajoutRestaurant extends AppCompatActivity {
    EditText name,type,image;
    Button add,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_restaurant);

        name=findViewById(R.id.txtName);
        type=findViewById(R.id.txttype);
        image=findViewById(R.id.txtimg);

        add=findViewById(R.id.btnSave);
        back=findViewById(R.id.btnBack);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("nomRes",name.getText().toString());
        map.put("typeRes",type.getText().toString());
        map.put("imageRes",image.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("restaurant")
                .push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Toast.makeText(ajoutRestaurant.this,"Insert successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ajoutRestaurant.this,"Error while Insertion !!!",Toast.LENGTH_SHORT).show();

                    }
                });

    }
    private void clearAll()
    {
        name.setText("");
        type.setText("");
        image.setText("");
    }
}