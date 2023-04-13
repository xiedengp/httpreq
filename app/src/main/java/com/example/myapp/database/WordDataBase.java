package com.example.myapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 3, exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    //单列优化
    private static WordDataBase INSTANCE;

    static synchronized WordDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDataBase.class, "word_database")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //alter table 表名 add 字段名 类型(长度);
            database.execSQL("ALTER TABLE WORD ADD COLUMN test_1 INTEGER NOT NULL DEFAULT 1");

        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //删除字段  新建表->写入数据->删除之前的表->更改表名为之前的
            database.execSQL("CREATE TABLE word_tepm (id INTEGER PRIMARY KEY  NOT NULL,english_word TEXT,chinese_meaning TEXT )");
            database.execSQL("INSERT INTO word_tepm(id,english_word,chinese_meaning)  SELECT id,english_word,chinese_meaning FROM WORD");
            database.execSQL("DROP TABLE WORD");
            database.execSQL("ALTER TABLE word_tepm RENAME TO WORD");

        }
    };
}
