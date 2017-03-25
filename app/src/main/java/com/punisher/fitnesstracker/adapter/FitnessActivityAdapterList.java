package com.punisher.fitnesstracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.punisher.fitnesstracker.R;
import com.punisher.fitnesstracker.dto.FitnessActivity;
import com.punisher.fitnesstracker.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *  ArrayAdapter for the list of activity
 */
public class FitnessActivityAdapterList extends ArrayAdapter<FitnessActivity> {

    private Context _context;
    private int _ressourceId;
    private FitnessActivity[] _data;
    private Filter _filter = null;

    public FitnessActivityAdapterList(Context context, int resource, FitnessActivity[] fa) {
        super(context, resource, fa);
        _context = context;
        _ressourceId = resource;
        _data = fa;

        _filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<FitnessActivity> list = new ArrayList<FitnessActivity>();
                for (FitnessActivity fa : _data) {
                    if (fa.getFitnessType().toString().contains(constraint)) {
                        list.add(fa);
                    }
                }

                results.values = list;
                results.count = list.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<FitnessActivity> list = (List<FitnessActivity>)results.values;
                _data = list.toArray(new FitnessActivity[0]);
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FitnessHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(_ressourceId, parent, false);

            holder = new FitnessHolder();
            holder.txtDate = (TextView)row.findViewById(R.id.fitness_date);
            holder.txtAverage = (TextView)row.findViewById(R.id.fitness_average);
            holder.txtDistance = (TextView)row.findViewById(R.id.fitness_distance);
            holder.txtDuration = (TextView)row.findViewById(R.id.fitness_duration);
            holder.imgType = (ImageView)row.findViewById(R.id.fitness_type_icon);

            row.setTag(holder);
        }
        else
        {
            holder = (FitnessHolder)row.getTag();
        }

        FitnessActivity fitness = _data[position];
        holder.imgType.setImageResource(getFitnessTypeImage(fitness.getFitnessType()));
        holder.txtDate.setText(FormatUtil.formatDate(fitness.getDayOfActivity()));
        holder.txtAverage.setText(FormatUtil.formatAverage(fitness.getDistance(), fitness.getDuration()));
        holder.txtDuration.setText(FormatUtil.formatDuration(fitness.getDuration()));
        holder.txtDistance.setText(FormatUtil.getDistance(fitness.getDistance()));
        return row;
    }

    @Override
    public Filter getFilter() {
        return _filter;
    }



    private int getFitnessTypeImage(FitnessActivity.FitnessType ft) {
        if (ft == FitnessActivity.FitnessType.BIKING) {
            return R.mipmap.icon_biking;
        }
        if (ft == FitnessActivity.FitnessType.RUNNING) {
            return R.mipmap.icon_running;
        }
        if (ft == FitnessActivity.FitnessType.SWIMMING) {
            return R.mipmap.icon_swimming;
        }
        return 0;
    }

    static class FitnessHolder
    {
        TextView txtDate;
        TextView txtAverage;
        TextView txtDuration;
        TextView txtDistance;
        ImageView imgType;
    }
}