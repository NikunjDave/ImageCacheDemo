package com.test.imagecachedemo.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable  {
    private String id;
    private String created_at;
    private String updated_at;
    private float width;
    private float height;
    private String color;
    private String description = null;
    private String alt_description;

    @SerializedName("urls")
    Urls UrlObjects;
    @SerializedName("links")
    Links LinksObject;


    protected Photo(Parcel in) {
        id = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        width = in.readFloat();
        height = in.readFloat();
        color = in.readString();
        description = in.readString();
        alt_description = in.readString();
        UrlObjects = in.readParcelable(Urls.class.getClassLoader());
        LinksObject = in.readParcelable(Links.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeString(color);
        dest.writeString(description);
        dest.writeString(alt_description);
        dest.writeParcelable(UrlObjects, flags);
        dest.writeParcelable(LinksObject, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public Urls getUrls() {
        return UrlObjects;
    }

    public Links getLinks() {
        return LinksObject;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlt_description(String alt_description) {
        this.alt_description = alt_description;
    }

    public void setUrls(Urls urlsObject) {
        this.UrlObjects = urlsObject;
    }

    public void setLinks(Links linksObject) {
        this.LinksObject = linksObject;
    }
}

