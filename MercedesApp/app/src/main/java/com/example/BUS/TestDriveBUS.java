package com.example.BUS;

import android.content.Context;

import com.example.DAO.MercedesDB;
import com.example.DTO.TestDrive;

import java.util.ArrayList;

public class TestDriveBUS {
    Context context;

    public TestDriveBUS(Context context) {
        this.context = context;
    }

    public boolean addTestDriveRegisterData(TestDrive testDriveData) {
        return new MercedesDB(context).addTestDriveRegisterData(testDriveData);
    }

    public ArrayList<TestDrive> getAllTestDriveData() {
        return new MercedesDB(context).getAllTestDriveData();
    }

    public TestDrive getTestDriveDetailData(String registerDate) {
        return new MercedesDB(context).getTestDriveDetailData(registerDate);
    }
}
