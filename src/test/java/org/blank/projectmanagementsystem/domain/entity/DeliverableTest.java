package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliverableTest {

    private Deliverable deliverable;
    private Deliverable deliverableWithBuilder;

    @BeforeEach
    public void setUp() {
        deliverable = new Deliverable();
        deliverable.setId(1);
        deliverable.setName("Simple Deliverable");

        deliverableWithBuilder=deliverable.builder()
                .id(2)
                .name("SimpleNew Deliverable")
                .build();

    }

    @Test
    public void testGetId() {
        assertEquals(1, deliverable.getId());
        assertEquals(2, deliverableWithBuilder.getId());



    }

    @Test
    public void testGetName() {
        assertEquals("Simple Deliverable", deliverable.getName());
        assertEquals("SimpleNew Deliverable", deliverable.getName());


    }

    @Test
    public void testSetName() {
        deliverable.setName("Updated Deliverable");
        deliverable.setName("UpdatedNew Deliverable");

    }

    @Test
    public void testEquals() {
        Deliverable sameDeliverable = new Deliverable();
        sameDeliverable.setId(1);
        sameDeliverable.setName("Simple Deliverable");

        Deliverable anotherDeliverableWithBuilder=Deliverable.builder()
                        .id(2)
                        .name("SimpleNew Deliverable")
                        .build();

        assertEquals(deliverable, sameDeliverable);
        assertEquals(deliverableWithBuilder, anotherDeliverableWithBuilder);
    }

    @Test
    public void testNotEquals() {

    }

    @Test
    public void testHashCode() {
        Deliverable sameDeliverable = new Deliverable();
        sameDeliverable.setId(1);
        sameDeliverable.setName("Simple Deliverable");
        assertEquals(deliverable.hashCode(), sameDeliverable.hashCode());
    }
}

