package com.deepti.amifit.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.model.Weight;
import com.deepti.amifit.util.ImageFilePath;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by deepti on 27/02/17.
 */

public class EditProfileFragment extends Fragment {
    public static String TAG = EditProfileFragment.class.getSimpleName();

    RelativeLayout layout;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    ImageView editPictureButton;
    ImageView editPicture;
    ImageView editStatus;
    ImageView editGender;
    ImageView editAge;
    ImageView editWeight;
    ImageView editHeight;
    TextView profileStatus;
    public RadioGroup radioGenderGroup;
    public RadioButton radioGenderButton;

    TextView genderValue;
    TextView ageValue;
    TextView weightValue;
    TextView heightValue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.edit_profile, container, false);
        TextView profileTitle = (TextView) layout.findViewById(R.id.profile_text);
        profileStatus = (TextView) layout.findViewById(R.id.status);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");

        profileTitle.setTypeface(customFont, Typeface.BOLD);
        profileStatus.setTypeface(customFont, Typeface.BOLD);

        editPictureButton = (ImageView) layout.findViewById(R.id.edit_picture);
        editPicture = (ImageView) layout.findViewById(R.id.profile_image);
        editStatus = (ImageView) layout.findViewById(R.id.edit_status);
        editGender = (ImageView) layout.findViewById(R.id.edit_gender);
        editAge = (ImageView) layout.findViewById(R.id.edit_age);
        editWeight = (ImageView) layout.findViewById(R.id.edit_weight);
        editHeight = (ImageView) layout.findViewById(R.id.edit_height);

        genderValue = (TextView) layout.findViewById(R.id.gender_value);
        ageValue = (TextView) layout.findViewById(R.id.age_value);
        weightValue = (TextView) layout.findViewById(R.id.weight_value);
        heightValue = (TextView) layout.findViewById(R.id.height_value);

        TextView genderTitle = (TextView) layout.findViewById(R.id.gender);
        TextView ageTitle = (TextView) layout.findViewById(R.id.age);
        TextView weightTitle = (TextView) layout.findViewById(R.id.weight);
        TextView heightTitle = (TextView) layout.findViewById(R.id.height);


        genderValue.setTypeface(customFont, Typeface.BOLD);
        ageValue.setTypeface(customFont, Typeface.BOLD);
        weightValue.setTypeface(customFont, Typeface.BOLD);
        heightValue.setTypeface(customFont, Typeface.BOLD);

        genderTitle.setTypeface(customFont, Typeface.BOLD);
        ageTitle.setTypeface(customFont, Typeface.BOLD);
        weightTitle.setTypeface(customFont, Typeface.BOLD);
        heightTitle.setTypeface(customFont, Typeface.BOLD);


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String filepath = shre.getString("profile_imagepath", "");
        File imgFile = new  File(filepath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            editPicture.setImageBitmap(myBitmap);
        }

        String age = shre.getString("age", "not set");
        String gender = shre.getString("gender", "not set");
        String weight = shre.getString("weight", "not set");
        String height = shre.getString("height", "not set");

        if(age !=  null && !age.equals("not set")){
            ageValue.setText(age);
        }
        if(gender !=  null && !gender.equals("not set")){
            genderValue.setText(gender);
        }
        if(weight !=  null && !weight.equals("not set")){
            weightValue.setText(weight);
        }

        if(height !=  null && !height.equals("not set")){
            heightValue.setText(height);
        }

        String status = shre.getString("status", "not set");
        Log.d(TAG, "status:" + status);
        if(status !=  null && !status.equals("not set")){
            profileStatus.setText(status);
        }
        if(status.equals("not set")){
            profileStatus.setText("You are awesome!");
        }
        editPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Gender", "Status", "gender");
            }
        });

        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Age", "enter age", "age");
            }
        });

        editHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Height", "enter height", "height");
            }
        });

        editWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Weight", "enter weight", "weight");
            }
        });

        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickUpDialog("Gender", "gender");
            }
        });



        return layout;
    }


    private void showDialog(String title, String entryValue, final String type) {
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dialog);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.edit_dialog_title);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        TextView entry = (TextView) dialog.findViewById(R.id.entry);
        final Editable inputValue = value.getText();

        dialogTitle.setText(title);
        entry.setText(entryValue);

        entry.setTypeface(customFont, Typeface.BOLD);
        value.setTypeface(customFont, Typeface.BOLD);

        dialogTitle.setTypeface(customFont, Typeface.BOLD);
        Button btn = (Button) dialog.findViewById(R.id.ok_button);
        btn.setTypeface(customFont, Typeface.BOLD);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputValue != null && !inputValue.toString().equals("")) {
                    switch(type){
                        case "status":
                            updateStatus(inputValue);
                            break;
                        case "weight":
                            updateWeight(inputValue);
                            break;
                        case "age":
                            updateAge(inputValue);
                            break;
                        case "height":
                            updateHeight(inputValue);
                            break;
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    private void showPickUpDialog(String title, final String type) {
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_pick_dialog);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.edit_pick_dialog_title);
        radioGenderGroup = (RadioGroup) dialog.findViewById(R.id.radioGender);
        dialogTitle.setText(title);

        dialogTitle.setTypeface(customFont, Typeface.BOLD);
        Button btn = (Button) dialog.findViewById(R.id.ok_button);
        btn.setTypeface(customFont, Typeface.BOLD);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioGenderButton = (RadioButton) dialog.findViewById(selectedId);

                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit=shre.edit();
                edit.putString("gender", radioGenderButton.getText().toString());
                edit.commit();
                genderValue.setText(radioGenderButton.getText());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateStatus(Editable inputValue) {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("status", inputValue.toString());
        edit.commit();
        profileStatus.setText(inputValue);

    }


    private void updateAge(Editable inputValue) {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("age", inputValue.toString());
        edit.commit();
        ageValue.setText(inputValue);
    }

    private void updateWeight(Editable inputValue) {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("weight", inputValue.toString());
        edit.commit();
        weightValue.setText(inputValue);
        Calendar c = Calendar.getInstance();
        Log.d(TAG, "Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDate = dateFormat.format(c.getTime());


        if(inputValue != null && inputValue.equals("not set")){
            ArrayList<Weight> w = (ArrayList<Weight>) Weight.findWithQuery(Weight.class, "select * from Weight where time = ?", onlyDate);
            if (w.size() > 0)
                for (Weight weight : w) {
                    Weight.delete(weight);
                }
            Weight weight = new Weight(Float.parseFloat(inputValue.toString()), formattedDate);
            weight.save();
        }


    }

    private void updateHeight(Editable inputValue) {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("height", inputValue.toString());
        edit.commit();
        heightValue.setText(inputValue);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Log.d(TAG, "onCaptureImageResult:" + data.getExtras().get("data"));
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        Log.d(TAG, "destination:" + destination);
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("profile_imagepath", destination.toString());
        edit.commit();

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editPicture.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Log.d(TAG,"data:" + data.getData());

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
                Uri selectedImageUri = data.getData( );
                String picturePath = ImageFilePath.getPath( getActivity().getApplicationContext(), selectedImageUri );
                Log.d(TAG, "picturePath:" + picturePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap thumbnail = bm;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        Log.d(TAG, "destination:" + destination);
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("profile_imagepath",destination.toString());
        edit.commit();

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editPicture.setImageBitmap(bm);
    }



}