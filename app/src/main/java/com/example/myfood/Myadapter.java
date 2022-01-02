package com.example.myfood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Myadapter extends FirebaseRecyclerAdapter<MainModel,Myadapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Myadapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {
        holder.name.setText((model.getNomRes()));
        holder.type.setText(model.getTypeRes());
        Glide.with(holder.img.getContext())
                .load(model.getImageRes())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog((holder.img.getContext()))
                        .setContentHolder(new ViewHolder(R.layout.update_res))
                        .setExpanded(true,1200)
                        .create();


                View view1 = dialogPlus.getHolderView();

                EditText name = view1.findViewById(R.id.txtName);
                EditText type = view1.findViewById(R.id.txttype);
                EditText image = view1.findViewById(R.id.txtimg);

                Button btnUpdate = view1.findViewById(R.id.btnUpdate);

                name.setText(model.getNomRes());
                type.setText(model.getTypeRes());
                image.setText(model.getImageRes());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("nomRes",name.getText().toString());
                        map.put("typeRes",type.getText().toString());
                        map.put("imageRes",image.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("restaurant")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(@NonNull Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Data Update successfully",Toast.LENGTH_SHORT).show();;
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(),"Error  while Updating",Toast.LENGTH_SHORT).show();;
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });


            }
        });

        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("restaurant")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,type;
        Button btnEdit ,btnDel;


        public myViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.im1);
            name=itemView.findViewById(R.id.nom1);
            type=itemView.findViewById(R.id.typeR);

            btnEdit=itemView.findViewById(R.id.edit);
            btnDel=itemView.findViewById(R.id.del);


        }
    }





}
