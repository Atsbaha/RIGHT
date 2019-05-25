package com.example.saintmary.right.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
// import com.readystatesoftware.sqliteasset.SQLiteHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import com.example.saintmary.right.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//        import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;//ezuy ab dependecies zelo eyu sync ms gebernayo kserh eyu

import java.util.ArrayList;
import java.util.List;

//        import static com.example.saintmary.right.Database.DatabaseHelper.*;

public class Database extends SQLiteAssetHelper {//nay ezuy ab Asset enserho database eyu

    //A helper class to manage database creation and version management.
    //the rightDB is found in the asset folder

    private static final String DB_NAME="rightDBB.db";

    private static final int DB_VER=1;
//    public Database(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, storageDirectory, factory, version);
//    }

    //    private static final String DB_NAME = "rightDB.db";//please rename this to your own which is found in asset folder
//    private static final int DB_VER = 1;//this is database version
  /*  public static final class rightEntry implements BaseColumns
    {
    public static String TABLE_NAME="OrderDetail";
    public static String COLUMN_ProductId="ProductId";
    public static String COLUMN_ProductName="ProductName";
    public static String COLUMN_Quantity="Quantity";
    public static String COLUMN_Price="Price";
    public static String COLUMN_Discount="Discount";
    }*/


    public Database(Context context){
        //this is context,name,storageDirectory,factory,version
        super(context,DB_NAME,null,DB_VER);//this is because of the gradle sync please sync it
    }

    public List<Order> getCarts(){//<Order> means the parameters are in Order class

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductId", "ProductName", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";//this table is where i created using DB browser for SQLite found in Assets folder

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){//(Order order) means the the below parameters are in the class Order

      SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId, ProductName, Quantity, Price, Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuanlity(),
                order.getPrice(),
                order.getDiscount());

       db.execSQL(query);

     /* //  public boolean insertData(String ProductId,String surname,String marks) {
            //SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,order.getProductId());
            contentValues.put(COL_3, order.getProductName());
            contentValues.put(COL_4,order.getQuanlity());
            contentValues.put(COL_5,order.getPrice());
            contentValues.put(COL_6,order.getDiscount());
            long result = db.insert(TABLE_NAME,null ,contentValues);
           /* if(result == -1)
                return false;
            else
*/

    }

    public void removeFromCart(String order){

        SQLiteDatabase db = getReadableDatabase();
//        SQLiteDatabase db = getWritableDatabase();

        String query = String.format("DELETE FROM OrderDetail WHERE ProductId='"+order+"'");
        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    //favourites
    public void addToFavourites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO Favorites(FoodId) VALUES('%s');",foodId);
        db.execSQL(query);

    }

    public void removeFromFavourites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM Favorites WHERE FoodId='%s';",foodId);
        db.execSQL(query);

    }

    public boolean isFavourite(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
       String query=String.format("SELECT * FROM Favorites WHERE FoodId='%s';",foodId);
       Cursor cursor=db.rawQuery(query,null);
       if(cursor.getCount()<=0)
       {
           cursor.close();
           return false;
       }
       cursor.close();
       return true;

    }

    public int getCountCart() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do{
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return count;
    };


}

