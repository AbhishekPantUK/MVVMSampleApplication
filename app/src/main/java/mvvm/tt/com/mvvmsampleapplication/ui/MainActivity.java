package mvvm.tt.com.mvvmsampleapplication.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import mvvm.tt.com.mvvmsampleapplication.R;
import mvvm.tt.com.mvvmsampleapplication.db.WordEntity;
import mvvm.tt.com.mvvmsampleapplication.ui.WordListAdapter;
import mvvm.tt.com.mvvmsampleapplication.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel.getAllWords().observe(this, adapter::setWords);


        findViewById(R.id.fabAddWord).setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            WordEntity word = new WordEntity(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insertWord(word);
        }else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
