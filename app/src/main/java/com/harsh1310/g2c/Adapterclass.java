package com.harsh1310.g2c;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adapterclass extends FirebaseRecyclerAdapter<Modelclass,Adapterclass.NoteHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    DatabaseReference serviceref;
    Context con;
    EditText e;
    public Adapterclass(@NonNull FirebaseRecyclerOptions<Modelclass> options, Context con) {

        super(options);
        this.con=con;
    }
        public   OnItemClickListener ItemClickListener;
    public interface OnItemClickListener
    {
        void onclick(int pos);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.ItemClickListener = (OnItemClickListener) onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull Adapterclass.NoteHolder holder, int position, @NonNull Modelclass model) {
        holder.serivcename.
                setText(model.getServicename());
       serviceref=FirebaseDatabase.getInstance().getReference().child("Servicesref");
         String id=getRef(position).getKey();

       holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               Log.d("check","click");
               AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
               View dialogView= LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.delete_dialog,null);
               TextView text;
               Button del;

               text=dialogView.findViewById(R.id.text);
               del=dialogView.findViewById(R.id.del);
               builder.setView(dialogView);
               builder.setCancelable(true);
               //builder.show();
              AlertDialog alertDialog=builder.create();
              alertDialog.show();
               del.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       serviceref.child(id).removeValue();
                       alertDialog.dismiss();
                   }
               });
             /*  AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
               builder.setMessage("Delete this data?");

               //   Button ok=view.findViewById(R.id.cancel);
               builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   @Override public void onClick(DialogInterface dialog, int arg1) {
                  serviceref=FirebaseDatabase.getInstance().getReference().child("Servicesref");

                  String id=getRef(position).getKey();
                  serviceref.child(id).removeValue();
                   }
               });

               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override public void onClick(DialogInterface dialog, int arg1) {
                       dialog.dismiss();
                   }
               });

               AlertDialog alert = builder.create();
               alert.show();*/
               return  true;
           }
       });
       /* holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(con,AddnotesActivity.class);
                intent.putExtra("Msg",holder.cardView.getCardBackgroundColor().toString());
                v.getContext().startActivity(intent);
            }
        });
       /* holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesref=   FirebaseDatabase.getInstance().getReference().child("Notesref");
                String id=getRef(position).getKey();
                notesref.child(id).removeValue();
            }
        });*/
    }

    @NonNull
    @Override
    public Adapterclass.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list,
                parent, false);

        return new NoteHolder(v,ItemClickListener);
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView serivcename;
        RelativeLayout relativeLayout;
        public NoteHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            serivcename = itemView.findViewById(R.id.service_name);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onclick(position);
                        }
                    }
                }
            });
         // itemView.setOnLongClickListener(new View.OnLongClickListener() {
           //   @Override
            //  public boolean onLongClick(View v) {
//
  //                return false;
    //          }
      //    });
        }

    }
}