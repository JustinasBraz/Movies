package iphone.service;

import iphone.model.MobilePhone;

import java.util.ArrayList;
import java.util.List;

public class MobilePhoneService {
    private final List<MobilePhone> storage = new ArrayList<>();
    private long seq = 1;

    public MobilePhoneService() {
        // Pre-populate some sample phones into the in-memory list
        create(new MobilePhone("Apple", "iPhone 13", 2021));
        create(new MobilePhone("Samsung", "S21", 2021));
        create(new MobilePhone("Google", "Pixel 5", 2020));
    }

    public MobilePhone create(MobilePhone phone) {
        if (phone == null) {
            throw new IllegalArgumentException("phone must not be null");
        }
        if (phone.getId() == null) {
            phone.setId(seq++);
        }
        storage.add(phone);
        return phone;
    }

    public MobilePhone getById(Long id) {
        if (id == null) return null;
        return storage.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    public MobilePhone updateById(Long id, MobilePhone updatedPhone) {
        if (id == null || updatedPhone == null) return null;
        MobilePhone existing = getById(id);
        if (existing != null) {
            updatedPhone.setId(id);
            storage.replaceAll(p -> id.equals(p.getId()) ? updatedPhone : p);
            return updatedPhone;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        if (id == null) return false;
        return storage.removeIf(p -> id.equals(p.getId()));
    }

    public void clear() {
        storage.clear();
        seq = 1;
    }
}
