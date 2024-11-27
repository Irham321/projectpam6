package air.mobile.calcount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView sarapanRecyclerView;
    private air.mobile.calcount.SarapanAdapter adapter;
    private List<air.mobile.calcount.Sarapan> dataset; // Change Object to Sarapan
    private EditText etStatus;
    private Button btTambah;
    private DatabaseReference dbRef;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Status");

        // Initialize UI components
        etStatus = findViewById(R.id.etStatus); // Initialize the EditText
        btTambah = findViewById(R.id.sarapanButton);
        sarapanRecyclerView = findViewById(R.id.sarapanRecyclerView);

        // Initialize RecyclerView and Adapter
        dataset = new ArrayList<>();
        adapter = new air.mobile.calcount.SarapanAdapter(this, dataset); // Initialize adapter here
        sarapanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sarapanRecyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadStatusesFromFirebase();

        // Set onClickListener for the "Tambah" button
        btTambah.setOnClickListener(this);
    }

    private void loadStatusesFromFirebase() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataset.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Sarapan status = s.getValue(Sarapan.class); // Ensure Sarapan class is used
                    if (status != null) {
                        dataset.add(status);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter about the updated data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sarapanButton) { // Corrected button ID
            String statusText = etStatus.getText().toString().trim();

            if (!statusText.isEmpty()) {
                // Create a new status object
                String id = dbRef.push().getKey();
                if (id != null) {
                    air.mobile.calcount.Sarapan status = new air.mobile.calcount.Sarapan(statusText, "Info", 0); // Create a Sarapan object
                    dbRef.child(id).setValue(status)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Status added successfully!", Toast.LENGTH_SHORT).show();
                                    etStatus.setText(""); // Clear input field
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed to add status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "Failed to generate ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a valid status", Toast.LENGTH_SHORT).show();
            }
        }
    }
}