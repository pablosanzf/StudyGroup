package com.example.usuariopc.lm3_studygroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;

public class StudentManager {
	
	private static final String FILENAME = "StudentsGroup";
	private Context mContext;
	
	public StudentManager(Context c){
		mContext = c;
	}
	
	public ArrayList<Student> loadStudents(){
		try {
			FileInputStream fis = mContext.openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			ArrayList<Student> arr = (ArrayList<Student>) ois.readObject();
			ois.close();
			fis.close();
			return arr;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public void saveStudents(ArrayList<Student> arr){
		try {
			FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(arr);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
