package com.android.tablaperiodica.tablaperiodicara;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Elementos extends ListActivity {
    private static final String DB_NAME = "TablaPeriodica.sqlite";
    //������� ��������� �������� ������� ���� ����� �� �����������
    private static final String TABLE_NAME = "ElementoQuimico";
    private static final String FRIEND_ID = "_NoAtomico";
    private static final String FRIEND_NAME = "Elemento";
    private static final String FRIEND_SIMBOL = "SimboloAtomico";
    private static final String FRIEND_PESO = "PesoAtomico";

    private SQLiteDatabase database;
    private ListView listView;
    private ArrayList<String> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos);

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        //���, ���� �������!
        fillFreinds();
        setUpList();
    }

    private void setUpList() {
        //��������� ����������� ������� � layout �������� ��� ���������
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, friends));
        listView = getListView();

        //������� ���� ����, ��� ����
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillFreinds() {
        friends = new ArrayList<String>();
        Cursor friendCursor = database.query(TABLE_NAME,
                new String[]
                        {FRIEND_ID, FRIEND_NAME, FRIEND_SIMBOL, FRIEND_PESO},
                null, null, null, null
                , FRIEND_ID);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                String name = friendCursor.getString(1);
                String num = friendCursor.getString(0);
                String simbol = friendCursor.getString(2);
                String peso = friendCursor.getString(3);

                friends.add(num+" "+name + " " + simbol+ " " + peso);
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }
}