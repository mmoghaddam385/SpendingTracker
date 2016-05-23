package com.moghies.SpendingTracker;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import com.moghies.SpendingTracker.model.Expense;
import com.moghies.SpendingTracker.model.ExpenseType;

public class MainActivity extends Activity implements View.OnClickListener {

    private ListFragment expenseListFragment;
    private ExpenseListAdapter expenseAdapter;

    private SharedPreferences prefs;

    private Button btnAdd;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

        this.expenseListFragment = (ListFragment) getFragmentManager().findFragmentById(R.id.frgExpenseList);

        this.expenseAdapter = new ExpenseListAdapter(null, this);
        this.expenseListFragment.setListAdapter(expenseAdapter);
        this.expenseAdapter.load(prefs);
        //this.expenseAdapter.add(new Expense("Test", 12, "myType"));


        this.btnAdd = (Button) findViewById(R.id.fab);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EditExpenseActivity.RESULT_SUCCESS) {
            Expense expense = (Expense) data.getSerializableExtra(EditExpenseActivity.EXPENSE_EXTRA);
            int position = data.getIntExtra(EditExpenseActivity.POSITION_EXTRA, -1);

            if (position != -1) {
                expenseAdapter.updateExpense(position, expense);
            } else {
                expenseAdapter.add(expense);
            }

            expenseAdapter.save(prefs);

            Log.d("Expense List Frag", "Got Data: " + expense.getDescription());
        }
        else if (resultCode == EditExpenseActivity.RESULT_DELETE) {
            int position = data.getIntExtra(EditExpenseActivity.POSITION_EXTRA, -1);

            if (position != -1) {
                expenseAdapter.remove(position);
            } else {
                Log.e("MainActivity", "Attempting to delete element -1...");
            }

            expenseAdapter.save(prefs);
        }
    }

    @Override
    public void onClick(View v) {
        Expense e = new Expense("", 0, "");
        Intent intent = new Intent(this, EditExpenseActivity.class);
        intent.putExtra(EditExpenseActivity.EXPENSE_EXTRA, e);
        intent.putExtra(EditExpenseActivity.POSITION_EXTRA, -1);

        startActivityForResult(intent, 0);
    }
}
