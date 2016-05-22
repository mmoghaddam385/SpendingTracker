package com.moghies.SpendingTracker;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import com.moghies.SpendingTracker.model.Expense;
import com.moghies.SpendingTracker.model.ExpenseType;

public class MainActivity extends Activity {

    private ListFragment expenseListFragment;
    private ExpenseListAdapter expenseAdapter;

    private SharedPreferences prefs;

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

}
