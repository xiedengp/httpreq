package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.adapter.WordAdapter;
import com.example.myapp.database.Word;
import com.example.myapp.database.WordViewMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DaoActivity extends AppCompatActivity {


    LiveData<List<Word>> liveData;
    WordViewMode wordViewMode;
    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private final List<Word> listWord = new ArrayList<>();
    private View view;
    private boolean isDelete = false;
    private SearchView searchView;
    private static final String TAG = "DaoActivity";
    LiveData<List<Word>> findWord;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityDaoBinding binding = ActivityDaoBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_dao);
        Button inset = findViewById(R.id.inset);
        Button clear = findViewById(R.id.clear);
        view = findViewById(R.id.layout_all);
        searchView = findViewById(R.id.search_view);
        adapter = new WordAdapter();
        adapter.setContext(this);
        adapter.setListener(new WordAdapter.ItemListener() {
            @Override
            public void selectWord(Word word) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://dict.youdao.com/m/search?keyfrom=dict.mindex&vendor=&q=" + word.word));
                startActivity(intent);
            }

            @Override
            public void editWord(Word word) {
                Intent intent = new Intent(DaoActivity.this, InsetWordActivity.class);
                String wordStr = new Gson().toJson(word);
                intent.putExtra("word", wordStr);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //分割线
        wordViewMode = new ViewModelProvider(this).get(WordViewMode.class);
        liveData = wordViewMode.getLiveData();
        findWord = wordViewMode.getLiveData();
        wordViewMode.getLiveData().observe(this, words -> {
            Log.e(TAG, "onCreate: " + words.size());
            listWord.clear();
            listWord.addAll(words);
            //数据改变的时候调用 更新优化
            if (liveData.getValue() != null && liveData.getValue().size() == words.size()) {
                if (!isDelete) {
                    recyclerView.smoothScrollBy(0, -200);
                }
                isDelete = false;
                adapter.submitList(words);
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击键盘搜索获取文字
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onQueryTextChange: " + newText);
                //实时获取文字
                findWord.removeObservers(DaoActivity.this); //先移除原有观察
                findWord = wordViewMode.findWordData(newText);
                findWord.observe(DaoActivity.this, words -> adapter.submitList(words));
                //false true  false = 事件是否传送 接着往下面处理 true 结束事件分发
                return true;
            }

        });


        //recyclerView 滑动删除
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Word word = listWord.get(viewHolder.getAdapterPosition()); //todo 可能是null list<T> 中间保存 获取值操作
                if (word != null) {
                    isDelete = true;
                    wordViewMode.deleteWord(word);

                }

                Snackbar.make(view, "是否撤销删除？", 2000).setAction("撤销", view -> wordViewMode.inset(word)).show();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);


        inset.setOnClickListener(v -> startActivity(new Intent(this, InsetWordActivity.class)));
        clear.setOnClickListener(v -> new AlertDialog.Builder(this).setMessage("是否删除全部数据").
                setNegativeButton("确定", (DialogInterface dialogInterface, int i) -> wordViewMode.deleteAll()).
                setPositiveButton("关闭", null).
                create().show());

    }


    private void http() {
        handler.postDelayed(runnable,2000); //延迟执行

        // 不能在主线程里面执行
        try {
            URL url = new URL(""); //设置地址

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //开启链接

            connection.setConnectTimeout(10000); //设置一些参数
            connection.setRequestMethod("GET");
            connection.setRequestProperty("", "");

            connection.connect();  //链接

            connection.getResponseCode(); //获取返回状态码

            InputStream inputStream = connection.getInputStream(); //获取返回流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = bufferedReader.readLine();  //读取流
            Log.e(TAG, "http: " + str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadBigImage(@IdRes int imageId) {
        //大图加载处理
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true; //不加载的内存中
//        BitmapFactory.decodeFile(imageSdUri, options);
        BitmapFactory.decodeResource(getResources(),imageId);

        final int height = options.outHeight; //获取图片的宽高
        final int width = options.outWidth;

        options.inSampleSize = 1; //默认设置1 不压缩

        int w = 320; //todo 这里实际获取控件的宽高 可以
        int h = 480;
        h = w * height / width;//计算出宽高等比率

        int a = options.outWidth / w;
        int b = options.outHeight / h;

        options.inSampleSize = Math.max(a, b); //设置压缩比例

        options.inJustDecodeBounds = false;  //加载的内存中
//        Bitmap bitmap = BitmapFactory.decodeFile(imageSdUri, options);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageId);
        return bitmap;
    }
}
