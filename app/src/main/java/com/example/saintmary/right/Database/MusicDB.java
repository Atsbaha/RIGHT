package com.example.saintmary.right.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.saintmary.right.Model.MusicOrder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
// import com.readystatesoftware.sqliteasset.SQLiteHelper;

//        import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;//ezuy ab dependecies zelo eyu sync ms gebernayo kserh eyu


//        import static com.example.saintmary.right.Database.DatabaseHelper.*;


class MusicDBextends extends SQLiteAssetHelper{


    //A helper class to manage database creation and version management.
    //the rightDB is found in the asset folder

    private static final String DB_NAME="musicDB.db";

    private static final int DB_VER=1;


    public MusicDBextends(Context context){
        //this is context,name,storageDirectory,factory,version
        super(context,DB_NAME,null,DB_VER);//this is because of the gradle sync please sync it
    }

    public List<MusicOrder> getCarts(){//<Order> means the parameters are in Order class

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"MusicName"};
        String sqlTable = "MusicOrderDetail";//this table is where i created using DB browser for SQLite found in Assets folder

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<MusicOrder> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new MusicOrder(c.getString(c.getColumnIndex("MusicName"))

                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCartMusic(MusicOrder orderMusic){//(Order order) means the the below parameters are in the class Order

//      SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO MusicOrderDetail(MusicName) VALUES('%s');",
                orderMusic.getMusicName());

//       db.execSQL(query);



    }

    public void removeFromCartMusic(String orderMusic){

        SQLiteDatabase db = getReadableDatabase();
//        SQLiteDatabase db = getWritableDatabase();

        String query = String.format("DELETE FROM MusicOrderDetail WHERE MusicName='"+orderMusic+"'");
//        db.execSQL(query);
    }

    public void cleanCartMusic(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM MusicOrderDetail");
//        db.execSQL(query);
    }

}

