package com.desirestodesigns.ecommerceadmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desirestodesigns.ecommerceadmin.R;
import com.desirestodesigns.ecommerceadmin.datamodel.AdminRegistrationForm;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditAdminDetailsActivity extends AppCompatActivity {
    private static final String TAG = "EDIT ADMIN DETAILS";
    EditText mFirstName, mLastName, mNumber,mAddress;
    String firstName,lastName,address,number;
    Button mSave;
    String userId;
    AdminRegistrationForm adminRegistrationForm;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_details);
        uiMethods();
        userId = getIntent().getStringExtra("USER_ID");
        Log.i(TAG, "return value test" + userId);
        if (!TextUtils.isEmpty(userId)) {
            Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
            editItem(userId);
        } else {
            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            userId = mCurrentUser.getUid();
        }
    }
    private void uiMethods() {
        mFirstName = findViewById(R.id.first_name_edit);
        mLastName = findViewById(R.id.last_name_edit);
        mNumber = findViewById(R.id.mobile_number_edit);
        mAddress = findViewById(R.id.address_edit);
        mSave = findViewById(R.id.save_btn_2);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Admin");
    }
    private void editItem(String userId) {
        collectionReference.document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot != null) {
                    adminRegistrationForm  = new AdminRegistrationForm();
                    adminRegistrationForm = documentSnapshot.toObject(AdminRegistrationForm.class);

                    mFirstName.setText(adminRegistrationForm.getFirstName());
                    mLastName.setText(adminRegistrationForm.getLastName());
                    mNumber.setText(adminRegistrationForm.getMobileNumber());
                    mAddress.setText(adminRegistrationForm.getAddress());
                }

            }
        });

    }


    public void onClickSave(View view) {
        Log.i(TAG, "Entered into Save loop");
        Log.i(TAG, "EDITED DATA WILL BE SENT TO DATABASE");
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        number = mNumber.getText().toString();
        address = mAddress.getText().toString();
        if ((!TextUtils.isEmpty(firstName)) && (!TextUtils.isEmpty(lastName))
                && (!TextUtils.isEmpty(number))&& (!TextUtils.isEmpty(address))){
            Log.i(TAG, "Entered in to IF loop");
            adminObject();
        }else {

            Log.i(TAG, "Entered into ELSE loop");
            Toast.makeText(this, "Please enter appropriate data", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void adminObject() {
        adminRegistrationForm = new AdminRegistrationForm();
        adminRegistrationForm.setFirstName(firstName);
        adminRegistrationForm.setLastName(lastName);
        adminRegistrationForm.setMobileNumber(number);
        adminRegistrationForm.setAddress(address);
        adminRegistrationForm.setUserId(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
        String currentDate = simpleDateFormat.format(new Date());
        Log.i(TAG, "Current Timestamp: " + currentDate);
        adminRegistrationForm.setCreatedDate(currentDate);
        sendToDb(userId);
    }

    private void sendToDb(String userId) {
        Log.i(TAG, "sendToDb method is invoked");
        collectionReference.document(userId)
                .set(adminRegistrationForm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                clearUi();
                Log.i(TAG, "clearUi method is executed");
                finish();
                Log.i(TAG, "Add Employee Activity is closed and returns to Main Activity");
                Toast.makeText(EditAdminDetailsActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditAdminDetailsActivity.this, "Issue while adding data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearUi() {
        mLastName.setText("");
        mFirstName.setText("");
        mNumber.setText("");
        mAddress.setText("");
        finish();
    }
}
