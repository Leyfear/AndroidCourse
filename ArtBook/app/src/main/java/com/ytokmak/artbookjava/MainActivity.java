package com.ytokmak.artbookjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ytokmak.artbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Details> arrayList;
    SQLiteDatabase database;
    mAdapter mmAdapter;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        arrayList = new ArrayList<>();
        datasave();

        binding.recylerVi.setLayoutManager(new LinearLayoutManager(this));
        mmAdapter = new mAdapter(arrayList);
        binding.recylerVi.setAdapter(mmAdapter);


    }
    public void datasave(){
        try {
            database = this.openOrCreateDatabase("Tetra",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM tetra",null);
            int idX = cursor.getColumnIndex("id");
            int namex= cursor.getColumnIndex("name");
            while (cursor.moveToNext()){
                String name = cursor.getString(namex);
                int numb = cursor.getInt(idX);
                Details details = new Details(name,numb);
                arrayList.add(details);
            }
            mmAdapter.notifyDataSetChanged();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.optionBar)
        {
            Intent intent = new Intent(this,InfoActivity.class);
            intent.putExtra("Old","New");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}