package mvvm.tt.com.mvvmsampleapplication.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {WordEntity.class}, version = 1 )
public abstract class WordRoomDb  extends RoomDatabase{

    public abstract WordDao wordDao();

    private static WordRoomDb INSTANCE;

    public static WordRoomDb getDatabase(Context context) {
        if(INSTANCE == null) {
            synchronized (WordRoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDb.class,
                            "word_database")
                            .addCallback(sRoomDataCallback)
//                            .fallbackToDestructiveMigration()  // version increased, fallback to destructive migration enabled — database is cleared
//                            .addMigrations(MIGRATION_1_2) // version increased, migration provided — data is kept
//                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Migration with simple schema changes
                            .build();
                }
            }
        }

        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDataCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    // migration with no schema changed
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    // migration with simple schema change
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word_table "
                    + " ADD COLUMN last_update INTEGER");
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDb db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            WordEntity wordOne = new WordEntity("Abhishek");
            mDao.insertWord(wordOne);
            WordEntity wordTwo = new WordEntity("Hello");
            mDao.insertWord(wordTwo);
            return null;
        }
    }
}
