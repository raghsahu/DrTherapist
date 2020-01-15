package com.example.drtherapist.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravindra Birla on 27/04/2019.
 */
public class UserInfoData {
   // public String id;
    //public String cat_id;
   // public String username;
   /// public String mobile;
   // public String email;
    //public String password;
   // public String address;
    //public String image;
    //public String availability;
   // public String timing;
   // public String fee;
//    public String specialization;
//    public String lat;
//    public String lng;
//    public String location;
//    public String experience;
//    public String review;
//    public String resume;
//    public String age;
//    public String date;
//    public String dob;
//    public String city;
//    public String state;
//    public String zip;
    public String social_id;
    public String oauth_provider;
   // public String status;
    //public String rating;
   // public String doller_rate;
    //public String type;
    //public String gender;
   // public String register_id;
    public String date_time;
    public String price;
    public String family_info;
    public String famly_member;

    @SerializedName("dr_id")
    @Expose
    public String id;
    @SerializedName("cat_id")
    @Expose
    public String cat_id;
    @SerializedName("cate_name")
    @Expose
    public String cateName;
    @SerializedName("dr_fname")
    @Expose
    public String username;
    @SerializedName("dr_contact")
    @Expose
    public String mobile;
    @SerializedName("dr_email")
    @Expose
    public String email;
    @SerializedName("dr_pass")
    @Expose
    public String password;
    @SerializedName("dr_address")
    @Expose
    public String address;
    @SerializedName("dr_image")
    @Expose
    public String image;
    @SerializedName("dr_availability")
    @Expose
    public String availability;
    @SerializedName("timing")
    @Expose
    public String timing;
    @SerializedName("fee")
    @Expose
    public String fee;
    @SerializedName("specialization")
    @Expose
    public String specialization;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lng")
    @Expose
    public String lng;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("experience")
    @Expose
    public String experience;
    @SerializedName("review")
    @Expose
    public String review;
    @SerializedName("resume")
    @Expose
    public String resume;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("created_date")
    @Expose
    public String date;
    @SerializedName("updated_date")
    @Expose
    public String updatedDate;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("zip")
    @Expose
    public String zip;
    @SerializedName("auth_id")
    @Expose
    public String authId;
    @SerializedName("fcm_id")
    @Expose
    public String register_id;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("doller_rate")
    @Expose
    public String doller_rate;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("dr_unique_key")
    @Expose
    public String drUniqueKey;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("Monday")
    @Expose
    public String monday;
    @SerializedName("Tuesday")
    @Expose
    public String tuesday;
    @SerializedName("Wednesday")
    @Expose
    public String wednesday;
    @SerializedName("Thursday")
    @Expose
    public String thursday;
    @SerializedName("Friday")
    @Expose
    public String friday;
    @SerializedName("Saturday")
    @Expose
    public String saturday;
    @SerializedName("Sunday")
    @Expose
    public String sunday;
    @SerializedName("high_education")
    @Expose
    public String highEducation;
    @SerializedName("linked_url")
    @Expose
    public String linkedUrl;
    @SerializedName("licence_nmbr")
    @Expose
    public String licenceNmbr;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getFamly_member() {
        return famly_member;
    }

    public void setFamly_member(String famly_member) {
        this.famly_member = famly_member;
    }

    public String getFamily_info() {
        return family_info;
    }

    public void setFamily_info(String family_info) {
        this.family_info = family_info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getOauth_provider() {
        return oauth_provider;
    }

    public void setOauth_provider(String oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDrUniqueKey() {
        return drUniqueKey;
    }

    public void setDrUniqueKey(String drUniqueKey) {
        this.drUniqueKey = drUniqueKey;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getHighEducation() {
        return highEducation;
    }

    public void setHighEducation(String highEducation) {
        this.highEducation = highEducation;
    }

    public String getLinkedUrl() {
        return linkedUrl;
    }

    public void setLinkedUrl(String linkedUrl) {
        this.linkedUrl = linkedUrl;
    }

    public String getLicenceNmbr() {
        return licenceNmbr;
    }

    public void setLicenceNmbr(String licenceNmbr) {
        this.licenceNmbr = licenceNmbr;
    }
}
