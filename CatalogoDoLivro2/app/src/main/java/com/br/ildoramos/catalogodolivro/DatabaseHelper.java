package com.br.ildoramos.catalogodolivro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="CATALOGO";
    private static final int DATABASE_VERSION=3;
    private final String CREATE_TABLE_CATALOGO=
            "CREATE TABLE catalogo(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT," +
                    "autor TEXT," +
                    "ano INTEGER)";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATALOGO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS catalogo");
    onCreate(db);

    }

    public Long inserir(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        Long id = db.insert("catalogo",null, cv);
        return id;
    }

    public List<ContentValues> pesquisarPorTitulo(String titulo){
        String sql = "SELECT * FROM catalogo WHERE titulo LIKE?";
        String where[] = new String[]{"%"+titulo+""};
        return pesquisar(sql, where);
    }

    public List<ContentValues> pesquisarPorAno(int ano){
        String sql = "SELECT *FROM catalogo WHERE ano=?";
        String where[] = new String[]{String.valueOf(ano)};

        return pesquisar(sql, where);
    }

    public  List<ContentValues> pesquisarTodos(){
        String sql= "SELECT * FROM catalogo ORDER BY id";
        String where[] =null;
        return pesquisar(sql, where);
    }

    private List<ContentValues> pesquisar(String sql, String[] where) {
        List<ContentValues> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,where);

        if(cursor.moveToFirst()){
            do{
                ContentValues cv = new ContentValues();
                cv.put("id",cursor.getInt(cursor.getColumnIndex("id")));
                cv.put("titulo",cursor.getInt(cursor.getColumnIndex("titulo")));
                cv.put("autor",cursor.getInt(cursor.getColumnIndex("autor")));
                cv.put("ano",cursor.getInt(cursor.getColumnIndex("ano")));

                lista.add(cv);

            }while (cursor.moveToNext());
        }
        return lista;
    }

}
