package com.exmaple.contactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Users extends RealmObject implements Parcelable {

    String firstName;
    String lastName;
    String number;

    public Users(String firstName, String lastName, String number, long timestamp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.timestamp = timestamp;
    }

    public Users() {
    }

    protected Users(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        number = in.readString();
        timestamp = in.readLong();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @PrimaryKey

    long timestamp;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(number);
        dest.writeLong(timestamp);
    }
}
