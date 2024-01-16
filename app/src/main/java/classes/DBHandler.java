package classes;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
    public  static  SQLiteDatabase checkingAndUpdatingTheDatabase(SQLiteDatabase appDataBase){
        appDataBase.execSQL("CREATE TABLE IF NOT EXISTS Projects(ID INT PRIMARY KEY, Name VARCHAR(70), Tasks BOOL, Description BOOL, Ideas BOOL, Script BOOL)");
        appDataBase.execSQL("CREATE TABLE IF NOT EXISTS Tasks(ID INT PRIMARY KEY, PID INT, Data VARCHAR(250), isCompleted BOOL, FOREIGN KEY (PID) REFERENCES Projects(ID))");
        appDataBase.execSQL("CREATE TABLE IF NOT EXISTS Descriptions(ID INT PRIMARY KEY, PID INT, Data VARCHAR(2000), FOREIGN KEY (PID) REFERENCES Projects(ID))");
        appDataBase.execSQL("CREATE TABLE IF NOT EXISTS Ideas(ID INT PRIMARY KEY, PID INT, Data VARCHAR(250), FOREIGN KEY (PID) REFERENCES Projects(ID))");
        appDataBase.execSQL("CREATE TABLE IF NOT EXISTS Scripts(ID INT PRIMARY KEY, PID INT, Data VARCHAR(10000), FOREIGN KEY (PID) REFERENCES Projects(ID))");
        return appDataBase;
    }
}
