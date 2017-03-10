package kode.kinopoisk.rudak.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kode.kinopoisk.rudak.mvp.models.KeyValue;


public class UtilResource {


    public  static List<KeyValue> getKeyValueArrayFromStringArray(Context ctx, String separator, int resource) {
        String[] array = ctx.getResources().getStringArray(resource);
        List<KeyValue> result = new ArrayList<KeyValue>();
        for (String str : array) {
            String[] splittedItem = str.split(separator);
            result.add(new KeyValue(splittedItem[0], splittedItem[1]));
        }
        return result;
    }

}



