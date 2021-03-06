package ku.eecscap.team8.auricle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Austin Kurtti on 4/23/2017.
 * Last Edited by Austin Kurtti on 4/30/2017
 */

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private ListingHelper[] mDataSet;
    private Context mContext;
    private View rootView;
    private ListingFragment listingFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFilename, tvLength, tvDateCreated;
        public ViewHolder(View view) {
            super(view);
            tvFilename = (TextView) view.findViewById(R.id.listing_item_filename);
            tvLength = (TextView) view.findViewById(R.id.listing_item_length);
            tvDateCreated = (TextView) view.findViewById(R.id.listing_item_date_created);
        }
    }

    public ListingAdapter(ListingHelper[] dataSet, Context ctx, View view, ListingFragment fragment) {
        mDataSet = dataSet;
        mContext = ctx;
        rootView = view;
        listingFragment = fragment;
    }

    @Override
    public ListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int listingId = mDataSet[position].getId();
        final String filename = mDataSet[position].getFilename();
        final String format = mDataSet[position].getFormat();
        final String length = mDataSet[position].getLength();
        final String dateCreated = mDataSet[position].getDateCreated();
        final String fullFile = filename + format;

        // set listener for tapping on listing item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = mContext.getFilesDir().getAbsolutePath();
                ListingControls controls = new ListingControls(mContext);
                controls.show(rootView, listingFragment, listingId, path, fullFile, dateCreated, length);
            }
        });

        holder.tvFilename.setTag(R.integer.tag_key_listing_id, listingId);
        holder.tvFilename.setText(fullFile);
        holder.tvLength.setText(length);
        holder.tvDateCreated.setText(dateCreated);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public void clearDataSet() {
        mDataSet = new ListingHelper[0];
    }

    public void fillDataSet(ListingHelper[] newSet) {
        mDataSet = newSet;
    }
}
