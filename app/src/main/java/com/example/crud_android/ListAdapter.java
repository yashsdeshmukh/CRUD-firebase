package com.example.crud_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crud_android.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private ArrayList<DBHelper> arrayList;
    private Context context;
    private DBHelper dbHelper;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ListAdapter(ArrayList<DBHelper> arrayList,Context  context) {
        this.arrayList = arrayList;
        this.context= context;
    }
    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_items,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        Intent i = new Intent(context, SecondActivity.class);
        String s1 = arrayList.get(position).getUserName();
        holder.textList.setText(s1);
        holder.editList_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = arrayList.get(position);
                i.putExtra("Button","Edit");
                i.putExtra("userName",dbHelper.getUserName());
                i.putExtra("email",dbHelper.getEmail());
                i.putExtra("number",dbHelper.getNumber());
                context.startActivity(i);
                MainActivity.Fa.finish();
            }
        });

        holder.readList_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = arrayList.get(position);
                i.putExtra("Button","Read");
                i.putExtra("userName",dbHelper.getUserName());
                i.putExtra("email",dbHelper.getEmail());
                i.putExtra("number",dbHelper.getNumber());
                context.startActivity(i);
                MainActivity.Fa.finish();
            }
        });
        holder.deleteList_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = arrayList.get(position);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference =firebaseDatabase.getReference("Users");
                String temp =dbHelper.getUserName();
                databaseReference.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context,"Data Removed",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Failed to remove data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                MainActivity.cleanList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ListHolder extends RecyclerView.ViewHolder{
        private TextView textList;
        private Button editList_items,deleteList_items,readList_item;
        public ListHolder(@NonNull View itemView ) {
            super(itemView);
            textList =(TextView) itemView.findViewById(R.id.textItem);
            editList_items =(Button) itemView.findViewById(R.id.editList_items);
            deleteList_items = (Button) itemView.findViewById(R.id.deleteList_items);
            readList_item = (Button) itemView.findViewById(R.id.readList_items);
        }

    }
}