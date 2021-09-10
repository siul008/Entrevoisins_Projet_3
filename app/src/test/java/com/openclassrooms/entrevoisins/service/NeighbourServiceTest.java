package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addToFavourite() {
        // Given
        Neighbour neighbour = new Neighbour(1, "nameTest", "urlTest", "adressTest",
                "phoneTest", "boutmeTest", false);
        // When
        service.favouriteNeighbour(neighbour);
        // Then
        assertTrue(neighbour.isFavourite());
    }

    @Test
    public void listFavourite() {
        // Given
        Neighbour neighbour1 = new Neighbour(1, "k", "l", "l", "k", "k", false);
        Neighbour neighbour2 = new Neighbour(2, "h", "l", "l", "k", "k", false);
        Neighbour neighbour3 = new Neighbour(3, "n", "l", "l", "k", "k", false);
        service.createNeighbour(neighbour1);
        service.createNeighbour(neighbour2);
        service.createNeighbour(neighbour3);
        service.favouriteNeighbour(neighbour1);
        service.favouriteNeighbour(neighbour2);
        // When
        List<Neighbour> favouriteNeighbours = service.getFavouriteNeighbours();
        // Then
        assertEquals(2, favouriteNeighbours.size());
        assert favouriteNeighbours.contains(neighbour1);
        assert favouriteNeighbours.contains(neighbour2);
        assertFalse(favouriteNeighbours.contains(neighbour3));
    }


    @Test
    public void listFavouriteWhenEmpty() {
        // Given
        Neighbour neighbour1 = new Neighbour(1, "k", "l", "l", "k", "k", false);
        Neighbour neighbour2 = new Neighbour(2, "h", "l", "l", "k", "k", false);
        Neighbour neighbour3 = new Neighbour(3, "n", "l", "l", "k", "k", false);
        service.createNeighbour(neighbour1);
        service.createNeighbour(neighbour2);
        service.createNeighbour(neighbour3);
        // When
        List<Neighbour> favouriteNeighbours = service.getFavouriteNeighbours();
        // Then
        assertEquals(0, favouriteNeighbours.size());
        assertFalse(favouriteNeighbours.contains(neighbour1));
        assertFalse(favouriteNeighbours.contains(neighbour2));
        assertFalse(favouriteNeighbours.contains(neighbour3));
    }

    @Test
    public void removeFavourite() {
        // Given
        Neighbour neighbour1 = new Neighbour(1, "k", "l", "l", "k", "k", true);

        // When
        service.favouriteNeighbour(neighbour1);
        // Then
        assertFalse(neighbour1.isFavourite());
    }

}
