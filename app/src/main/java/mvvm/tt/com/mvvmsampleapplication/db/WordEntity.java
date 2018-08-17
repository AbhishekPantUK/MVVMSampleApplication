package mvvm.tt.com.mvvmsampleapplication.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class WordEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;


    public WordEntity(@NonNull String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
