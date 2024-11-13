package fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sportsapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private EditText name, email, totalGames, winCount, loseCount, averageRating, ratingOverTime, participationList;
    private TextView profileDisplay;
    private ImageView profileImage;
    private Uri cameraImageUri;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        name = rootView.findViewById(R.id.name);
        email = rootView.findViewById(R.id.email);
        totalGames = rootView.findViewById(R.id.total_games);
        winCount = rootView.findViewById(R.id.win_count);
        loseCount = rootView.findViewById(R.id.lose_count);
        averageRating = rootView.findViewById(R.id.average_rating);
        ratingOverTime = rootView.findViewById(R.id.rating_over_time);
        participationList = rootView.findViewById(R.id.participation_list);
        profileDisplay = rootView.findViewById(R.id.profile_display);
        profileImage = rootView.findViewById(R.id.profile_image);

        sharedPreferences = getActivity().getSharedPreferences("userProfile", getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadProfileData();

        Button saveButton = rootView.findViewById(R.id.profile_save_button);
        saveButton.setOnClickListener(v -> saveProfileData());

        Button selectImageButton = rootView.findViewById(R.id.select_image_button);
        selectImageButton.setOnClickListener(v -> showImageSourceDialog());

        // Initialize Image Pickers
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        loadProfileImage(imageUri);
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && cameraImageUri != null) {
                        loadProfileImage(cameraImageUri);
                    }
                }
        );

        return rootView;
    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image")
                .setItems(new String[]{"Choose from Gallery", "Take a Picture"}, (dialog, which) -> {
                    if (which == 0) {
                        openImagePicker();
                    } else {
                        if (hasCameraPermission()) {
                            openCamera();
                        } else {
                            requestCameraPermission();
                        }
                    }
                })
                .show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void openCamera() {
        try {
            File imageFile = createImageFile();
            cameraImageUri = FileProvider.getUriForFile(getActivity(), "com.example.sportsapp.fileprovider", imageFile);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            cameraLauncher.launch(cameraIntent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to create image file", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getActivity(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadProfileImage(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .into(profileImage);
        profileImage.setTag(imageUri.toString());
    }

    private void saveProfileData() {
        editor.putString("name", name.getText().toString());
        editor.putString("email", email.getText().toString());
        editor.putString("totalGames", totalGames.getText().toString());
        editor.putString("winCount", winCount.getText().toString());
        editor.putString("loseCount", loseCount.getText().toString());
        editor.putString("averageRating", averageRating.getText().toString());
        editor.putString("ratingOverTime", ratingOverTime.getText().toString());
        editor.putString("participationList", participationList.getText().toString());
        if (profileImage.getTag() != null) {
            editor.putString("profileImage", profileImage.getTag().toString());
        }

        editor.apply();
        Toast.makeText(getActivity(), "Profile saved!", Toast.LENGTH_SHORT).show();
        loadProfileData();
    }

    private void loadProfileData() {
        String savedName = sharedPreferences.getString("name", "No Name");
        String savedEmail = sharedPreferences.getString("email", "No Email");
        String savedTotalGames = sharedPreferences.getString("totalGames", "0");
        String savedWinCount = sharedPreferences.getString("winCount", "0");
        String savedLoseCount = sharedPreferences.getString("loseCount", "0");
        String savedAverageRating = sharedPreferences.getString("averageRating", "0");
        String savedRatingOverTime = sharedPreferences.getString("ratingOverTime", "Not Available");
        String savedParticipationList = sharedPreferences.getString("participationList", "No Data");
        String savedProfileImage = sharedPreferences.getString("profileImage", "");

        String profileText = "Name: " + savedName + "\nEmail: " + savedEmail +
                "\nTotal Games: " + savedTotalGames + "\nWins: " + savedWinCount +
                "\nLosses: " + savedLoseCount + "\nAvg Rating: " + savedAverageRating +
                "\nRating Over Time: " + savedRatingOverTime +
                "\nParticipation List: " + savedParticipationList;

        profileDisplay.setText(profileText);
        profileDisplay.setVisibility(View.VISIBLE);

        if (!savedProfileImage.isEmpty()) {
            Glide.with(this)
                    .load(savedProfileImage)
                    .into(profileImage);
        }
    }
}

