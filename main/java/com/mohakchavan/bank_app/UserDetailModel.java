package com.mohakchavan.bank_app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohak Chavan on 16-10-2017.
 */

public class UserDetailModel implements Parcelable {
    int id, pass;
    String name, sname;
    double amt;

    protected UserDetailModel(Parcel in) {
        id = in.readInt();
        pass = in.readInt();
        name = in.readString();
        sname = in.readString();
        amt = in.readDouble();
    }

    public static final Creator<UserDetailModel> CREATOR = new Creator<UserDetailModel>() {
        @Override
        public UserDetailModel createFromParcel(Parcel in) {
            return new UserDetailModel(in);
        }

        @Override
        public UserDetailModel[] newArray(int size) {
            return new UserDetailModel[size];
        }
    };

    public UserDetailModel() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeInt(pass);
        dest.writeString(name);
        dest.writeString(sname);
        dest.writeDouble(amt);
    }
}
