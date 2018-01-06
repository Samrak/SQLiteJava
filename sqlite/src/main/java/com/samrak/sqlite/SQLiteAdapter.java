package com.samrak.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by sametoztoprak on 30.12.2017.
 */

public class SQLiteAdapter<TT extends SQLiteOpenHelper> extends SQLiteUtilities<TT> {

    public SQLiteAdapter(TT dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public <T> ArrayList<T> select(T obj) {
        String query = getSql(obj);
        return fillFields(query, obj);
    }

    @Override
    public <T> ArrayList<T> select(T obj, String value) {
        String query = getSql(obj);
        ArrayList<String> columns = getAllColumnNames(getTableName(obj));

        if (!value.isEmpty()) {
            String conditions = "";
            for (int index = 0; index < columns.size(); index++) {
                String condition = columns.get(index) + " like '%" + value + "%' ";
                conditions = (index == 0) ? conditions + condition : conditions + " or " + condition;
            }
            query = query + " where " + conditions;
        }
        return fillFields(query, obj);
    }

    @Override
    public <T> ArrayList<T> select(T obj, String key, String value) {
        String query = getSql(obj);
        if (!key.isEmpty()) {
            query = query + " where " + key + "=" + "'" + value + "'";
        }

        return fillFields(query, obj);
    }

    private String getSql(Object obj) {
        String tableName = getTableName(obj);
        return "select * from " + tableName;
    }

    private <T> ArrayList<T> fillFields(String query, Object obj) {
        ArrayList<T> entityList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tableName = getTableName(obj);
        Class entityClass = obj.getClass();
        ArrayList<String> columns = getAllColumnNames(tableName);

        try {
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Object newObj = entityClass.newInstance();

                for (int index = 0; index < columns.size(); index++) {

                    Field field = getField(obj, columns.get(index));
                    field.setAccessible(true);
                    if (field.getType().equals(String.class)) {
                        field.set(newObj, cursor.getString(index));
                    } else if (field.getType().equals(byte[].class)) {
                        field.set(newObj, cursor.getBlob(index));
                    } else if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
                        field.set(newObj, cursor.getShort(index));
                    } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                        field.set(newObj, cursor.getInt(index));
                    } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                        field.set(newObj, cursor.getLong(index));
                    } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                        field.set(newObj, cursor.getDouble(index));
                    } else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
                        field.set(newObj, cursor.getFloat(index));
                    } else if (field.getType().equals(boolean.class)) {
                        field.set(newObj, Boolean.parseBoolean(cursor.getString(index)));
                    } else {
                        field.set(newObj, cursor.getString(index));
                    }
                }
                entityList.add((T) newObj);
                cursor.moveToNext();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    //insert entity
    @Override
    public <T> long insert(T obj, String idColumnName) {
        String tableName = getTableName(obj);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getContentValues(tableName, idColumnName, obj);
        return db.insert(tableName, null, values);
    }


    //update entity
    @Override
    public <T> long update(T obj, String idColumnName) {
        String tableName = getTableName(obj);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Field field = getField(obj, idColumnName);
        String idColumnValue = getValue(field, obj);
        ContentValues values = getContentValues(tableName, idColumnName, obj);
        return db.update(tableName, values, idColumnName + "=" + idColumnValue, null);
    }

    //delete entity
    @Override
    public <T> long delete(T obj, String idColumnName) {

        String tableName = getTableName(obj);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Field field = getField(obj, idColumnName);
        String idColumnValue = getValue(field, obj);
        return db.delete(tableName, idColumnName + "='" + idColumnValue + "'", null);
    }
}