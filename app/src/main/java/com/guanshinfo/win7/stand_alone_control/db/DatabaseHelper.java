/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * sqlite帮助类
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "policyDb";
    static final String employeeTable = "pkg";
    static final String colID = "pkgID";
    static final String colName = "pkgName";
    static final String colAge = "Age";//测试数据
    static final String colDept = "policyDept";

    static final String deptTable = "policy";
    static final String colDeptID = "policyID";
    static final String colDeptName = "policyName";
    static final String viewEmps = "ViewEmps";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + deptTable + " (" + colDeptID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colDeptName + " TEXT)");

        db.execSQL("CREATE TABLE " + employeeTable + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colName + " TEXT, " + colAge + " Integer, " + colDept + " INTEGER NOT NULL ,FOREIGN KEY (" + colDept + ") REFERENCES " + deptTable + " (" + colDeptID + "));");

        //创建触发器
        db.execSQL("CREATE TRIGGER fk_empdept_deptid " +
                " BEFORE INSERT " +
                " ON " + employeeTable +

                " FOR EACH ROW BEGIN" +
                " SELECT CASE WHEN ((SELECT " + colDeptID + " FROM " + deptTable + " WHERE " + colDeptID + "=new." + colDept + " ) IS NULL)" +
                " THEN RAISE (ABORT,'Foreign Key Violation') END;" +
                "  END;");
        //创建视图
        db.execSQL("CREATE VIEW " + viewEmps +
                " AS SELECT " + employeeTable + "." + colID + " AS _id," +
                " " + employeeTable + "." + colName + "," +
                " " + employeeTable + "." + colAge + "," +
                " " + deptTable + "." + colDeptName + "" +
                " FROM " + employeeTable + " JOIN " + deptTable +
                " ON " + employeeTable + "." + colDept + " =" + deptTable + "." + colDeptID
        );
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + employeeTable);
        db.execSQL("DROP TABLE IF EXISTS " + deptTable);

        db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
        db.execSQL("DROP VIEW IF EXISTS " + viewEmps);
        onCreate(db);
    }
}
