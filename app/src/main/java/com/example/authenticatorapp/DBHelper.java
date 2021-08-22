package com.example.authenticatorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "Login2.db";
    public static final String TABLE_SERVICES = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FORMS = "forms";
    public static final String COLUMN_DOCUMENTS = "documents";



    public DBHelper(Context context) {
        super(context, "Login2.db", null, 23);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(email TEXT primary key, username TEXT,job TEXT,password TEXT)");
        MyDB.execSQL("create Table services(name TEXT primary key, forms TEXT,documents TEXT,rate TEXT, branch TEXT)");
        MyDB.execSQL("create Table branch(name TEXT, phoneNumber TEXT primary key,address TEXT)");
        MyDB.execSQL("create Table branchServices(name TEXT , forms TEXT,documents TEXT, rate TEXT,branch TEXT,ID TEXT primary key)");
        MyDB.execSQL("create Table workingDates(day TEXT, startTime TEXT,endTime TEXT,user TEXT, id TEXT primary key)");
        MyDB.execSQL("create Table serviceRequest(name TEXT, status TEXT ,user TEXT, asker,id TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists services");
        MyDB.execSQL("drop Table if exists branch");
        MyDB.execSQL("drop Table if exists branchServices");
        MyDB.execSQL("drop Table if exists workingDates");
        MyDB.execSQL("drop Table if exists serviceRequest");

        onCreate(MyDB);
    }

    public Boolean sendRequest(String name, String user, String status, String asker){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("status", status);
        contentValues.put("user", user);
        contentValues.put("id", name+user+asker);
        contentValues.put("asker",asker);
        long result = MyDB.insert("serviceRequest", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertData(String email, String username, String password, String job) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("job", job);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor loginCheck(String email){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=?", new String[]{email});
        return cursor;
    }

    public Cursor viewBranch(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from branch ",null);

        return cursor;

    }

    public Cursor viewUser(String start,String end,String day){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select user from workingDates where startTime = ? and endTime = ? and day = ?",new String[]{start,end,day});

        return cursor;

    }

    public Cursor viewRequestsUser(String asker){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from serviceRequest where asker = ? and status = ?",new String[]{asker,"Accepted"});

        return cursor;

    }

    public Cursor viewRequestsUser1(String asker){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from serviceRequest where asker = ? and status = ?",new String[]{asker,"Declined"});

        return cursor;

    }

    public Cursor viewRequests(String user){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from serviceRequest where user = ? and status = ?",new String[]{user,"Pending"});

        return cursor;

    }


    public Cursor viewStart(String day){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select startTime from workingDates where day =? ",new String[]{day});

        return cursor;
    }

    public Cursor viewEnd(String day){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select endTime from workingDates where day =? ",new String[]{day});

        return cursor;
    }

    public Cursor ViewRole(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select job from users where email=?", new String[]{email});

        return cursor;
    }



    public Cursor ViewName(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select username from users where job=?", new String[]{email});

        return cursor;
    }
    public Cursor ViewU(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select email from users ", null);

        return cursor;
    }

    public Cursor ViewName1(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select username from users where email=?", new String[]{email});

        return cursor;
    }

    public Cursor viewB(String address){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from branch where address=?", new String[]{address});

        return cursor;
    }



    public Cursor ViewServices(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select name from branchServices where branch=?", new String[]{email});

        return cursor;
    }

    public Cursor ViewBranchServices(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select branch from branchServices where name=?", new String[]{email});

        return cursor;
    }

    public Cursor viewAddress(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select address from branch ",null);

        return cursor;
    }

    public void updateService(String name, String forms, String documents, String rate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE services SET forms = \"" + forms + "\"" + " WHERE name = \"" + name + "\"";
        String query1 = "UPDATE services SET documents = \"" + documents + "\"" + " WHERE name = \"" + name + "\"";
        String query2 = "UPDATE services SET rate = \"" + rate + "\"" + " WHERE name = \"" + name + "\"";
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }

    public void updateRequest(String name, String user,String response){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE serviceRequest SET status = \"" + response + "\"" + " WHERE name = \"" + name +"\""+ "AND user = \"" + user +"\"";
        db.execSQL(query);
    }

    public void updateTime(String day, String start, String end, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE workingDates SET startTime = \"" + start + "\"" + " WHERE day = \"" + day + "\""+ "AND user = \"" + user +"\"";
        String query1 = "UPDATE workingDates SET endTime = \"" + end + "\"" + " WHERE day = \"" + day + "\""+ "AND user = \"" + user +"\"";
        db.execSQL(query);
        db.execSQL(query1);
    }


    public Boolean addService(Service service) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", service.getName());
        values.put("forms", service.getForms());
        values.put("documents", service.getDocuments());
        values.put("rate", service.getRate());

        long result = db.insert("services", null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean addBranchService(Service service) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID",service.getID());
        values.put("name", service.getName());
        values.put("forms", service.getForms());
        values.put("documents", service.getDocuments());
        values.put("rate",service.getRate());
        values.put("branch",service.getBranch());

        long result = db.insert("branchServices", null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertHour(String day,String start, String end, String user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("day",day);
        contentValues.put("startTime",start);
        contentValues.put("endTime",end);
        contentValues.put("user",user);
        contentValues.put("id",user+day);

        long result = MyDB.insert("workingDates",null,contentValues);
        if(result==-1){
            return false;
        } else {
            return true;
        }
    }
    public Boolean insertDataBranch(String name, String phoneNumber, String streetNumber, String streetName, String city) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("address", streetNumber+" "+streetName+" "+city);


        long result = MyDB.insert("branch", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //method to view Data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query= "Select * from services"  ;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor viewService(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from services where name=?", new String[]{name});

        return cursor;
    }


    public Cursor viewDataBranch(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from branchServices where branch=?", new String[]{email});

        return cursor;

    }


    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }



    public Boolean checkDay(String day, String user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from workingDates where day=? and user = ?", new String[]{day,user});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkemailpassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void deleteService(String serviceName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM services WHERE name  = \"" + serviceName + "\"";
        String query1 = "DELETE FROM branchServices WHERE name  = \"" + serviceName + "\"";
        db.execSQL(query);
        db.execSQL(query1);
    }

    public void deleteServiceBranch(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM branchServices WHERE branch = ? and name = ?",new String[]{email,name});
    }

    public Cursor returnService (String service){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * FROM services WHERE name  = \"" + service + "\"";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


    public Cursor returnTime (String day, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * FROM workingDates WHERE day  = \"" + day + "\""+ "AND user = \"" + user +"\"";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public void deleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM users WHERE email = \""+email+"\"";
        db.execSQL(query);

    }
}

