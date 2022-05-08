package pgl.proyecto_pgl_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "appTareas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "correo TEXT NOT NULL, " +
                "contrasena TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Tareas(" +
                "idTarea INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario INTEGER NOT NULL, " +
                "titulo TEXT, " +
                "descripcion TEXT, " +
                "fecha TEXT, " +
                "fav INTEGER," +
                "FOREIGN KEY (usuario) REFERENCES Usuarios(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Tareas");
    }

    // C - CREATE Usuario
    public boolean addUsuario(String nom, String ap, String mail, String con) {
        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", nom);
        cv.put("apellido", ap);
        cv.put("correo", mail);
        cv.put("contrasena", con);

        long result = sql.insert("Usuarios", null, cv);
        return result != -1;
    }

    // R - READ Usuarios
    public Cursor getUsuarios() {
        SQLiteDatabase sql = this.getWritableDatabase();
        return sql.rawQuery("SELECT * FROM Usuarios", null);
    }

    // R - READ Usuario
    public Cursor getUsuarioID(String id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        return sql.rawQuery("SELECT * FROM Usuarios WHERE id = ?", new String[]{id});
    }

    // R - READ Usuario
    public Cursor getUsuario(String correo) {
        SQLiteDatabase sql = this.getWritableDatabase();
        return sql.rawQuery("SELECT * FROM Usuarios WHERE correo = ?", new String[]{correo});
    }

    // U - UPDATE Usuario
    public boolean upUsuario(int id, String nom, String ap, String mail, String con) {
        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", nom);
        cv.put("apellido", ap);
        cv.put("correo", mail);
        cv.put("contrasena", con);

        long result = sql.update("Usuarios",cv,"id = ?", new String[]{id+""});
        return result != -1;
    }

    // D - DELETE Usuario
    public boolean delUsuario(int id) {
        SQLiteDatabase sql = this.getWritableDatabase();

        long result = sql.delete("Usuarios", "id = ?", new String[]{id+""});
        return result != -1;
    }

    // ----------------------------------------------------------------------------------------- //

    // C - CREATE Tarea
    public boolean addTarea(int usu, String tit, String des, String fe, int fav) {
        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("usuario", usu);
        cv.put("titulo", tit);
        cv.put("descripcion", des);
        cv.put("fecha", fe);
        cv.put("fav", fav);

        long result = sql.insert("Tareas", null, cv);
        return result != -1;
    }

    // R - READ Tareas
    public Cursor getTareas(String id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        return sql.rawQuery("SELECT * FROM Tareas WHERE usuario = ?", new String[]{id});
    }

    // U - UPDATE Tarea
    public boolean upTarea(int idTarea, int usu, String tit, String des, String fe, int fav) {
        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("usuario", usu);
        cv.put("titulo", tit);
        cv.put("descripcion", des);
        cv.put("fecha", fe);
        cv.put("fav", fav);

        long result = sql.update("Tareas",cv,"idTarea = ?", new String[]{idTarea+""});
        return result != -1;
    }

    // D - DELETE Tarea
    public boolean delTarea(int idTarea) {
        SQLiteDatabase sql = this.getWritableDatabase();

        long result = sql.delete("Tareas", "idTarea = ?", new String[]{idTarea+""});
        return result != -1;
    }
}
