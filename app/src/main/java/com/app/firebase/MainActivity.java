package com.app.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.firebase.Modelo.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText txtRun, txtNom, txtNum, txtCiudad;
    Button btnIn, btnElim, btnBusc, btnAct;

    FirebaseDatabase FBData;
    DatabaseReference DBReference,DBMostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtRun = (EditText) findViewById(R.id.txtRu);
        txtNom = (EditText) findViewById(R.id.txtNo);
        txtCiudad = (EditText) findViewById(R.id.txtCi);
        txtNum = (EditText) findViewById(R.id.txtNu);

        btnIn = (Button) findViewById(R.id.button_ING);
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RUN = txtRun.getText().toString().trim().toUpperCase();
                String NOMBRE = txtNom.getText().toString().trim().toUpperCase();
                String CIUDAD = txtCiudad.getText().toString().trim().toUpperCase();
                String NUMERO = txtNum.getText().toString().trim().toUpperCase();
                if(RUN.equals("")||NOMBRE.equals("")||CIUDAD.equals("")||NUMERO.equals(""))
                {
                    validar();
                }
                else {
                    Persona P = new Persona();
                    P.setRun(RUN);
                    P.setCiudad(CIUDAD);
                    P.setNombre(NOMBRE);
                    P.setNUmero(NUMERO);
                    DBReference.child("Persona").child(P.getRun()).setValue(P);
                    Toast.makeText(MainActivity.this, "GUARDANDO !!!!!", Toast.LENGTH_LONG).show();
                    limpiar();
                }
            }
        });


        btnElim = (Button) findViewById(R.id.button_ELIM);
        btnElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RUN = txtRun.getText().toString().trim().toUpperCase();
                if(RUN.equals(""))
                {
                    validar();
                }
                else {
                    DBReference.child("Persona").child(RUN).removeValue();
                    Toast.makeText(MainActivity.this, "ELIMINANDO !!!!!", Toast.LENGTH_LONG).show();
                    limpiar();
                }
            }
        });

        btnBusc = (Button) findViewById(R.id.button_Cons);
        btnBusc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] cont = {0};
                DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Persona");
                /*DBMostrar = FirebaseDatabase.getInstance().getReference().child("Persona");
                DBMostrar.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            Toast.makeText(MainActivity.this, "ACA", Toast.LENGTH_LONG).show();
                            String RUN = txtRun.getText().toString().trim().toUpperCase();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Persona miP = snapshot.getValue(Persona.class);
                                if (miP.getRun().equals(RUN)) {
                                    txtNom.setText(miP.getNombre());
                                    txtCiudad.setText(miP.getCiudad());
                                    txtNum.setText(miP.getNUmero());
                                    break;
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                */
                String RUN = txtRun.getText().toString().trim().toUpperCase();
                Query q=bbdd.orderByChild("run").equalTo(RUN);

                /*
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;
                            Toast.makeText(MainActivity.this, "He encontrado "+cont, Toast.LENGTH_LONG).show();

                            Persona td =dataSnapshot.getValue(Persona.class);


                            System.out.println(td.getRun());



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                */
                q.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont[0]++;
                            System.out.println(dataSnapshot.getValue());

                            Persona miP = dataSnapshot.getValue(Persona.class);
                            txtNom.setText(miP.getNombre().toString());
                            txtCiudad.setText(miP.getCiudad().toString());
                            txtNum.setText(miP.getNUmero().toString());
                            Toast.makeText(MainActivity.this, "ENCONTRADO !!! ", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(cont[0] ==0)
                {
                    Toast.makeText(MainActivity.this, "NO EXISTE ESA PERSONA!!! ", Toast.LENGTH_LONG).show();
                }
            }




        });


        btnAct = (Button) findViewById(R.id.button_UPD);
        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ACTUALIZANDO !!!!!", Toast.LENGTH_LONG).show();
            }
        });

        iniciar_firebase();

    } // FIN ONCREATE

    private void iniciar_firebase() {
        FirebaseApp.initializeApp(this);
        this.FBData = FirebaseDatabase.getInstance();
        this.DBReference = this.FBData.getReference();
    }

    private void limpiar() {
        this.txtRun.setText("");
        this.txtNom.setText("");
        this.txtCiudad.setText("");
        this.txtNum.setText("");
    }

    private void validar() {
        if(txtRun.getText().toString().equals(""))
        {
            txtRun.setError("Escriba Run");
        }
        if(txtNom.getText().toString().equals(""))
        {
            txtNom.setError("Escriba Nombre");
        }
        if(txtCiudad.getText().toString().equals(""))
        {
            txtCiudad.setError("Escriba Ciudad");
        }
        if(txtNum.getText().toString().equals(""))
        {
            txtNum.setError("Escriba Numero");
        }

    }


}