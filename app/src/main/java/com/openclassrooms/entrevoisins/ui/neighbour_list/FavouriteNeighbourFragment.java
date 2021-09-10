package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.AddToFavouriteEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class FavouriteNeighbourFragment extends Fragment {

        private NeighbourApiService mApiService;
        private List<Neighbour> mNeighbours;
        private RecyclerView mRecyclerView;
        private MyFavouriteNeighbourRecyclerViewAdapter mAdapter;

        /**
         * Create and return a new instance
         * @return @{@link FavouriteNeighbourFragment}
         */
        public static FavouriteNeighbourFragment newInstance() {
            FavouriteNeighbourFragment fragment = new FavouriteNeighbourFragment();
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mApiService = DI.getNeighbourApiService();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //TODO 3C
            View view = inflater.inflate(R.layout.fragment_list_favourite_neighbours, container, false);
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            return view;
        }

        /**
         * Init the List of neighbours
         */
        private void initList() {
            mNeighbours = mApiService.getFavouriteNeighbours();
            mRecyclerView.setAdapter(mAdapter = new MyFavouriteNeighbourRecyclerViewAdapter(mNeighbours));


            // TODO 3A
            mAdapter.setOnItemClickListener(new MyFavouriteNeighbourRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position)
                {
                    Intent intent = new Intent(getActivity(), ProfileNeighbourActivity.class);
                    intent.putExtra("mName"   , mNeighbours.get(position).getName());
                    intent.putExtra("mAboutMe", mNeighbours.get(position).getAboutMe());
                    intent.putExtra("mPhone"  , mNeighbours.get(position).getPhoneNumber());
                    intent.putExtra("mAdress" , mNeighbours.get(position).getAddress());
                    intent.putExtra("mAvatar" , mNeighbours.get(position).getAvatarUrl());
                    intent.putExtra("mPosition",position);
                    startActivity(intent);
                }

            });
        }

        @Override
        public void onResume() {
            super.onResume();
            initList();
        }

        @Override
        public void onStart() {
            super.onStart();
            EventBus.getDefault().register(this);
        }

        @Override
        public void onStop() {
            super.onStop();
            EventBus.getDefault().unregister(this);
        }


        @Subscribe
        public void onFavourite(AddToFavouriteEvent event) {
            mApiService.favouriteNeighbour(event.neighbour);
            initList();
        }
    }
