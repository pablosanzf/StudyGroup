package com.example.usuariopc.lm3_studygroup;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemberListActivity extends ListActivity {

    private ArrayList<Student> arrStudents;
    private ArrayAdapter<Student> adpStudents;
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

    private void createStudentList(){
        arrStudents = new ArrayList<Student>();
        arrStudents.add(new Student("Mikel","11111111"));
        arrStudents.add(new Student("Ibai","22222222"));
        arrStudents.add(new Student("Itziar","33333333"));
        arrStudents.add(new Student("Irene","44444444"));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        callStudent(position);
    }

    private void callStudent(int pos){
        Toast.makeText(
                getBaseContext(),
                "Calling " + arrStudents.get(pos).getPhoneNumber(),
                Toast.LENGTH_LONG)
                .show();
    }

}
