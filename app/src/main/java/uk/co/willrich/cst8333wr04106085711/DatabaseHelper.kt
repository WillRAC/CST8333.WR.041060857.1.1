package uk.co.willrich.cst8333wr04106085711

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase"
        private const val TABLE_USERS = "userMain"
        private const val TABLE_USER_DATA = "userData"

        private const val KEY_ID = "id"
        private const val KEY_USERNAME = "username"
        private const val KEY_FIRSTNAME = "firstname"
        private const val KEY_LASTNAME = "lastname"
        private const val KEY_GENDER = "gender"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_HEIGHT = "height"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"

        private const val KEY_USER_ID = "user_id"
        private const val KEY_DATE = "date"
        private const val KEY_CALORIE = "calorie"
        private const val KEY_MINUTES = "minutes"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = ("CREATE TABLE $TABLE_USERS ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_USERNAME TEXT,"
                + "$KEY_FIRSTNAME TEXT,"
                + "$KEY_LASTNAME TEXT,"
                + "$KEY_GENDER TEXT,"
                + "$KEY_WEIGHT INTEGER,"
                + "$KEY_HEIGHT INTEGER,"
                + "$KEY_EMAIL TEXT,"
                + "$KEY_PASSWORD TEXT"  // Removed the trailing comma
                + ")")
        db.execSQL(createUserTable)

        val createUserDataTable = ("CREATE TABLE $TABLE_USER_DATA ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_USER_ID INTEGER,"
                + "$KEY_DATE INTEGER,"
                + "$KEY_CALORIE INTEGER,"
                + "$KEY_MINUTES INTEGER"
                + ")")
        db.execSQL(createUserDataTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_DATA")
        onCreate(db)
    }

    fun addUser(user: UserMain): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_USERNAME, user.username)
        values.put(KEY_FIRSTNAME, user.firstname)
        values.put(KEY_LASTNAME, user.lastname)
        values.put(KEY_GENDER, user.gender)
        values.put(KEY_WEIGHT, user.weight)
        values.put(KEY_HEIGHT, user.height)
        values.put(KEY_EMAIL, user.email)
        values.put(KEY_PASSWORD, user.password)
        return db.insert(TABLE_USERS, null, values)
    }

    @SuppressLint("Range")
    fun getUser(username: String): UserMain? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$KEY_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        return try {
            if (cursor.moveToFirst()) {
                UserMain(
                    id = cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    username = cursor.getString(cursor.getColumnIndex(KEY_USERNAME)),
                    firstname = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)),
                    lastname = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)),
                    gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER)),
                    weight = cursor.getDouble(cursor.getColumnIndex(KEY_WEIGHT)),
                    height = cursor.getDouble(cursor.getColumnIndex(KEY_HEIGHT)),
                    email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                )
            } else {
                null
            }
        } finally {
            cursor.close()
        }
    }

    fun addUserData(userData: UserData): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_USER_ID, userData.userId)
        values.put(KEY_DATE, userData.date)
        values.put(KEY_CALORIE, userData.calorie)
        values.put(KEY_MINUTES, userData.minutes)
        return db.insert(TABLE_USER_DATA, null, values)
    }

    @SuppressLint("Range")
    fun getUserData(userId: Long, date: Long): UserData? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER_DATA WHERE $KEY_USER_ID = $userId AND $KEY_DATE = $date"
        val cursor = db.rawQuery(query, null)

        return try {
            if (cursor.moveToFirst()) {
                UserData(
                    id = cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    userId = cursor.getLong(cursor.getColumnIndex(KEY_USER_ID)),
                    date = cursor.getLong(cursor.getColumnIndex(KEY_DATE)),
                    calorie = cursor.getInt(cursor.getColumnIndex(KEY_CALORIE)),
                    minutes = cursor.getInt(cursor.getColumnIndex(KEY_MINUTES))
                )
            } else {
                null
            }
        } finally {
            cursor.close()
        }
    }
}
