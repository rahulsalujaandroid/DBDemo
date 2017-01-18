package demo1.ma1.ma.com.dbdemoproject.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import demo1.ma1.ma.com.dbdemoproject.DB.DatabaseHandler;
import demo1.ma1.ma.com.dbdemoproject.R;

public class CustomListViewAdapter extends CursorAdapter implements View.OnClickListener {
    private Context mContext;

    public CustomListViewAdapter(Context context, Cursor c) {
        super(context, c, false);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvWord = (TextView) view.findViewById(R.id.tv_list_item);
        tvWord.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        tvWord.setTag(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        tvWord.setOnClickListener(this);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        return super.runQueryOnBackgroundThread(constraint);
    }

    @Override
    public void onClick(View v) {
        String str = (String) v.getTag();
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
