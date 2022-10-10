package codesver.tannae.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import codesver.tannae.R;

import java.util.ArrayList;

import codesver.tannae.activity.menu.HistoryActivity;
import codesver.tannae.activity.menu.ReceiptActivity;
import codesver.tannae.domain.Content;
import codesver.tannae.domain.History;

public class ListViewAdapter<T> extends BaseAdapter {

    private final ArrayList<T> items = new ArrayList();

    public void addItem(T item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressLint({"ResourceType", "ViewHolder", "SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        T t = items.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (t.getClass().equals(History.class)) {
            int layout = R.layout.list_view_history;
            convertView = inflater.inflate(layout, parent, false);
            History history = (History) t;
            ((TextView) convertView.findViewById(R.id.text_date_list_view_history)).setText(history.getRequestTime().toString().substring(0, 10));
            ((TextView) convertView.findViewById(R.id.text_origin_list_view_history)).setText(history.getOrigin());
            ((TextView) convertView.findViewById(R.id.text_destination_list_view_history)).setText(history.getDestination());
            ((TextView) convertView.findViewById(R.id.text_fare_list_view_history)).setText(history.getRealFare() + "p");
            convertView.findViewById(R.id.button_details_list_view_history).setOnClickListener(v -> startReceipt(context, history));
        }

        return convertView;
    }

    private void startReceipt(Context context, History history) {
        context.startActivity(new Intent(context, ReceiptActivity.class).putExtra("hsn", history.getHsn()));
    }
}
