package com.kt.na_social.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kt.na_social.R;
import com.kt.na_social.api.UserApi;
import com.kt.na_social.user.UpdateUserProfileRequest;
import com.kt.na_social.api.response.BaseResponse;
import com.kt.na_social.model.User;


import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1001;

    private ImageView ivProfileAvatar;
    private EditText edtDisplayName, edtPhoneNumber, edtEmail;
    private Button btnSaveProfile, btnEditAvatar;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfileAvatar = findViewById(R.id.imageview_account_profile);
        edtDisplayName = findViewById(R.id.edtUserName);
        edtPhoneNumber = findViewById(R.id.edtEmail);
        edtEmail = findViewById(R.id.edtPassword);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnEditAvatar = findViewById(R.id.floatingActionButton);

        getCurrentUserInfo();

        btnEditAvatar.setOnClickListener(v -> openGallery());

        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void getCurrentUserInfo() {
        UserApi userApi = ApiClient.getClient().create(UserApi.class);
        userApi.getCurrentUserInfo().enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    currentUser = response.body().getData();
                    displayUserData();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserData() {
        if (currentUser != null) {
            Glide.with(this)
                    .load(currentUser.getProfileAvatar())
                    .circleCrop()
                    .into(ivProfileAvatar);
            edtDisplayName.setText(currentUser.getDisplayName());
            edtPhoneNumber.setText(currentUser.getPhoneNumber());
            edtEmail.setText(currentUser.getEmail());
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                ivProfileAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProfile() {
        String displayName = edtDisplayName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if (displayName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest(displayName, phoneNumber, email);

        UserApi userApi = ApiClient.getClient().create(UserApi.class);
        userApi.updateProfile(updateRequest).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
