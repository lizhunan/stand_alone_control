/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;

import java.util.List;

import static com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper.*;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 数据库操作类
 */

public class DatabaseUtils {

    /**
     * 添加策略
     *
     * @param databaseHelper
     * @param policy         策略名称
     */
    public static void insertPolicy(DatabaseHelper databaseHelper, String policy) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colDeptName, policy);
        db.insert(deptTable, colDeptID, cv);

    }

    /**
     * 添加app信息
     *
     * @param databaseHelper
     * @param appInfos       app列表
     * @param policyInt      对应策略ID
     */
    public static void insertPkgs(DatabaseHelper databaseHelper, List<AppInfo> appInfos, int policyInt) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < appInfos.size(); i++) {
            cv.put(colName, appInfos.get(i).getPkgName());
            cv.put(colDept, policyInt);
            db.insert(employeeTable, colID, cv);
        }

    }

    /**
     * 删除策略组中的app信息
     *
     * @param databaseHelper
     * @param appInfos
     */
    public static void deletePkgs(DatabaseHelper databaseHelper, List<AppInfo> appInfos) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        for (int i = 0; i < appInfos.size(); i++) {
            db.delete(employeeTable, colName + "=?", new String[]{appInfos.get(i).getPkgName()});
        }
    }

    public static void deletePkg(DatabaseHelper databaseHelper, String pkg) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(employeeTable, colName + "=?", new String[]{pkg});
    }

    /**
     * 删除策略（删除策略前要确保里面没有app，否则会造成外键约束错误）
     *
     * @param databaseHelper
     * @param poName         策略名称
     */
    public static void deletePolicy(DatabaseHelper databaseHelper, String poName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(deptTable, colDeptName + "=?", new String[]{String.valueOf(poName)});
    }

    /**
     * 获取所有策略
     *
     * @param databaseHelper
     * @return 策略游标
     */
    public static Cursor getPolicy(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT " + colDeptID + " as _id, " + colDeptName + " from " + deptTable, new String[]{});

        return cur;
    }

    /**
     * 获取所有app信息
     *
     * @param databaseHelper
     * @param policy         app对应的策略
     * @return app游标
     */
    public static Cursor getPkg(DatabaseHelper databaseHelper, String policy) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = new String[]{"_id", colName, colAge, colDeptName};
        Cursor c = db.query(viewEmps, columns, colDeptName + "=?", new String[]{policy}, null, null, null);

        return c;
    }

    public static int GetPolicyID(DatabaseHelper databaseHelper, String policy) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.query(deptTable, new String[]{colDeptID + " as _id", colDeptName},
                colDeptName + "=?", new String[]{policy}, null, null, null);
        //Cursor c=db.rawQuery("SELECT "+colDeptID+" as _id FROM "+deptTable+"
        //WHERE "+colDeptName+"=?", new String []{Dept});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("_id"));
    }

}
