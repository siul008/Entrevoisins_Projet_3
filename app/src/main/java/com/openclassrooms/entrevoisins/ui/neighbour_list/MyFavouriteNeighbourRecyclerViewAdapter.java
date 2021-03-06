package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.AddToFavouriteEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavouriteNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyFavouriteNeighbourRecyclerViewAdapter.ViewHolder> {
    private final List<Neighbour> mNeighbours;


    private MyNeighbourRecyclerViewAdapter.OnItemClickListener mListener;


    public MyFavouriteNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    public void setOnItemClickListener(MyNeighbourRecyclerViewAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favourite_neighbour, parent, false);
        return new ViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar
                .getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);


        holder.mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddToFavouriteEvent(neighbour));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_favourite_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_favourite_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_fav_button)
        public ImageButton mFavouriteButton;


        public ViewHolder(View view, MyNeighbourRecyclerViewAdapter.OnItemClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(mNeighbours.get(position));
                        }
                    }
                }
            });

        }

    }
}

