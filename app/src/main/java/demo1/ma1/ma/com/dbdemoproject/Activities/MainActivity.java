package demo1.ma1.ma.com.dbdemoproject.Activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import demo1.ma1.ma.com.dbdemoproject.Constants.Constants;
import demo1.ma1.ma.com.dbdemoproject.Constants.SharePreferenceManager;
import demo1.ma1.ma.com.dbdemoproject.Adapters.CustomListViewAdapter;
import demo1.ma1.ma.com.dbdemoproject.DB.DatabaseHandler;
import demo1.ma1.ma.com.dbdemoproject.R;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private ListView mListView;
    private EditText mEditText;
    private DatabaseHandler db;
    private CustomListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        insertIntoDB();
        getDataFromDB();
    }

    private void init() {
        mEditText = (EditText) findViewById(R.id.et_search);
        mListView = (ListView) findViewById(R.id.list_view);
        db = new DatabaseHandler(this);
        mEditText.addTextChangedListener(this);
    }

    private void insertIntoDB() {
        if (!SharePreferenceManager.getPreferences(this, Constants.IS_DICTIONARY_DATA_INSERT, false)) {
            db.addWords();
            SharePreferenceManager.writeToPreferences(this, Constants.IS_DICTIONARY_DATA_INSERT, true);
        }
    }

    private void getDataFromDB() {
        Cursor cursor = db.getWords("");
        mAdapter = new CustomListViewAdapter(this, cursor);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mAdapter.changeCursor(db.getWords(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}