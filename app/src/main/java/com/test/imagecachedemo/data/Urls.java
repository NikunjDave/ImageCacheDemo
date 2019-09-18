package com.test.imagecachedemo.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Urls implements Parcelable {
    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;


    // Getter Methods

    protected Urls(Parcel in) {
        raw = in.readString();
        full = in.readString();
        regular = in.readString();
        small = in.readString();
        thumb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(raw);
        dest.writeString(full);
        dest.writeString(regular);
        dest.writeString(small);
        dest.writeString(thumb);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Urls> CREATOR = new Creator<Urls>() {
        @Override
        public Urls createFromParcel(Parcel in) {
            return new Urls(in);
        }

        @Override
        public Urls[] newArray(int size) {
            return new Urls[size];
        }
    };

    public String getRaw() {
        return raw;
    }

    public String getFull() {
        return full;
    }

    public String getRegular() {
        return regular;
    }

    public String getSmall() {
        return small;
    }

    public String getThumb() {
        return thumb;
    }

    // Setter Methods

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}