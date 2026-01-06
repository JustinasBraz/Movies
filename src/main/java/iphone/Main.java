package iphone;

import iphone.model.MobilePhone;
import iphone.service.MobilePhoneService;

public class Main {
    public static void main(String[] args) {
        MobilePhoneService service = new MobilePhoneService();

        System.out.println("=== MobilePhone Service Demo ===\n");

        // Create: MobilePhone can be added to in-memory storage
        System.out.println("1. CREATE - Adding MobilePhone to in-memory storage");
        MobilePhone phone1 = new MobilePhone("Apple", "iPhone 13", 2021);
        MobilePhone saved = service.create(phone1);
        System.out.println("   Created phone with ID: " + saved.getId());
        System.out.println("   Brand: " + saved.getBrand() + ", Model: " + saved.getModel() + ", Year: " + saved.getYear());

        // Create: Added MobilePhone is immediately retrievable
        System.out.println("\n2. CREATE - Verifying added MobilePhone is immediately retrievable");
        MobilePhone retrieved = service.getById(saved.getId());
        if (retrieved != null) {
            System.out.println("   ✓ Phone is immediately retrievable");
            System.out.println("   Retrieved: " + retrieved.getBrand() + " " + retrieved.getModel());
        }

        // Get by ID: Existing ID returns correct MobilePhone data
        System.out.println("\n3. GET BY ID - Existing ID returns correct MobilePhone data");
        MobilePhone existing = service.getById(saved.getId());
        if (existing != null) {
            System.out.println("   ✓ Found phone: " + existing.getBrand() + " " + existing.getModel());
        }

        // Get by ID: Non-existing ID returns null or empty result
        System.out.println("\n4. GET BY ID - Non-existing ID returns empty result");
        MobilePhone nonExisting = service.getById(999L);
        if (nonExisting == null) {
            System.out.println("   ✓ Non-existing ID returns null (as expected)");
        }

        // Update by ID: Existing MobilePhone is updated successfully
        System.out.println("\n5. UPDATE BY ID - Updating existing MobilePhone");
        MobilePhone updatedPhone = new MobilePhone("Apple", "iPhone 14", 2022);
        MobilePhone updated = service.updateById(saved.getId(), updatedPhone);
        if (updated != null) {
            System.out.println("   ✓ Phone updated successfully");
            System.out.println("   Updated: " + updated.getBrand() + " " + updated.getModel() + " (" + updated.getYear() + ")");
        }

        // Update by ID: Updated data is retrievable
        System.out.println("\n6. UPDATE BY ID - Verifying updated data is retrievable");
        MobilePhone retrievedUpdated = service.getById(saved.getId());
        if (retrievedUpdated != null) {
            System.out.println("   ✓ Updated data is retrievable");
            System.out.println("   Retrieved updated: " + retrievedUpdated.getModel() + " (" + retrievedUpdated.getYear() + ")");
        }

        // Delete by ID: Existing MobilePhone is deleted successfully
        System.out.println("\n7. DELETE BY ID - Deleting existing MobilePhone");
        boolean deleted = service.deleteById(saved.getId());
        if (deleted) {
            System.out.println("   ✓ Phone deleted successfully");
        }

        // Delete by ID: Deleted MobilePhone cannot be retrieved
        System.out.println("\n8. DELETE BY ID - Verifying deleted MobilePhone cannot be retrieved");
        MobilePhone afterDelete = service.getById(saved.getId());
        if (afterDelete == null) {
            System.out.println("   ✓ Deleted phone cannot be retrieved (as expected)");
        }

        System.out.println("\n=== All operations completed successfully ===");
    }
}
