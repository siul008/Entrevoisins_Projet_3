package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;


public class AddToFavouriteEvent
{
    //TODO 3B
    public Neighbour neighbour;

    public AddToFavouriteEvent(Neighbour neighbour)
    {
        this.neighbour = neighbour;
    }

}
