package com.example.usuariopc.lm3_studygroup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.ListActivity;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemberListActivity extends ListActivity {

    static final int SELECT_CONTACT = 1;

    private ArrayList<Student> arrStudents;
    private ArrayAdapter<Student> adpStudents;
    private int posicionClase = new Integer(0);



    //Sirve comonexo de unión entre la estructura de datos y
    // el elemento de visualización. Para decidir que añadimos en la lista de los datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_member_list); no interesa porque no tendría la lista agregada
        createStudentList();
        adpStudents = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arrStudents) {

            @Override //es una implementación diferente del getview del arrayAdapter
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(arrStudents.get(position).getName());
                text2.setText(arrStudents.get(position).getPhoneNumber());
                return view;
            }
        };
        setListAdapter(adpStudents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.member_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_add_student) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI); //es la URI propia de la listas de contactos
            startActivityForResult(intent, SELECT_CONTACT); //es lanzar una actividad acon la finalidad de que haya un resultado, SELECT_CONTACT es el código de invocación propio de esta actividad
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //aparece cada vez que hay una activity esperando un resultado
        if (requestCode == SELECT_CONTACT) { //Comprobar que el request code es el que mandaste
            if (resultCode == RESULT_OK) { //El usuario ha completado tod0 el proceso
                Cursor cursor = getContentResolver().query(data.getData(), new String[]  /*
                    El content representa el una llamada al contenido, como si una base de datos se tratase
                    además esta consultas se hace como en SQL, Priemro la tabla que en nuestro caso es el data y luego el array
                       de strings es las cosas que se han de seleccionar como "select __array de strings__ , from ___tabla___", ....where
                       ese where es los valores nulls pero se podria añadir wheres añadiendo arrays de strings donde por ejemplo el número empezase por 7*/
                        {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                /*Y esto devuelve un cursor que además está situado en el punto -1 por ello se necesita el moveNext*/
                if(cursor.moveToNext()){
                    Log.i("Intent:",data.getDataString()); //Log es lo que utilicamos para sacar información por log
                    arrStudents.add(new Student(cursor.getString(0),
                            cursor.getString(1))); //Y aquí se añaden los datos de la tabla, siendo el nombre el 0 y el número el 1
                    adpStudents.notifyDataSetChanged(); //Método propio del adaptador que se autoreferesca
                    return;
                }
            }
        }
        // Recommended
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void createStudentList() {
        arrStudents = new ArrayList<Student>();
        arrStudents.add(new Student("Jose Luis", "611111234"));
        arrStudents.add(new Student("Ibai", "22222222"));
        arrStudents.add(new Student("Itziar", "33333333"));
        arrStudents.add(new Student("Irene", "44444444"));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        posicionClase = position;
        callStudent(position);
    }

    private void callStudent(int pos) {
       /* Toast.makeText(
                getBaseContext(),
                "Calling " + arrStudents.get(pos).getPhoneNumber(),
                Toast.LENGTH_LONG)
                .show();*/
        if (Build.VERSION.SDK_INT >= 23 &&
                this.checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_DENIED) {
            this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent2 = new Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:" + arrStudents.get(pos).getPhoneNumber()));
            startActivity(intent2);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:

                if (grantResults.length == 0) { // Permissions nor granted
                } else {
                    if (permissions[0].equals(Manifest.permission.CALL_PHONE) &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission granted!
                        callStudent(posicionClase);
                    }
                }
                return;
        }
    }







}


