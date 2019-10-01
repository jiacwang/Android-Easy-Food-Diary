package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import android.widget.ImageView;
import android.net.Uri;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import java.io.IOException;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.*;
import android.app.*;

import java.net.URI;
import java.util.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import android.widget.Toast;
import android.support.annotation.*;

import org.json.JSONArray;
import org.json.JSONException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private TextView textViewEGoal;
    private TextView textViewWGoal;

    private TextView logoutTextView;

    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private Button setUserName;

    protected Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    protected FirebaseStorage storage;
    protected StorageReference storageReference;
    protected Button deleteAccount;

    protected DatabaseReference dbf;
    protected Uri downloadUrl;
    protected Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();


        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        logoutTextView = (TextView) findViewById(R.id.textLogout);
        textViewEGoal = (TextView) findViewById(R.id.textViewEGoal);
        textViewWGoal = (TextView) findViewById(R.id.textViewWGoal);
        //displaying logged in user name
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        DatabaseReference ndbr= FirebaseDatabase.getInstance().getReference("name").child(user.getUid());
        ndbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                String value = dataSnapshot.getValue(String.class);
                //et_array[index] = (EditText) findViewById(R.id.Name);
                textViewUserEmail.setText("Name:" + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference edbr= FirebaseDatabase.getInstance().getReference("egoal").child(user.getUid());
        edbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                String value = dataSnapshot.getValue(String.class);
                //et_array[index] = (EditText) findViewById(R.id.Name);
                textViewEGoal.setText("Exercise Goal:" + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
        DatabaseReference wdbr= FirebaseDatabase.getInstance().getReference("wgoal").child(user.getUid());
        wdbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                String value = dataSnapshot.getValue(String.class);
                //et_array[index] = (EditText) findViewById(R.id.Name);
                textViewWGoal.setText("Weight Goal:" + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });



        //adding listener to button
        logoutTextView.setOnClickListener(this);

        //Initialize Views
        setUserName = (Button) findViewById(R.id.setUsername);
        deleteAccount = (Button) findViewById(R.id.deleteAccount);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dbf = FirebaseDatabase.getInstance().getReference().child("images").child(user.getUid());
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }

        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        deleteAccount.setOnClickListener(this);
        setUserName.setOnClickListener(this);
        dbf.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String s1 = dataSnapshot.getValue(String.class);
                try{
                    if(s1 ==null){
                        imageView.setImageURI(null);
                        //textViewUserEmail.setText("hellohelllo");
                    }else{
                        Uri uri = Uri.parse(s1.toString());
                        imageView.setImageURI(uri);
                        //textViewUserEmail.setText(s1.toString());
                    }

                }catch (NullPointerException e){

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == logoutTextView){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if(view == deleteAccount){
            //TO DO

            //Toast.makeText(ProfileActivity.this, ""+FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().get), Toast.LENGTH_SHORT).show();


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "User account deleted.", Toast.LENGTH_SHORT).show();
                                //Log.d("TAG", "");
                            }
                        }
                    });

            startActivity(new Intent(this, LoginActivity.class));
        }else if(view == setUserName){
            startActivity(new Intent(this, UsernameActivity.class));

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            dbf.push().setValue(downloadUrl.toString());

                            Picasso.with(ProfileActivity.this).load(downloadUrl).fit().centerCrop().into(imageView);
                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}