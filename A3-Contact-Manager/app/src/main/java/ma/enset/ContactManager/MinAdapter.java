package ma.enset.ContactManager;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MinAdapter extends RecyclerView.Adapter<MinAdapter.ViewHolder>  {

    private List<Contact> contactList;
    private Activity activity;
    private  RoomDB roomDB;

    public MinAdapter(Activity activity,List<Contact> contactList){
        this.activity = activity;
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_contactes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MinAdapter.ViewHolder holder,@SuppressLint("RecyclerView") final int position) {


        roomDB = RoomDB.getInstance(activity);

        holder.text_nome.setText(contactList.get(position).getFirst_name());
        holder.text_prenom.setText(contactList.get(position).getLast_name());
        holder.text_phone.setText(contactList.get(position).getPhone());
        holder.text_email.setText(contactList.get(position).getEmail());

        holder.btt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = contactList.get(holder.getAdapterPosition());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.getPhone()));
                view.getContext().startActivity(intent);
            }
        });
        

        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact d = contactList.get(holder.getAdapterPosition());

                Intent intent = new Intent(view.getContext() , UpdateContact.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", d.getId());

                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        holder.list_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Contact contact = contactList.get(holder.getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(true);
                builder.setTitle("delete");
                builder.setMessage("you want delete this contact?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                roomDB.mainDao().delete( contact );
                                contactList.remove( holder.getAdapterPosition() );
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_nome,text_prenom,text_phone,text_email;
        ImageView btt_call;
        ImageView btt_img;
        LinearLayout list_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_nome=itemView.findViewById(R.id.text_nome);
            text_prenom=itemView.findViewById(R.id.text_prenom);
            text_phone=itemView.findViewById(R.id.text_phone);
            text_email=itemView.findViewById(R.id.text_email);

            btt_call=itemView.findViewById(R.id.btt_call);
            btt_img=itemView.findViewById(R.id.btt_img);

            list_item = itemView.findViewById(R.id.list_item);
        }
    }
}
