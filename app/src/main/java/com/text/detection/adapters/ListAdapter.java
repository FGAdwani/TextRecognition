package com.text.detection.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.text.detection.databinding.TextItemBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    Context c;
    List<String> list = new ArrayList<>();
    private static final String TAG = "ListAdapter";

    public ListAdapter(Context c) {
        Log.e(TAG, "ListAdapter: ");
        this.c = c;
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = c.getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String a = sharedPreferences.getString("list", "[]");
        Log.e(TAG, "ListAdapter: a:"+a );
        try {
            JSONArray x = new JSONArray(a);
            for (int i = 0; i < x.length(); i++) {
                list.add(x.getString(i));
                notifyItemInserted(i);
            }
//            notifyDataSetChanged();
            Log.e(TAG, "ListAdapter: "+list.size() );
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "ListAdapter: "+e.toString() );
        }

    }

    public static void storeString(String s, Context c) {
// Storing data into SharedPreferences
        SharedPreferences sharedPreferences = c.getSharedPreferences("MySharedPref", MODE_PRIVATE);


        String a = sharedPreferences.getString("list", "[]");
        try {
            JSONArray x = new JSONArray(a);
            x.put(s);
            String b = x.toString();
            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("list", b);
            myEdit.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHolder(TextItemBinding.inflate(LayoutInflater.from(c), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.b.setMata(list.get(position));
        holder.b.getRoot().setOnClickListener(view -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(list.get(position));
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("From Text detection",list.get(position));
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(c, "Text Copied to clip board", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextItemBinding b;

        public ListHolder(@NonNull TextItemBinding itemView) {
            super(itemView.getRoot());
            b = itemView;
        }
    }
}
