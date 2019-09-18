package com.test.imagecachedemo.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Links implements Parcelable {
    private String self;
    private String html;
    private String download;
    private String download_location;


    // Getter Methods

    protected Links(Parcel in) {
        self = in.readString();
        html = in.readString();
        download = in.readString();
        download_location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(html);
        dest.writeString(download);
        dest.writeString(download_location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    public String getSelf() {
        return self;
    }

    public String getHtml() {
        return html;
    }

    public String getDownload() {
        return download;
    }

    public String getDownload_location() {
        return download_location;
    }

    // Setter Methods

    public void setSelf(String self) {
        this.self = self;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public void setDownload_location(String download_location) {
        this.download_location = download_location;
    }
}