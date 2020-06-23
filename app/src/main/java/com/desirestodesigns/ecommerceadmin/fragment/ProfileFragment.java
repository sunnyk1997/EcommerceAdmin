package com.desirestodesigns.ecommerceadmin.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.desirestodesigns.ecommerceadmin.R;
import com.desirestodesigns.ecommerceadmin.activity.EditAdminDetailsActivity;
import com.desirestodesigns.ecommerceadmin.datamodel.AdminRegistrationForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private static final String TAG = "PROFILE FRAGMENT";
    TextView mName, mNumber, mAddress;
    Button mEdit;
    String userId;
    ArrayList<AdminRegistrationForm> adminArrayList = new ArrayList<>();
    //    ArrayList<Address> addressArrayList = new ArrayList<>();
    View view;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmrnt_profile, container, false);
        initializeUi();
        readFromDb();
        return view;
    }


    private void initializeUi() {
        mName = view.findViewById(R.id.customer_name);
        mNumber = view.findViewById(R.id.customer_number);
        mAddress = view.findViewById(R.id.customer_address);
        mEdit = view.findViewById(R.id.edit);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditAdminDetailsActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = mCurrentUser.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Admin");
    }

    private void readFromDb() {
        Log.i(TAG, "readFromDb Method Invoked");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    adminArrayList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        AdminRegistrationForm adminRegistrationForm = doc.toObject(AdminRegistrationForm.class);
                        String adminName = adminRegistrationForm.getFirstName() + " " + adminRegistrationForm.getLastName();
                        String customerNumber = adminRegistrationForm.getMobileNumber();
                        String address = adminRegistrationForm.getAddress();
                        userId = adminRegistrationForm.getUserId();
                        mName.setText(adminName);
                        mNumber.setText(customerNumber);
                        mAddress.setText(address);

                    }
                }

            }
        });
    }
}
