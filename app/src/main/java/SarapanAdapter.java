package air.mobile.calcount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SarapanAdapter extends RecyclerView.Adapter<SarapanAdapter.SarapanViewHolder> {

    private Context context;
    private List<Sarapan> sarapanList;

    public SarapanAdapter(Context context, List<Sarapan> sarapanList) {
        this.context = context;
        this.sarapanList = sarapanList;
    }

    @Override
    public SarapanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.sarapan_item, parent, false);
        return new SarapanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SarapanViewHolder holder, int position) {
        air.mobile.calcount.Sarapan sarapan = sarapanList.get(position);
        holder.foodNameTextView.setText(sarapan.getName());
        holder.foodInfoTextView.setText(sarapan.getInfo());
        holder.foodImageView.setImageResource(sarapan.getImageResId());

        // Add listener to "Tambah" button
        holder.btTambah.setOnClickListener(v -> {
            // Handle button click, for example, add to a list or update Firebase
            String id = FirebaseDatabase.getInstance().getReference("sarapan").push().getKey();
            if (id != null) {
                FirebaseDatabase.getInstance().getReference("sarapan").child(id).setValue(sarapan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sarapanList.size();
    }

    public static class SarapanViewHolder extends RecyclerView.ViewHolder {

        TextView foodNameTextView, foodInfoTextView;
        ImageView foodImageView;
        Button btTambah;

        public SarapanViewHolder(View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
            foodInfoTextView = itemView.findViewById(R.id.foodInfoTextView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            btTambah = itemView.findViewById(R.id.btTambah);
        }
    }
}