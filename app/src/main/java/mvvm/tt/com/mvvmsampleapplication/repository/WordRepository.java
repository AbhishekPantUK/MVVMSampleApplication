package mvvm.tt.com.mvvmsampleapplication.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mvvm.tt.com.mvvmsampleapplication.db.WordDao;
import mvvm.tt.com.mvvmsampleapplication.db.WordEntity;
import mvvm.tt.com.mvvmsampleapplication.db.WordRoomDb;

public class WordRepository {

    private WordDao wordDao;

    private LiveData<List<WordEntity>> mListWords;

    public WordRepository(Context context) {
        WordRoomDb wordRoomDb = WordRoomDb.getDatabase(context);
        wordDao = wordRoomDb.wordDao();
        mListWords = wordDao.getAllWord();
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mListWords;
    }

    public void insert(WordEntity word){
        new InsertAsyncTak(wordDao).execute(word);
    }

    public static class InsertAsyncTak extends AsyncTask<WordEntity, Void , Void>{
        private WordDao wordDao;

        public InsertAsyncTak(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(final WordEntity... params) {
            wordDao.insertWord(params[0]);
            return null;
        }
    }
}
