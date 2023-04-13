package com.example.myapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

//数据仓库
public class WordRepository {
    private final LiveData<List<Word>> liveData;
    private final WordDao wordDao;

    public WordRepository(Context context) {
        WordDataBase wordDataBase = WordDataBase.getInstance(context);
        wordDao = wordDataBase.getWordDao();
        liveData = wordDao.getAllWordLive();

    }

    public LiveData<List<Word>> getLiveData() {
        return liveData;
    }

    public LiveData<List<Word>> findWordData(String str) {
        return wordDao.getLikeWord(str);
    }

    public void inset(Word... words) {
        new InsetAsyncTack(wordDao).execute(words);
    }

    public void deleteAll() {
        new ClearAsyncTack(wordDao).execute();
    }

    public void deleteWord(Word... word) {
        new DeleteAsyncTack(wordDao).execute(word);
    }

    public void updateWord(Word... word) {
        new UpdateAsyncTack(wordDao).execute(word);
    }


    static class SelectLick extends AsyncTask<String, Void, Void> {
        private final WordDao wordDao;

        public SelectLick(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.e("TAG", "doInBackground: sart-->"+strings[0]);
           wordDao.getLikeWord(strings[0]);
//           wordDao.getAllWord();

            return null;
        }

    }

    //线程优化
    static class InsetAsyncTack extends AsyncTask<Word, Void, Void> {
        private final WordDao wordDao;

        public InsetAsyncTack(WordDao wordDao) {
            this.wordDao = wordDao;

        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsyncTack extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public UpdateAsyncTack(WordDao wordDao) {
            this.wordDao = wordDao;

        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsyncTack extends AsyncTask<Word, Void, Void> {
        private final WordDao wordDao;

        public DeleteAsyncTack(WordDao wordDao) {
            this.wordDao = wordDao;

        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class ClearAsyncTack extends AsyncTask<Void, Void, Void> {
        private final WordDao wordDao;

        public ClearAsyncTack(WordDao wordDao) {
            this.wordDao = wordDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWord();
            return null;
        }
    }
}
