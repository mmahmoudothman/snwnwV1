package com.snwnw.snwnw.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fifi elshafie on 2/24/2018.
 */

public class place_model implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("langitude")
    double langitude;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("logo")
    String logo ;
    @SerializedName("address")
    String address;
    @SerializedName("work_hours")
    String work_hours;
    @SerializedName("email")
    String email ;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("phone")
    String phone;
    @SerializedName("fb_page")
    String fb_page;
    @SerializedName("website_link")
    String website_link;
    @SerializedName("is_publish")
    int is_publish;
    @SerializedName("is_feature")
    int is_feature;

    @SerializedName("is_favourite")
    int is_favourite;

    @SerializedName("type")
    int type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork_hours() {
        return work_hours;
    }

    public void setWork_hours(String work_hours) {
        this.work_hours = work_hours;
    }

    @SerializedName("created_at")

    String created_at;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("rate")
    String rate;
    @SerializedName("images")
    ArrayList<String>images;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFb_page() {
        return fb_page;
    }

    public void setFb_page(String fb_page) {
        this.fb_page = fb_page;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public int getIs_publish() {
        return is_publish;
    }

    public void setIs_publish(int is_publish) {
        this.is_publish = is_publish;
    }

    public int getIs_feature() {
        return is_feature;
    }

    public void setIs_feature(int is_feature) {
        this.is_feature = is_feature;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public int getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
