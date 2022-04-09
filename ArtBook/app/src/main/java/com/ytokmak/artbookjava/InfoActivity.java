package com.ytokmak.artbookjava;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ContentInfoCompat;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.ytokmak.artbookjava.databinding.ActivityInfoBinding;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;

public class InfoActivity extends AppCompatActivity {
    ActivityInfoBinding binding;
    ActivityResultLauncher<Intent> intentActivityResultLauncher;
    ActivityResultLauncher<String> stringActivityResultLauncher;
    Bitmap images;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //activitylauncherları register etmemiz gerekiyor.
        registerLauncher();
        database = this.openOrCreateDatabase("Tetra",MODE_PRIVATE,null);

        Intent intent = getIntent();
        String a = intent.getStringExtra("Old");
        if(a.equals("New")){
            binding.editTextTextPersonName2.setText("");
            binding.editTextTextPersonName3.setText("");
            binding.editText3.setText("");
            binding.imageView3.setImageResource(R.drawable.img);
            binding.button.setVisibility(View.VISIBLE);
        }else{
            binding.button.setVisibility(View.INVISIBLE);
            int id = intent.getIntExtra("ArtName",0);
            try{

                Cursor cursor = database.rawQuery("SELECT * FROM tetra WHERE id = ?",new String[] {String.valueOf(id)});
                int nameIx =cursor.getColumnIndex("name");
                int objname = cursor.getColumnIndex("objname");
                int years = cursor.getColumnIndex("year");
                int image = cursor.getColumnIndex("image");
                while(cursor.moveToNext()){
                binding.editTextTextPersonName2.setText(cursor.getString(nameIx));
                binding.editTextTextPersonName3.setText(cursor.getString(objname));
                binding.editText3.setText(cursor.getString(years));
                byte[] bytes = cursor.getBlob(image);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                binding.imageView3.setImageBitmap(bitmap);
                }
               cursor.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void selectedimageView(View view){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission Needed",BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //permission + galleery
                        stringActivityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }else{
                //permisison + gallery
                stringActivityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
        else {
            //gallery
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentActivityResultLauncher.launch(intent);
        }
    }

    public void savex(View view){
        String name = binding.editTextTextPersonName2.getText().toString();
        String artistname = binding.editText3.getText().toString();
        String year = binding.editTextTextPersonName3.getText().toString();

        Bitmap bitmat = convertimage(images,300);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmat.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] dizi =byteArrayOutputStream.toByteArray();


        try{

            database.execSQL("CREATE TABLE IF NOT EXISTS tetra (id INTEGER PRIMARY KEY, name VARCHAR, objname VARCHAR, year VARCHAR, image BLOB)");
            String s = "INSERT INTO tetra (name, objname, year, image) VALUES (?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(s);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,artistname);
            sqLiteStatement.bindString(3,year);
            sqLiteStatement.bindBlob(4,dizi);
            sqLiteStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
        Intent intent = new Intent(InfoActivity.this , MainActivity.class);
        startActivity(intent);
    }
    public Bitmap convertimage(Bitmap image, int enlem){
        int heigh = image.getHeight();
        int weight = image.getWidth();
        float bolum =  (float)weight / (float )heigh;
        if(bolum > 1){
            weight = enlem;
            heigh = (int) (weight / bolum);
        }else{
            heigh = enlem;
            weight = (int) ( heigh * bolum);
        }

        return image.createScaledBitmap(image,weight,heigh,false);
    }
    public void registerLauncher()
    {
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Intent intentData = result.getData();
                    if(intentData != null){
                        Uri uri = intentData.getData();
                        try{
                            if(Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                                images = ImageDecoder.decodeBitmap(source);
                                binding.imageView3.setImageBitmap(images);
                            }else{
                                images = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                                binding.imageView3.setImageBitmap(images);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
        stringActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    //izin verildi galeryie git
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intentActivityResultLauncher.launch(intent);

                }
                else{
                    //izin verilmedi izin verilmedi de
                    Toast.makeText(InfoActivity.this, "Izın verilmedi", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}