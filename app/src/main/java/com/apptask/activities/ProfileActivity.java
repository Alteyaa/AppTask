package com.apptask.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.apptask.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Button mSaveBtn;
    private EditText mTextAdd;
    public FirebaseFirestore mFirestore;
    private Uri selectedImage;
    private ImageView pickedImage;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirestore = FirebaseFirestore.getInstance();
        mTextAdd = findViewById(R.id.editName);
        pickedImage = findViewById(R.id.imageView);
        mSaveBtn = findViewById(R.id.save_btn);
        mStorageRef = FirebaseStorage.getInstance().getReference("Profile image");
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage != null) {
                    StorageReference reference = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(selectedImage));
                    reference.putFile(selectedImage);

                    String name = mTextAdd.getText().toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name of user", name);

                    mFirestore.collection("users").
                            add(user).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            String error = e.getMessage();
                            Toast.makeText(ProfileActivity.this, "Error :" + error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 20) {
            if (data != null) {
                selectedImage = data.getData();
                pickedImage.setImageURI(selectedImage);
            } else {
                Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}