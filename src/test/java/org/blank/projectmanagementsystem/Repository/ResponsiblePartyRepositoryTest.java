package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.ResponsibleParty;
import org.blank.projectmanagementsystem.repository.ResponsiblePartyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ResponsiblePartyRepositoryTest {

    @Mock
    private ResponsiblePartyRepository responsiblePartyRepository;



    @Test
    public void testFindByNonExistentId() {
        // When
        Optional<ResponsibleParty> foundParty = responsiblePartyRepository.findById(-1L);

        // Then
        assertThat(foundParty).isEmpty();
    }


}

