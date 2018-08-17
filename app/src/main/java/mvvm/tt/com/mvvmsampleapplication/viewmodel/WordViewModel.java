package mvvm.tt.com.mvvmsampleapplication.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import java.util.List;

import mvvm.tt.com.mvvmsampleapplication.db.WordEntity;
import mvvm.tt.com.mvvmsampleapplication.repository.WordRepository;

public class WordViewModel extends AndroidViewModel {

    private LiveData<List<WordEntity>> mListAllWords;
    private WordRepository mWordRepository;


    public WordViewModel(Application context) {
        super(context);
        mWordRepository = new WordRepository(context);
        mListAllWords  = mWordRepository.getAllWords();
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mListAllWords;
    }

    public void insertWord(WordEntity word){
        mWordRepository.insert(word);
    }
}
