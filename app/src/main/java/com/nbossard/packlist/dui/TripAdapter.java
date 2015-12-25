package com.nbossard.packlist.dui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;

import java.util.List;

/**
 * @author Created by nbossard on 25/12/15.
 */
public class TripAdapter extends BaseAdapter {

    // *********************** INNER CLASS *****************************************************************

    /**
     * View holder, works better for recycling of views.
     *
     * @author Nicolas BOSSARD
     *
     */
    private class InnerMyViewHolder
    {

        // getting views
        /**
         * Reference (result of findviewbyid) to the trip name.
         */
        private TextView tvName;

        /**
         * Reference (result of findviewbyid) to the trip start date.
         */
        private TextView tvStartDate;

        /**
         * Reference (result of findviewbyid) to the trip end date.
         */
        private TextView tvEndDate;
    }

    // ********************** FIELDS ************************************************************************

    /**
     * Devices to be displayed in the list.
     */
    private final List<Trip> mTripsList;

    /**
     * Provided context.
     */
    private final Context mContext;

    // *********************** METHODS **********************************************************************

    /**
     * Standard constructor.
     *
     * @param parResList
     *            data to be displayed in the list.
     * @param parContext
     *            context sic
     */
    public TripAdapter(final List<Trip> parResList, final Context parContext)
    {
        super();
        mTripsList = parResList;
        mContext = parContext;
    }


    @Override
    public int getCount() {
        return mTripsList.size();
    }

    @Override
    public Object getItem(int parPosition) {
        return mTripsList.get(parPosition);
    }

    @Override
    public long getItemId(int parPosition) {
        return parPosition;
    }

    @Override
    public View getView(int parPosition, View parConvertView, ViewGroup parParentView) {
        InnerMyViewHolder vHolderRecycle;
        if (parConvertView == null)
        {
            vHolderRecycle = new InnerMyViewHolder();
            final LayoutInflater inflater = (LayoutInflater) TripAdapter.this.
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            parConvertView = inflater.inflate(R.layout.trip_adapter, parParentView, false);
        } else
        {
            vHolderRecycle = (InnerMyViewHolder) parConvertView.getTag();
        }
        // getting views
        vHolderRecycle.tvName = (TextView) parConvertView.findViewById(R.id.ta__name);
        vHolderRecycle.tvStartDate = (TextView) parConvertView.findViewById(R.id.ta__start_date);
        vHolderRecycle.tvEndDate = (TextView) parConvertView.findViewById(R.id.ta__end_date);

        final Trip oneDev = mTripsList.get(parPosition);

        // updating views
        vHolderRecycle.tvName.setText(oneDev.getName());
        vHolderRecycle.tvStartDate.setText(oneDev.getStartDate());
        vHolderRecycle.tvEndDate.setText(oneDev.getEndDate());

        parConvertView.setTag(vHolderRecycle);
        return parConvertView;
    }
}
