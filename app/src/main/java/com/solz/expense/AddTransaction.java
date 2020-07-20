//package com.solz.expense;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.solz.expense.R;
//import com.solz.expense.data.AppDatabase;
//import com.solz.expense.data.AppExecutors;
//import com.solz.expense.data.Transaction;
//
//import java.util.Calendar;
//import java.util.Date;
//
//public class AddTransaction extends AppCompatActivity {
//
//    EditText description;
//    EditText amount;
//    TextView noSelectedDate;
//    TextView selectedDate;
//    Button add;
//
//    String dateFromPicker;
//    DatePickerDialog datePickerDialog;
//
//    private AppDatabase mDb;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_transaction);
//
//        findView();
//
//        mDb = AppDatabase.getInstance(getApplicationContext());
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String desc = description.getText().toString();
//                String number = amount.getText().toString();
//                double num = Double.parseDouble(number);
//                String date = dateFromPicker;
//
//                final Transaction trans = new Transaction(desc,num,date);
//
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        mDb.taskDao().insertTask(trans);
//                        finish();
//
//                    }
//                });
//
//            }
//        });
//
//        selectedDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                DatePicker datepic = new DatePicker(AddTransaction.this);
////                String day = "" + datepic.getDayOfMonth();
////                String month = "" + datepic.getMonth();
////                String year = "" + datepic.getYear();
////
////
////                dateFromPicker = day + "/" + month + "/" + year;
////                noSelectedDate.setText(dateFromPicker);
//                final Calendar c = Calendar.getInstance();
//                int day =  c.get(Calendar.DAY_OF_MONTH);
//                int month = c.get(Calendar.MONTH);
//                int year = c.get(Calendar.YEAR);
//
//                datePickerDialog = new DatePickerDialog(AddTransaction.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        datePicker.setMinDate(3000);
//                        dateFromPicker = i2 + "/" + (i1 + 1) + "/" + i;
//                        noSelectedDate.setText(dateFromPicker);
//                    }
//
//                },day,month,year);
//                datePickerDialog.show();
//
//
//
//
//            }
//        });
//
//        findView();
//    }
//
//    private void findView() {
//        description = findViewById(R.id.desc);
//        amount = findViewById(R.id.amt);
//        noSelectedDate = findViewById(R.id.nSelectedD);
//        selectedDate = findViewById(R.id.SelectedD);
//        add = findViewById(R.id.add);
//    }
//}
