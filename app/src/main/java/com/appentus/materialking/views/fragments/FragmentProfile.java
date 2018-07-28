package com.appentus.materialking.views.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appentus.materialking.R;
import com.appentus.materialking.utility.AndroidMultiPartEntity;
import com.appentus.materialking.utility.MyApplication;
import com.appentus.materialking.utility.PrefsData;
import com.appentus.materialking.utility.ProgressView;
import com.appentus.materialking.webservice.WebServiceUrl;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/17/2018.
 */

public class FragmentProfile extends Fragment {
    View view;
    @BindView(R.id.iv_edit_profile_pic)
    CircleImageView ivEditProfilePic;
    @BindView(R.id.et_edit_profile_name)
    EditText etEditProfileName;
    @BindView(R.id.et_edit_profile_gender)
    EditText etEditProfileGender;
    @BindView(R.id.et_edit_profile_email)
    EditText etEditProfileEmail;
    @BindView(R.id.et_edit_profile_mobileNumber)
    EditText etEditProfileMobileNumber;
    @BindView(R.id.et_edit_profile_address)
    EditText etEditProfileAddress;
    @BindView(R.id.et_edit_profile_pincode)
    EditText etEditProfilePincode;
    @BindView(R.id.et_edit_profile_city)
    EditText etEditProfileCity;
    @BindView(R.id.et_edit_profile_state)
    EditText etEditProfileState;
    Unbinder unbinder;


    String pathOfPicture = "";

    @BindView(R.id.btUpdateProfile)
    AppCompatButton btUpdateProfile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        ivEditProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfilePic();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // setting defaults
        setDefaults();


        btUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringAddress =  etEditProfileAddress.getText().toString().trim();
                String stringPincode =  etEditProfilePincode.getText().toString().trim();
                String stringState =  etEditProfileState.getText().toString().trim();
                String stringCity =  etEditProfileCity.getText().toString().trim();


                if(stringAddress.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Your Address !",5000).show();
                    return;
                }

                if(stringPincode.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Your PinCode !",5000).show();
                    return;
                }

                if(stringPincode.length()<6)
                {
                    Toast.makeText(getActivity(),"PinCode Minimum Length is 6 !",5000).show();
                    return;
                }

                if(stringState.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Your State !",5000).show();
                    return;
                }


                if(stringCity.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter Your City !",5000).show();
                    return;
                }



                updateProfileHere(stringAddress,stringPincode,stringState,stringCity);




            }
        });

    }

    private void updateProfileHere(final String stringAddress,
                                   final String stringPincode,
                                   final String stringState,
                                   final String stringCity) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader("Updating...");


        MyApplication.getInstance().cancelPendingRequests("update_profile_details");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebServiceUrl.update_profile_details, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("update_profile_details",response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if(status.equalsIgnoreCase("1"))
                    {

                        MyApplication.writeStringPref(PrefsData.address,stringAddress);
                        MyApplication.writeStringPref(PrefsData.pincode,stringPincode);
                        MyApplication.writeStringPref(PrefsData.state,stringState);
                        MyApplication.writeStringPref(PrefsData.city,stringCity);

                        setDefaults();


                        Toast.makeText(getActivity(),message,5000).show();

                    }
                    else
                    {
                        // user name or password wrong
                        Toast.makeText(getActivity(),message,5000).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                progressView.hideLoader();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));

                } else if (error instanceof AuthFailureError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO

                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getParams()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("address", stringAddress);
                headers.put("pincode", stringPincode);
                headers.put("state", stringState);
                headers.put("city", stringCity);
                headers.put("user_id", MyApplication.readStringPref(PrefsData.PREF_USERID));

                Log.e("POST DATA", headers.toString());



                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq,"update_profile_details");

    }

    private void setDefaults() {


        etEditProfileName.setText(MyApplication.readStringPref(PrefsData.name));
        etEditProfileGender.setText(MyApplication.readStringPref(PrefsData.gender));
        etEditProfileEmail.setText(MyApplication.readStringPref(PrefsData.email));
        etEditProfileMobileNumber.setText(MyApplication.readStringPref(PrefsData.PREF_mobileNumber));
        etEditProfileAddress.setText(MyApplication.readStringPref(PrefsData.address));
        etEditProfilePincode.setText(MyApplication.readStringPref(PrefsData.pincode));
        etEditProfileCity.setText(MyApplication.readStringPref(PrefsData.city));
        etEditProfileState.setText(MyApplication.readStringPref(PrefsData.state));


        if (MyApplication.readStringPref(PrefsData.image).equalsIgnoreCase("")) {

        } else {
            Glide.with(getActivity())
                    .load(WebServiceUrl.IMAGEBASEURL + MyApplication.readStringPref(PrefsData.image))
                    .into(ivEditProfilePic);
        }


    }

    private void setProfilePic() {
        ImagePicker.with(this)
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(false)
                .setFolderMode(true)
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {

                try {
                    ivEditProfilePic.setImageBitmap(null);
                    ivEditProfilePic.setImageBitmap(decodeUri(Uri.fromFile(new File(images.get(0).getPath()))));
                    pathOfPicture = compressImage(images.get(0).getPath());

                    new UploadFileToServer(pathOfPicture).execute();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //  MainActivity.ATTACHPIC = images.get(0).getPath();
                // Glide.with(getActivity()).load(images.get(0).getPath()).into(ivEditProfilePic);

                // Log.e("path =",images.get(0).getPath());


            } else {
                Toast.makeText(getActivity(), "Please select the pic", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
        final int REQUIRED_SIZE = 100;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }


    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;


        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;


        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }


        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();


        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MaterialKing/Profile");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        String imagePath = "";
        long totalSize = 0;
        ProgressView progressView;

        public UploadFileToServer(String imagepathToSend) {

            this.imagePath = imagepathToSend;
            progressView = new ProgressView(getActivity());
        }

        @Override
        protected void onPreExecute() {
            progressView.showLoader("Uploading...");

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            progressView.showLoader("Uploading... " + String.valueOf(progress[0]) + "%");

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {


            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(WebServiceUrl.update_profile);

            try {

                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(imagePath);
                entity.addPart("image", new FileBody(sourceFile));
                Log.e("sourceFile", imagePath);
                entity.addPart("user_id", new StringBody(MyApplication.readStringPref(PrefsData.PREF_USERID)));
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }


            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("result", result);

            if (progressView.isShowing()) {
                progressView.hideLoader();
            }
            try {
                JSONObject mJsonObject = new JSONObject(result);
                String status = mJsonObject.getString("status");
                String message = mJsonObject.getString("message");
                if (status.equalsIgnoreCase("1")) {

                   /* String image = mJsonObject.getString("image");
                    MyApplication.writeStringPref(PrefsData.PREF_IMAGE, image);*/

                    String image = mJsonObject.getString("profile");
                    MyApplication.writeStringPref(PrefsData.image, image);
                    setDefaults();
                    Toast.makeText(getActivity(), message, 5000).show();

                } else {
                    Toast.makeText(getActivity(), message, 5000).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            super.onPostExecute(result);
        }

    }


}
