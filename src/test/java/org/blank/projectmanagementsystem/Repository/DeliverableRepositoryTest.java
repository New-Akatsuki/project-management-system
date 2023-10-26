package org.blank.projectmanagementsystem.Repository;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DeliverableRepositoryTest {

    @Autowired
    private DeliverableRepository deliverableRepository;

    @Test
    public void testFindByName() {
        // Arrange
        Deliverable deliverable = new Deliverable();
        deliverable.setName("TestDeliverable");
        deliverableRepository.save(deliverable);

        // Act
        Deliverable foundDeliverable = deliverableRepository.findByName("TestDeliverable");

        // Assert
        assertEquals("TestDeliverable", foundDeliverable.getName());
    }
}
