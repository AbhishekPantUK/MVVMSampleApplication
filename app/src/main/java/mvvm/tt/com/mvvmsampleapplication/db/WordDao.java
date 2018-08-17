package mvvm.tt.com.mvvmsampleapplication.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insertWord(WordEntity word);

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<WordEntity>>   getAllWord();

    @Query("DELETE FROM word_table")
    void deleteAll();
}
