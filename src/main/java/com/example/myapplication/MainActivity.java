package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    public EditText ed1,ed2,ed3,id;
    public Button addbtn , viewall , update ,delete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);

        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        id = (EditText) findViewById(R.id.id);

        addbtn = (Button) findViewById(R.id.addbtn);
        add();
        viewall = (Button) findViewById(R.id.viewall);
        viewAllData ();
        updateData();
        delete();
    }

    public void add (){
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isInserted =  mydb.insert(ed1.getText().toString(),
                        ed2.getText().toString(),
                        ed3.getText().toString());

                if(isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                   ed1.setText(null);
                    ed2.setText(null);
                    ed3.setText(null);

                }else{
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAllData (){

           viewall = (Button) findViewById(R.id.viewall);
           viewall.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Cursor res = mydb.getAllValue();
                                              if (res.getCount() == 0) {
                                                  showMessage("Error ", "Data not Found");
                                                  return;
                                              }
                                              StringBuffer buffer = new StringBuffer();
                                              while (res.moveToNext()) {
                                                 try{
                                                     buffer.append("Id: " + res.getString(0) + "\n");
                                                     buffer.append("Name: " + res.getString(1) + "\n");
                                                     buffer.append("Surname: " + res.getString(2) + "\n");
                                                     buffer.append("Marks: " + res.getString(3) + "\n" + "\n");

                                                 }catch (Exception ee){
                                                     Log.d( "onClick3: ",String.valueOf(ee));
                                                     Toast.makeText(MainActivity.this, String.valueOf(ee), Toast.LENGTH_SHORT).show();
                                                 }
                                                                                         }
                                              showMessage("Data",buffer.toString());
                                          }

                                      }
           );

       }


    public void showMessage (String title ,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isUpdate = mydb.updateValues(id.getText().toString(),
                                  ed1.getText().toString(),
                                  ed2.getText().toString(),
                                  ed3.getText().toString());
               if(isUpdate == true){
                   Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    public void delete(){

    delete = (Button) findViewById(R.id.delete_btn);
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer deletedrows = mydb.delete(id.getText().toString());
            if(deletedrows > 0)
            {
                Toast.makeText(MainActivity.this, "data Deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "data not Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }
    }

