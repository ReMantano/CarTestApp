package com.example.auto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.auto.Adapter.Car;
import com.example.auto.Fragments.CarAddFragment;
import com.example.auto.Fragments.CarListFragment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_CAR_ID = "EXTRA_CAR_ID";

    public static final String query = "SELECT  car.car_id ,owner.owner_name, brand.brand_name, car.type, car.class, car.price, " +
                                    "car.date,car.name, car.engine, car.speed, car.fuel, car.transmission, image.car_image " +
                                    "FROM car INNER JOIN owner ON car.owner_id = owner.owner_id " +
                                    "INNER JOIN brand ON car.brand_id = brand.brand_id " +
                                    "INNER JOIN image ON car.image_id = image.image_id ";

    public final  String QUERY_INSERT_OWNER = "INSERT INTO owner VALUES(null,'";
    public final  String QUERY_INSERT_BRAND = "INSERT INTO brand VALUES(null,'";
    public final  String QUERY_INSERT_CAR = "INSERT INTO car VALUES (null,'";
    public final  String QUERY_SELECT_BRAND = "SELECT brand_id FROM brand WHERE brand_name = '";
    public final  String QUERY_SELECT_ID_OWNER = "SELECT owner_id FROM owner WHERE owner_name = '";
    public final  String QUERY_SELECT_ID_BRAND = "SELECT brand_id FROM brand WHERE brand_name = '";
    public final String  QUERY_UPDATE_CAR = "UPDATE car SET owner_id = ";
    public final String  QUERY_GET_LAST_ID = "SELECT seq FROM sqlite_sequence WHERE name = ";

    public static final String FRAGMENT_CREATE_CAR_TAG = "FRAGMENT_CREATE_CAR_TAG";
    public static final String FRAGMENT_EDIT_CAR_TAG = "FRAGMENT_EDIT_CAR_TAG";
    public static final String FRAGMENT_LIST_CAR_TAG = "FRAGMENT_LIST_CAR_TAG";



    public static final String DB_NAME = "android.db";
    private File DB_PATH;
    public int currentID = 0;

    private final int SELECT_PICTURE = 1;

    public ArrayList<Car> list;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        list = new ArrayList<Car>();

        try {
            createDataBase();
        } catch (IOException e) {
            Log.d("Error",  e.toString());
        }

        db = openDataBase(this);


        getCarList(query);

        Fragment fragment = new CarListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.addToBackStack(FRAGMENT_LIST_CAR_TAG);

        transaction.replace(R.id.containerFragment,fragment,FRAGMENT_LIST_CAR_TAG);

        transaction.commit();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            if(requestCode == SELECT_PICTURE){

                ((CarAddFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_CREATE_CAR_TAG))
                        .getImagePatch(data.getData());


            }
        }

    }

    public void createDataBase() throws IOException {

        DB_PATH = getExternalCacheDir();

        DB_PATH.mkdirs();

        File db = new File(DB_PATH, DB_NAME);
        if (!db.exists()) {

            db.createNewFile();
            try {
                copyFromZipFile();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private void copyFromZipFile() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.android);
        File outFile = new File(DB_PATH, DB_NAME);
        OutputStream myOutput = new FileOutputStream(outFile.getAbsolutePath());
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        try {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }
                baos.writeTo(myOutput);
            }
        } finally {
            zis.close();
            myOutput.flush();
            myOutput.close();
            is.close();
        }
    }

    public static SQLiteDatabase openDataBase(Context context) throws SQLException {
        File DB_PATH = context.getExternalCacheDir();
        File dbFile = new File(DB_PATH, DB_NAME);
        SQLiteDatabase myDataBase =
                SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase;
    }


    public void sortPrice(String s1, String s2, String s3){
        list.clear();

        String s = "";
        if(s1.equals("")) {
            if (!s2.equals("")) s = "WHERE ";

        }else {
            if  (!s2.equals("") ) s = "AND ";
        }


        getCarList(query + s1 + s + s2 + s3);


    }

    private void getCarList(String query){

            Cursor cur = db.rawQuery(query, null);

            if (cur.getCount() == 0) return ;
            cur.moveToFirst();


            do {
                String owner = cur.getString(cur.getColumnIndex("owner.owner_name"));
                String brand = cur.getString(cur.getColumnIndex("brand.brand_name"));
                String type = cur.getString(cur.getColumnIndex("car.type"));
                String class_type = cur.getString(cur.getColumnIndex("car.class"));
                String transmission = cur.getString(cur.getColumnIndex("car.transmission"));
                String name = cur.getString(cur.getColumnIndex("car.name"));

                int id = cur.getInt(cur.getColumnIndex("car.car_id"));
                int price = cur.getInt(cur.getColumnIndex("car.price"));
                int date = cur.getInt(cur.getColumnIndex("car.date"));
                int engine = cur.getInt(cur.getColumnIndex("car.engine"));

                int speed = cur.getInt(cur.getColumnIndex("car.speed"));
                int fuel = cur.getInt(cur.getColumnIndex("car.fuel"));

                Car a;

                byte[] bit = cur.getBlob(cur.getColumnIndex("image.car_image"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(bit, 0, bit.length);

                a = new Car(owner, brand,name, type, class_type, transmission,
                            id,price, date, engine, speed, fuel, bitmap);

                list.add(a);

            } while (cur.moveToNext());


    }

    public ArrayList<String> getOwners(){

        ArrayList<String> list  = new ArrayList<String>();
        list.add("Owner");

        Cursor cur = db.rawQuery("SELECT owner_name FROM owner",null);
        cur.moveToFirst();

        do{
            list.add(cur.getString(cur.getColumnIndex("owner_name")));
        }
        while(cur.moveToNext());


        return list;
    }

    public ArrayList<String> getBrands(){

        ArrayList<String> list  = new ArrayList<String>();
        list.add("Brand");

        Cursor cur = db.rawQuery("SELECT brand_name FROM brand",null);
        cur.moveToFirst();

        do{
            list.add(cur.getString(cur.getColumnIndex("brand_name")));
        }
        while(cur.moveToNext());


        return list;
    }

    public void saveNewCar(Car car){

        Cursor c = db.rawQuery(QUERY_SELECT_ID_OWNER + car.owner + "'",null);

        if (c.getCount() == 0){
            db.execSQL(QUERY_INSERT_OWNER + car.owner + "')");
        }

        c = db.rawQuery(QUERY_SELECT_BRAND + car.brand + "'",null);

        if (c.getCount() == 0){
            db.execSQL(QUERY_INSERT_BRAND + car.brand + "',(" + QUERY_SELECT_ID_OWNER +
                    car.owner + "'))");
        }



        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = car.bitmap;
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);

        byte[] bytes = stream.toByteArray();


        String ss = "INSERT INTO image  VALUES(?,?)";
        SQLiteStatement s = db.compileStatement(ss);
        s.bindNull(1);
        s.bindBlob(2,bytes);
        s.executeInsert();



        db.execSQL(QUERY_INSERT_CAR + car.name + "','" + car.type + "','" + car.class_type + "'," + car.price
                + "," + car.date  + "," + car.engine + "," + car.speed + "," + car.fuel+ ",'" + car.transmission
                + "',(" + QUERY_SELECT_ID_BRAND + car.brand + "'),("+QUERY_SELECT_ID_OWNER + car.owner + "')," +
                "(" +QUERY_GET_LAST_ID + "'image'))");

        Toast.makeText(this,"New car added",Toast.LENGTH_SHORT).show();

    }

    public void saveEditCar(Car car){

        db.execSQL("UPDATE car SET type = '" + car.type + "',class = '" + car.class_type + "'"+
                ",price = " + car.price + ",date = "+  car.date + ",engine = "+car.engine+
                ",speed = " + car.speed + ",fuel = " + car.fuel +
                ", transmission = '" + car.transmission + "' WHERE car_id = " +car.id);

        Toast.makeText(this,"Edit secsesfull",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {


        if ((getSupportFragmentManager().getFragments().get(0).getClass() == CarListFragment.class )){
            finish();
        }
        else super.onBackPressed();
    }
}
