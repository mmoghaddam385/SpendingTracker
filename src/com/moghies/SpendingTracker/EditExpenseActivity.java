package com.moghies.SpendingTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.moghies.SpendingTracker.model.Expense;
import com.moghies.SpendingTracker.model.ExpenseDate;

/**
 * Created by mmogh on 5/22/2016.
 */
public class EditExpenseActivity extends Activity implements View.OnClickListener {

    public static final String EXPENSE_EXTRA = "EXPENSE";
    public static final String POSITION_EXTRA = "POSITION";

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_CANCEL = -1;
    public static final int RESULT_DELETE = -2;

    private EditText txtDescription;
    private Spinner spnCategory;
    private EditText txtPrice;
    private DatePicker dpDate;

    private Button btnSubmit;
    private Button btnCancel;
    private Button btnDelete;

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense_layout);

        final Activity activity = this;


        txtDescription = (EditText) findViewById(R.id.txtDescription);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        dpDate = (DatePicker) findViewById(R.id.dpDate);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        Intent info = getIntent();
        init(info);
    }

    private void init(Intent intent) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnCategory.setAdapter(adapter);

        Expense expense = (Expense) intent.getSerializableExtra(EXPENSE_EXTRA);
        position = intent.getIntExtra(POSITION_EXTRA, -1);
        if (position < 0) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

        txtDescription.setText(expense.getDescription());
        txtPrice.setText(expense.getPrice() + "");

        int cat = adapter.getPosition(expense.getType());
        cat = Math.max(0, cat);

        spnCategory.setSelection(cat);

        dpDate.updateDate(expense.getDate().getYear(),
                          expense.getDate().getMonth() - 1,
                          expense.getDate().getDay());
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            Log.d("Edit Expense", "Submit, Hoe");

            Intent intent = new Intent();
            intent.putExtra(POSITION_EXTRA, position);
            intent.putExtra(EXPENSE_EXTRA, new Expense(txtDescription.getText().toString(),
                    Double.parseDouble(txtPrice.getText().toString()),
                    (String) spnCategory.getSelectedItem(),
                    getDatePickerDate()));



            setResult(RESULT_SUCCESS, intent);
        }
        else if (v == btnCancel) {
            setResult(RESULT_CANCEL);
        }
        else if (v == btnDelete) {
            Intent intent = new Intent();
            intent.putExtra(POSITION_EXTRA, position);

            setResult(RESULT_DELETE, intent);
        }

        finish();
    }

    private ExpenseDate getDatePickerDate() {
        int month = dpDate.getMonth() + 1;
        int day = dpDate.getDayOfMonth();
        int year = dpDate.getYear();

        return new ExpenseDate(month, day, year);
    }

}