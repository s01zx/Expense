package com.solz.expense;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.solz.expense.data.AppDatabase;
import com.solz.expense.data.AppExecutors;
import com.solz.expense.data.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements BottomSheet.BottomSheetListener {


    TransactionAdapter adapter;
    private AppDatabase mDb;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TransactionAdapter.RecyclerViewClickListener listener = new TransactionAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, List<Transaction> transEntries) {

                Transaction trans;
                List<Transaction> mTransEntries = transEntries;

                trans = mTransEntries.get(position);
                //Toast.makeText(getApplicationContext(), "Position" + position, Toast.LENGTH_SHORT).show();
                BottomSheet bottomSheet = new BottomSheet();

                Bundle bundle = new Bundle();
                String des = trans.getTitle();
                String amou = String.valueOf(trans.getAmount());
                int id = trans.getId();


                bundle.putString("des", des);
                bundle.putString("amt", amou);
                bundle.putInt("id", id);

                bottomSheet.setArguments(bundle);

                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");

            }
        };


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this, listener);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        mDb = AppDatabase.getInstance(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent intent = new Intent(MainActivity.this, AddTransaction.class);
//                startActivity(intent);
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");
                //showBottomSheetDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Transaction> trans = mDb.taskDao().loadAllTasks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setTasks(trans);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClickeed() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Transaction> trans = mDb.taskDao().loadAllTasks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setTasks(trans);
                    }
                });
            }
        });


        /* run a task on a new thread */
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                final List<Transaction> trans = mDb.taskDao().loadAllTasks();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.setTasks(trans);
//                    }
//                });
//
//            }
//        });

    }
}
