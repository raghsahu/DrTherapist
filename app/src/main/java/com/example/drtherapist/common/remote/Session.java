package com.example.drtherapist.common.remote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.BasicActivities.LoginActivity;
import com.google.gson.Gson;

public class Session extends Object {

    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "Dr_pref";
    private static final String PREF_NAME2 = "Dr_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String FAV = "fav";
    private static final String Name = "name";


    private static final String Date = "date";
    private static final String City = "city";
    private static final String Zip = "Zip";
    private static final String Phone = "Phone";
    private static final String State = "state";
    private static final String Exeperience = "Exeperience";
    private static final String Age = "Age";
    private static final String HighEdu = "HighEdu";
    private static final String ProfileUrl = "ProfileUrl";
    private static final String LicenceNumber = "LicenceNumber";
    private static final String Image = "image";

    private static final String Mobile = "mobile";
    private static final String Resume = "resume";
    private static final String Email = "email";
    private static final String Gender="gender";
    private static final String Token_Id = "token";
    private static SharedPreferences sharedPreferences;
    private Context _context;
    private SharedPreferences mypref, mypref2;
    private SharedPreferences.Editor editor, editor2, editor3;

    public Session(Context context) {
        this._context = context;
        mypref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mypref.edit();
        editor.apply();

        mypref2 = _context.getSharedPreferences(PREF_NAME2, Context.MODE_PRIVATE);
        editor2 = mypref2.edit();
        editor2.apply();
    }

    public void createSession(UserInfoData userInfoData) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfoData);
        editor.putString("user", json);
        //editor.putBoolean(IS_LOGGEDIN, true);
        editor.putBoolean("IsLogin", true);
        editor.apply();
    }

    public UserInfoData getUser() {
        Gson gson = new Gson();
        String str = mypref.getString("user", "");
        if (str.isEmpty())
            return null;
        return gson.fromJson(str, UserInfoData.class);
    }







    public void setResume( String resume) {
        editor2.putString(Resume, resume);
        editor2.apply();
        editor2.commit();
    }
    public String getResume() {
        return mypref2.getString(Resume, "");

    }



    public void setName( String name) {
        editor2.putString(Name, name);
        editor2.apply();
        editor2.commit();
    }
    public String getName() {
        return mypref2.getString(Name, "");

    }




    public void setGender( String gender) {
        editor2.putString(Gender, gender);
        editor2.apply();
        editor2.commit();
    }
    public String getGender() {
        return mypref2.getString(Gender, "");

    }




    public void setDate( String date) {
        editor2.putString(Date, date);
        editor2.apply();
        editor2.commit();
    }
    public String getDate() {
        return mypref2.getString(Date, "");
    }




    public void setCity( String city) {
        editor2.putString(City, city);
        editor2.apply();
        editor2.commit();
    }
    public String getCity() {
        return mypref2.getString(City, "");
    }





    public void setZip( String zip) {
        editor2.putString(Zip, zip);
        editor2.apply();
        editor2.commit();
    }
    public String getZip() {
        return mypref2.getString(Zip, "");
    }




    public void setState( String state) {
        editor2.putString(State, state);
        editor2.apply();
        editor2.commit();
    }
    public String getState() {
        return mypref2.getString(State, "");
    }


    public void setPhone( String phone) {
        editor2.putString(Phone, phone);
        editor2.apply();
        editor2.commit();
    }
    public String getPhone() {
        return mypref2.getString(Phone, "");
    }




    public void setExeperience( String exeperience) {
        editor2.putString(Exeperience, exeperience);
        editor2.apply();
        editor2.commit();
    }
    public String getExeperience() {
        return mypref2.getString(Exeperience, "");
    }




    public void setAge( String age) {
        editor2.putString(Age, age);
        editor2.apply();
        editor2.commit();
    }
    public String getAge() {
        return mypref2.getString(Age, "");
    }





    public void setHighEdu( String highedu) {
        editor2.putString(HighEdu, highedu);
        editor2.apply();
        editor2.commit();
    }
    public String getHighEdu() {
        return mypref2.getString(HighEdu, "");
    }


    public void setProfileUrl( String profileUrl) {
        editor2.putString(ProfileUrl, profileUrl);
        editor2.apply();
        editor2.commit();
    }
    public String getProfileUrl() {
        return mypref2.getString(ProfileUrl, "");
    }




    public void setLicenceNumber( String licenceNumber) {
        editor2.putString(LicenceNumber, licenceNumber);
        editor2.apply();
        editor2.commit();
    }
    public String getLicenceNumber() {
        return mypref2.getString(LicenceNumber, "");
    }










    public void setImage( String image) {
        editor2.putString(Image, String.valueOf(image));
        editor2.apply();
        editor2.commit();
    }
    public String getImage() {
        return mypref2.getString(Image, "");

    }








    public void setMobile(String mobile, String email) {

        editor2.putString(Mobile, mobile);
        editor2.putString(Email, email);
        editor2.apply();
        editor2.commit();
    }


    public String getMobile() {
        return mypref2.getString(Mobile, "");

    }

    public String getEmail() {
        return mypref2.getString(Email, "");

    }


    public void saveToken(String token) {

        editor2.putString(Token_Id, token);
        editor2.apply();
        editor2.commit();
    }

    public String getTokenId() {
        return mypref2.getString(Token_Id, "");
    }



    /*public void setPassword(String password) {
        editor2.putString("userpassword", password);
        this.editor2.apply();
    }

    public void setReminderState(boolean isset) {
        editor2.putBoolean("ReminderState", isset);
        this.editor2.apply();
    }


    public void setEmailPhone(String emailPhone) {
        editor2.putString("emailPhone", emailPhone);
        this.editor2.apply();
    }

    public String getEmailPhone() {
        return mypref2.getString("emailPhone", "");
    }

    public boolean getReminderState() {
        return mypref2.getBoolean("ReminderState", true);
    }


    public String getPassword() {
        return mypref2.getString("userPassword", "");
    }

    public void rememberMe(String user, String password) {
        editor2.putString("email", getEmailPhone());
        editor2.putString("pass", getPassword());
        editor2.apply();
    }*/

    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }

    public boolean isLoggedIn() {
        //return mypref.getBoolean(IS_LOGGEDIN, false);
        return mypref.getBoolean("IsLogin", false);
    }


    public void rememberMe(String email, String password) {
        editor2.putString("rem_email", email);
        editor2.putString("rem_password", password);
        editor2.apply();
    }

    public String getRemEmail() {
        return mypref2.getString("rem_email", "");
    }

    public String getRemPassword() {
        return mypref2.getString("rem_password", "");
    }

    public void setPriceRangerValue(String product_name, String quantity, String minValue, String maxValue) {
        editor.putString("product_name", product_name);
        editor.putString("quantity", quantity);
        editor.putString("minPrice", minValue);
        editor.putString("maxPrice", maxValue);
        editor.apply();
    }

    public String getMinValue() {
        return mypref.getString("minPrice", "0");
    }

    public String getMaxValue() {
        return mypref.getString("maxPrice", "500");
    }

    public String getProductName() {
        return mypref.getString("product_name", "");
    }

    public String getProductQuantity() {
        return mypref.getString("quantity", "");
    }
}
