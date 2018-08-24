package be.kristofheyndels.mobdev.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<String> {
    private final ArrayList<String> stringList;

    public MyArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        stringList = new ArrayList<>();
    }

    public MyArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        stringList = new ArrayList<>();
    }

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, (String[]) objects);
        stringList = new ArrayList<>();
    }

    public MyArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, (String[]) objects);
        this.stringList = new ArrayList<>(Arrays.asList((String[]) objects));
    }

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.stringList = new ArrayList<String>(objects);
    }

    public MyArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        this.stringList = new ArrayList<String>(objects);
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }
}
