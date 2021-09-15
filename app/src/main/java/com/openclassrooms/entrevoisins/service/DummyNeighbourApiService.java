package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }


    @Override
    public void favouriteNeighbour(Neighbour neighbour) {
        neighbour.setFavourite(!neighbour.isFavourite());
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }


    @Override
    public List<Neighbour> getFavouriteNeighbours() {
        List<Neighbour> neighbourList = new ArrayList<Neighbour>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavourite()) {
                neighbourList.add(neighbour);
            }
        }
        return neighbourList;

        // return neighbours.stream().filter(neighbour -> neighbour.isFavourite()).collect(Collectors.toList());
    }

}
