package iphone;

import iphone.model.MobilePhone;
import iphone.repository.MobilePhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MobilePhoneRepositoryTest {
    private MobilePhoneRepository repo;

    @BeforeEach
    void setup() {
        repo = new MobilePhoneRepository();
        repo.clear();
    }

    @Test
    void addAndRetrieve_inMemoryStorage() {
        MobilePhone phone = new MobilePhone("Apple", "iPhone 13", 2021);
        MobilePhone saved = repo.save(phone);

        assertNotNull(saved.getId(), "Saved phone should have an ID");

        Optional<MobilePhone> fetched = repo.findById(saved.getId());
        assertTrue(fetched.isPresent(), "Saved phone should be retrievable");
        assertEquals("Apple", fetched.get().getBrand());
        assertEquals("iPhone 13", fetched.get().getModel());
        assertEquals(2021, fetched.get().getYear());
    }

    @Test
    void getById_existingAndNonExisting() {
        MobilePhone phone = new MobilePhone("Samsung", "S21", 2021);
        MobilePhone saved = repo.save(phone);

        Optional<MobilePhone> existing = repo.findById(saved.getId());
        assertTrue(existing.isPresent());
        assertEquals("Samsung", existing.get().getBrand());

        Optional<MobilePhone> missing = repo.findById(999L);
        assertTrue(missing.isEmpty());
    }

    @Test
    void updateById_existingPhoneIsUpdated() {
        MobilePhone phone = new MobilePhone("Google", "Pixel 5", 2020);
        MobilePhone saved = repo.save(phone);

        saved.setModel("Pixel 6");
        saved.setYear(2021);
        repo.save(saved);

        Optional<MobilePhone> updated = repo.findById(saved.getId());
        assertTrue(updated.isPresent());
        assertEquals("Pixel 6", updated.get().getModel());
        assertEquals(2021, updated.get().getYear());
    }

    @Test
    void deleteById_existingIsDeleted() {
        MobilePhone phone = new MobilePhone("OnePlus", "8T", 2020);
        MobilePhone saved = repo.save(phone);
        Long id = saved.getId();

        repo.deleteById(id);

        Optional<MobilePhone> afterDelete = repo.findById(id);
        assertTrue(afterDelete.isEmpty(), "Deleted phone should not be retrievable");
    }
}

