package com.moghies.SpendingTracker;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.moghies.SpendingTracker.model.Expense;

import java.io.Serializable;

/**
 * Created by mmogh on 5/22/2016.
 */
public class ExpenseListFrag extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_list_layout, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("List Item Fragment", "Item Clicked!");
        ExpenseListAdapter adapter = (ExpenseListAdapter) l.getAdapter();
        Activity activity = adapter.getActivity();

        Intent intent = new Intent(activity, EditExpenseActivity.class);
        intent.putExtra(EditExpenseActivity.EXPENSE_EXTRA, (Serializable) adapter.getItem(position));
        intent.putExtra(EditExpenseActivity.POSITION_EXTRA, position);

        activity.startActivityForResult(intent, 0);
    }


}
