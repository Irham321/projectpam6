package air.mobile.calcount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class addActivity extends AppCompatActivity {

    private RecyclerView sarapanRecyclerView;
    private SarapanAdapter SarapanAdapter;
    private List<Sarapan> sarapanList; // List<Sarapan> to match adapter requirements
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Corrected layout to activity_add.xml

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().getReference();

        sarapanRecyclerView = findViewById(R.id.sarapanRecyclerView);
        sarapanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sarapanList = new ArrayList<>(); // Initialize the list
        // Populate the list with Sarapan items
        sarapanList.add(new air.mobile.calcount.Sarapan("Gudeg", "100 kkal | 100 gram", R.drawable.gudeg));
        sarapanList.add(new air.mobile.calcount.Sarapan("Bubur Ayam", "220 kkal | 100 gram", R.drawable.bubur));
        sarapanList.add(new air.mobile.calcount.Sarapan("Pecel Madiun", "250 kkal | 100 gram", R.drawable.pecel));
        sarapanList.add(new air.mobile.calcount.Sarapan("Bakso", "230 kkal | 100 gram", R.drawable.bakso));
        sarapanList.add(new air.mobile.calcount.Sarapan("Ketoprak", "300 kkal | 100 gram", R.drawable.ketoprak));
        sarapanList.add(new air.mobile.calcount.Sarapan("Nasi Uduk", "270 kkal | 100 gram", R.drawable.nasiuduk));
        sarapanList.add(new air.mobile.calcount.Sarapan("Soto", "200 kkal | 100 gram", R.drawable.soto));
        sarapanList.add(new air.mobile.calcount.Sarapan("Oatmeal", "130 kkal | 100 gram", R.drawable.oatmeal));
        sarapanList.add(new air.mobile.calcount.Sarapan("Nasi Goreng", "250 kkal | 100 gram", R.drawable.nasigoreng));
        sarapanList.add(new air.mobile.calcount.Sarapan("Roti", "100 kkal | 100 gram", R.drawable.roti));
        sarapanList.add(new air.mobile.calcount.Sarapan("Pisang", "110 kkal | 100 gram", R.drawable.pisang));

        // Set adapter for RecyclerView
        SarapanAdapter = new air.mobile.calcount.SarapanAdapter(this, sarapanList);
        sarapanRecyclerView.setAdapter(SarapanAdapter);

        // Add button functionality to push data to Firebase
        Button sarapanButton = findViewById(R.id.sarapanButton);
        sarapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save Sarapan list to Firebase
                for (air.mobile.calcount.Sarapan sarapan : sarapanList) {
                    String id = database.child("sarapan").push().getKey(); // Generate unique ID for each item
                    if (id != null) {
                        database.child("sarapan").child(id).setValue(sarapan);
                    }
                }
            }
        });
    }
}