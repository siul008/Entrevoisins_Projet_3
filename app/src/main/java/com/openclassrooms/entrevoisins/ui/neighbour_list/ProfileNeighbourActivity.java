package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.AddToFavouriteEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileNeighbourActivity extends AppCompatActivity
{
    // TODO : 3A
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;

    @BindView(R.id.text_profile_name)
    TextView mName;
    @BindView(R.id.text_profile_name_2)
    TextView mName2;
    @BindView(R.id.profile_text_description)
    TextView mAboutMe;
    @BindView(R.id.text_phoneNumber)
    TextView mPhoneNumber;
    @BindView(R.id.text_localisation)
    TextView mAdress;
    @BindView(R.id.text_url)
    TextView mUrl;
    @BindView(R.id.image_avatar)
    ImageView mAvatar;
    @BindView(R.id.profile_favourite_button)
    FloatingActionButton mFavouriteButton;
    @BindView(R.id.arrow_back)
    ImageView mArrowBack;

    protected void onCreate(Bundle savedInstanceState)
    {


        mApiService = DI.getNeighbourApiService();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profile);
        ButterKnife.bind(this);


          Bundle extras = getIntent().getExtras();
          String name = extras.getString("mName");
          String about = extras.getString("mAboutMe");
          String phone = extras.getString("mPhone");
          String adress = extras.getString("mAdress");
          String avatar = extras.getString("mAvatar");
          int position = extras.getInt("mPosition");

       mNeighbours = mApiService.getNeighbours();
       Neighbour neighbour = mNeighbours.get(position);
       setFavouriteStar(neighbour);

     // TODO 3.B
        mFavouriteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                EventBus.getDefault().post(new AddToFavouriteEvent(neighbour));

               setFavouriteStar(neighbour);

            }
        });
        mArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });




          mName.setText("" + name);
          mName2.setText("" + name);
          mAboutMe.setText("" + about);
          mPhoneNumber.setText("" + phone);
          mAdress.setText("" + adress);
          mUrl.setText("www.facebook.fr/" + name.toLowerCase());

          Glide.with(ProfileNeighbourActivity.this)
                  .load(avatar)
                  .centerCrop()
                  .into(mAvatar);


    }
    @Override
    public void onResume() {
        super.onResume();
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
    public void AddToFavourite(AddToFavouriteEvent event)
    {
        mApiService.favouriteNeighbour(event.neighbour);

    }

    private void setFavouriteStar (Neighbour neighbour)
    {
        // Si isFavourite est vrai  ?R.drawable.ic_star_yellow
        // sinon :R.drawable.ic_star_border;

        int star = neighbour.isFavourite()
                ?R.drawable.ic_star_yellow
                :R.drawable.ic_star_border;

        mFavouriteButton.setImageDrawable(getDrawable(star));
    }

}



