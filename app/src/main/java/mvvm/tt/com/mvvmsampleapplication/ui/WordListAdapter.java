package mvvm.tt.com.mvvmsampleapplication.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mvvm.tt.com.mvvmsampleapplication.R;
import mvvm.tt.com.mvvmsampleapplication.db.WordEntity;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<WordEntity> mWords; // Cached copy of words

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            WordEntity current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<WordEntity> words){
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView wordItemView;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
