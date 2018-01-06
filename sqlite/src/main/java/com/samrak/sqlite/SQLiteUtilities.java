package com.samrak.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by samet on 31-May-16.
 */
public abstract class SQLiteUtilities<TT extends SQLiteOpenHelper>  {

    protected final static String logLabel = "SQLiteAdapter";
    protected TT dbHelper;

    private String getElement(String member) {
        String elements[] = member.toString().split("\\.");
        return elements[elements.length - 1];
    }

    protected String getTableName(Object obj) {
        Class<? extends Object> Class = obj.getClass();
        String className = getElement(Class.getName()).toLowerCase();
        return className.replaceAll("entity", "");
    }

    protected ArrayList<Field> getDeclaredFields(Object obj) {
        Class<? extends Object> c = obj.getClass();
        ArrayList<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(c.getDeclaredFields()));
        if (c.getSuperclass().getDeclaredFields() != null)
            fields.addAll(Arrays.asList(c.getSuperclass().getDeclaredFields()));
        return fields;
    }

    protected Field getField(Object obj, String idColumnName) {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(idColumnName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
           // field = obj.getClass().getSuperclass().getDeclaredField(idColumnName);
            e.printStackTrace();
        } finally {
            //db.close();
        }
        return field;
    }

    protected String getValue(Field field, Object obj) {
        String result = null;
        try {
            result = (field.get(obj) == null) ? "" : field.get(obj).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected ArrayList<String> getAllColumnNames(String tableName) {
        int tableNameIndex = 1;
        ArrayList<String> columns = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "PRAGMA table_info(" + tableName + ")";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            columns.add(cursor.getString(tableNameIndex));
            cursor.moveToNext();
        }
        //db.close();
        cursor.close();
        return columns;
    }

    protected ArrayList<String> getColumnNames(String tableName, String idColumnName) {
        ArrayList<String> columnNames = getAllColumnNames(tableName);
        columnNames.remove(idColumnName);
        return columnNames;
    }

    protected ContentValues getContentValues(String tableName, String idColumnName, Object obj) {
        ContentValues values = new ContentValues();
        try {
            Class entityClass = obj.getClass();
            for (String column : getColumnNames(tableName, idColumnName)) {
                Field field = entityClass.getDeclaredField(column);
                field.setAccessible(true);

                if (field.getType().equals(String.class)) {
                    values.put(field.getName(), (String) field.get(obj));
                } else if (field.getType().equals(byte[].class)) {
                    values.put(field.getName(), (byte[]) field.get(obj));
                } else if (field.getType().equals(short.class)) {
                    values.put(field.getName(), (short) field.get(obj));
                } else if (field.getType().equals(int.class)) {
                    values.put(field.getName(), (int) field.get(obj));
                } else if (field.getType().equals(long.class)) {
                    values.put(field.getName(), (long) field.get(obj));
                } else if (field.getType().equals(double.class)) {
                    values.put(field.getName(), (double) field.get(obj));
                } else if (field.getType().equals(float.class)) {
                    values.put(field.getName(), (float) field.get(obj));
                } else if (field.getType().equals(boolean.class)) {
                    values.put(field.getName(), (boolean) field.get(obj));
                } else {
                    values.put(field.getName(), getValue(field, obj));
                }
            }
        } catch (IllegalAccessException e) {
            Log.d(logLabel, e.getMessage());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            // db.close();
        }
        return values;
    }


    public abstract <T> ArrayList<T> select(T obj);

    public abstract <T> ArrayList<T> select(T obj, String value); //select with value

    public abstract <T> ArrayList<T> select(T obj, String key, String value); //select with condition

    public abstract <T> long insert(T obj, String idColumnName);

    public abstract <T> long delete(T obj, String idColumnName);

    public abstract <T> long update(T obj, String idColumnName);
}