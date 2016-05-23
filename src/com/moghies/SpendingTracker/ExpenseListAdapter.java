package com.moghies.SpendingTracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.moghies.SpendingTracker.model.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmogh on 5/22/2016.
 */
public class ExpenseListAdapter extends BaseAdapter {

    private static final String NUM_EXPENSES_KEY = "NumExpenses";
    private static final String EXPENSE_ROOT = "Expense_";

    private List<Expense> data;
    private Activity activity;


    public ExpenseListAdapter(List<Expense> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void save(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.putInt(NUM_EXPENSES_KEY, data.size());
        for (int n = 0; n < data.size(); n++) {
            editor.putString(EXPENSE_ROOT + n, data.get(n).toString());
        }

        editor.commit();
    }

    public void load(SharedPreferences prefs) {
        int num = prefs.getInt(NUM_EXPENSES_KEY, 0);
        data = new ArrayList<>();

        for (int n = 0; n < num; n++) {
            String asStr = prefs.getString(EXPENSE_ROOT + n, "");
            Expense e = Expense.FromString(asStr);
            data.add(e);
        }

        notifyDataSetChanged();
    }

    public void addAll(List<Expense> items) {
        if (data == null) {
            data = new ArrayList<>(items);
        }
        else {
            data.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void add(Expense expense) {
        if (data == null) {
            data = new ArrayList<>();
        }

        data.add(expense);
        notifyDataSetChanged();
    }

    public void updateExpense(int position, Expense expense) {
        data.set(position, expense);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        public TextView tvDescription;
        public TextView tvType;
        public TextView tvPrice;
        public TextView tvDate;

        public Expense itemData;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        //view doensn't exist yet, make that ho
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.expense_layout, null);

            holder = new ViewHolder();
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            holder.tvType = (TextView) v.findViewById(R.id.tvType);
            holder.tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            holder.tvDate = (TextView) v.findViewById(R.id.tvDate);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        if (data == null || data.size() == 0) {
            //don't worry about it because ListFragment has our backs
        }
        else {
            Expense itemData = data.get(position);

            holder.tvDescription.setText(itemData.getDescription());
            holder.tvType.setText(itemData.getType().toString());
            holder.tvPrice.setText(itemData.getStrPrice());
            holder.tvDate.setText(itemData.getDateString());

            holder.itemData = itemData;
        }

        v.setTag(holder);

        return v;
    }

}
