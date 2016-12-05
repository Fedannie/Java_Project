package project.fedorova.polyglotte.layouts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import project.fedorova.polyglotte.R;
import project.fedorova.polyglotte.excercise.Exercise;

public class ExerciseListAdapter extends BaseAdapter {
    private ArrayList<Exercise> exerciseList;
    Activity activity;
    private LayoutInflater layoutInflater;
//    ImageDownloader imageDownloader = new ImageDownloader();
    public static final int SIZE = 9;
    public ExerciseListAdapter(Context context) {
        exerciseList = new ArrayList<Exercise>();
        exerciseList.add(new Exercise("Translation by word"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Word by translation"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Choose translation by word"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Choose word by translation"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Choose picture"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Translate by picture"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Connect"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Correct the mistake"/*, Resource.drawable.elephant*/));
        exerciseList.add(new Exercise("Whole"/*, Resource.drawable.elephant*/));
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Exercise eachMyData;
        class ViewHolder{
            TextView title;
        //    ImageView imageView;
        }
        ViewHolder VH = new ViewHolder();
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.excersicelistlayout, null);
            VH.title = (TextView)view.findViewById(R.id.titleView);
            view.setTag(VH);
        }
        else{
            VH = (ViewHolder)view.getTag();
        }

        eachMyData = exerciseList.get(position);
        VH.title.setText(eachMyData.getTitle());

        //imageDownloader.download(eachMyData.getImage(), VH.imageView);

        return view;
    }
}
