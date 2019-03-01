package com.example.worldapp.Activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.worldapp.R;
import java.util.Arrays;
import java.util.List;

public class AddHome2Activity extends AppCompatActivity {

    private Button mNextButton;
    private Spinner listingsSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        InitializeViews();
        InitializeListingTypeSpinner();
    }

    private void InitializeListingTypeSpinner() {
        String[] listingType = {"Private room","Shared room","Private home"};
        List<String> listings = Arrays.asList(listingType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.template_spinner_layout, listings);
        listingsSpinner.setAdapter(adapter);
    }

    private void InitializeViews()
    {
        listingsSpinner = findViewById(R.id.spinner_select_listing_type);
        mNextButton = findViewById(R.id.btn_next_home_register_page_2);
    }
}
