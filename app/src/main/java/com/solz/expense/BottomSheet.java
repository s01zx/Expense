package com.solz.expense;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.solz.expense.data.AppDatabase;
import com.solz.expense.data.AppExecutors;
import com.solz.expense.data.Transaction;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener mListner;

    String dateFromPicker;
    DatePickerDialog datePickerDialog;

    Transaction trans;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_transaction,container,false);


        Button add = v.findViewById(R.id.add);
        final EditText description = v.findViewById(R.id.desc);
        final EditText amount = v.findViewById(R.id.amt);
        final TextView noSelectedDate = v.findViewById(R.id.nSelectedD);
        TextView selectedDate = v.findViewById(R.id.SelectedD);
        final AppDatabase mDb = AppDatabase.getInstance(getContext());




        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day =  c.get(Calendar.DAY_OF_MONTH);



                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //datePickerDialog.getDatePicker().setMinDate(202);
                        dateFromPicker = i2 + "/" + (i1 + 1) + "/" + i;
                        noSelectedDate.setText(dateFromPicker);
                    }

                },year,month,day);
                datePickerDialog.show();




            }
        });

        Bundle bundle = getArguments();

        if (bundle != null){
            final String des = bundle.getString("des");
            String amt = bundle.getString("amt");
            final int bID = bundle.getInt("id");

            description.setText(des);
            amount.setText(amt);
            add.setText("UPDATE");





            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String desc = description.getText().toString();
                    String number = amount.getText().toString();
                    //double num = Double.parseDouble(number);



                    String date = dateFromPicker;

                    if (desc.isEmpty()){
                        Toast.makeText(getContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (number.isEmpty()){
                        Toast.makeText(getContext(), "Enter amount", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    // using dialog
                    if (date == null){
//                        Toast.makeText(getContext(), "Select Date", Toast.LENGTH_SHORT).show();
//                        return;
                        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .create();
                        dialog.setTitle("Error");
                        dialog.setMessage("Select A date");
                        dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return;
                    }


                    trans = new Transaction(desc,Double.parseDouble(number),date);
                    trans.setId(bID);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.taskDao().insertTask(trans);
                            dismiss();
                        }
                    });

                    mListner.onButtonClickeed();
                }
            });

        }else{

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String desc = description.getText().toString();
                    String number = amount.getText().toString();
                    //double num = Double.parseDouble(number);
                    String date = dateFromPicker;



                    if (desc.isEmpty()){
                        Toast.makeText(getContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (number.isEmpty()){
                        Toast.makeText(getContext(), "Enter amount", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (date == null){
                        Toast.makeText(getContext(), "Select Date", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    trans = new Transaction(desc,Double.parseDouble(number),date);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.taskDao().insertTask(trans);
                            dismiss();

                        }
                    });

                    mListner.onButtonClickeed();
                }
            });
        }

        return v;
    }

    public interface BottomSheetListener{
        void onButtonClickeed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListner = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");

        }
    }
}
