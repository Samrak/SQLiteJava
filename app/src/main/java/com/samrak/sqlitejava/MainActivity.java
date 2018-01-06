package com.samrak.sqlitejava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samrak.dao.SQLiteHelper;
import com.samrak.model.EntityMaterial;
import com.samrak.model.EntityPerson;
import com.samrak.sqlite.SQLiteAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    SQLiteAdapter db = new SQLiteAdapter(dbHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), db.insert(new EntityPerson(0, "samet", "öztoprak","data",3, 4.3f), "keyId") + " id inserted", Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), db.insert(new EntityMaterial(0, "samet", "öztoprak", "data"), "materialId") + " id inserted", Toast.LENGTH_SHORT).show();

            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(), db.delete(new EntityPerson(2, "samet", "öztoprak","data",3, 4.3f),"keyId") + " id deleted", Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), db.delete(new EntityMaterial(2, "samet", "öztoprak", "data"), "materialId") + " id deleted", Toast.LENGTH_SHORT).show();

            }
        });

        Button btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityPerson person = new EntityPerson();
                ArrayList<EntityMaterial> materials = db.select(new EntityMaterial());
                ArrayList<EntityPerson> entities = db.select(person);
                for (EntityPerson e : entities)
                    Log.d("dbColumns :", e.getKeyId() + " " + e.getKeyName() + " " + e.getKeySurname() + " " + e.getKeyData() + " " + e.getKeyInt() + " " + e.getKeyFloat());
            }
        });

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), db.update(new EntityPerson(5, "Toygur", "Bozkurt", "data", 3, 4.3f), "keyId") + " id updated.", Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), db.update(new EntityMaterial(5, "Toygur", "Bozkurt", "data"), "materialId") + " id updated", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
