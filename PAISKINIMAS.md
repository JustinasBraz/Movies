# Kodo Paaiškinimas

## MobilePhoneService.java

```java
package iphone.service;
```
- Nurodo, kad klasė priklauso `iphone.service` paketui

```java
import iphone.model.MobilePhone;
```
- Importuoja `MobilePhone` klasę iš `iphone.model` paketo

```java
import iphone.repository.MobilePhoneRepository;
```
- Importuoja `MobilePhoneRepository` klasę iš `iphone.repository` paketo

```java
import java.util.Optional;
```
- Importuoja `Optional` klasę iš Java standartinės bibliotekos (naudojama saugiai grąžinti reikšmes, kurios gali būti null)

```java
public class MobilePhoneService {
```
- Deklaruoja viešą klasę `MobilePhoneService`

```java
    private final MobilePhoneRepository repository;
```
- Sukuria privačią, final (nekeičiamą) kintamąjį `repository`, kuris saugo `MobilePhoneRepository` objektą
- `final` reiškia, kad šis kintamasis negali būti pakeistas po inicializacijos

```java
    public MobilePhoneService(MobilePhoneRepository repository) {
```
- Konstruktorius, kuris priima `MobilePhoneRepository` objektą kaip parametrą

```java
        this.repository = repository;
```
- Priskiria perduotą `repository` parametrą klasės laukui `this.repository`

```java
    public MobilePhone create(MobilePhone phone) {
```
- Viešas metodas `create`, kuris priima `MobilePhone` objektą ir grąžina `MobilePhone`
- Naudojamas naujam telefonui sukurti

```java
        return repository.save(phone);
```
- Iškviečia `repository.save()` metodą, kad išsaugotų telefoną į atmintinę
- Grąžina išsaugotą telefoną (su priskirtu ID)

```java
    public Optional<MobilePhone> getById(Long id) {
```
- Viešas metodas `getById`, kuris priima `Long` tipo ID ir grąžina `Optional<MobilePhone>`
- `Optional` naudojamas, nes telefonas gali neegzistuoti

```java
        return repository.findById(id);
```
- Iškviečia `repository.findById()` metodą, kad rastų telefoną pagal ID
- Grąžina `Optional`, kuris gali būti tuščias, jei telefonas nerastas

```java
    public MobilePhone updateById(Long id, MobilePhone updatedPhone) {
```
- Viešas metodas `updateById`, kuris priima ID ir naują `MobilePhone` objektą
- Grąžina atnaujintą `MobilePhone` arba `null`, jei telefonas nerastas

```java
        Optional<MobilePhone> existing = repository.findById(id);
```
- Bando rasti esantį telefoną pagal ID ir saugo rezultatą `existing` kintamajame

```java
        if (existing.isPresent()) {
```
- Tikrina, ar `Optional` objektas turi reikšmę (t.y., ar telefonas egzistuoja)

```java
            updatedPhone.setId(id);
```
- Nustato naujo telefono ID į esamą ID (kad atnaujintume, o ne sukurtume naują)

```java
            return repository.save(updatedPhone);
```
- Išsaugo atnaujintą telefoną į repository ir grąžina jį

```java
        }
        return null;
```
- Jei telefonas nerastas, grąžina `null`

```java
    public boolean deleteById(Long id) {
```
- Viešas metodas `deleteById`, kuris priima ID ir grąžina `boolean`
- `true` - jei ištrinta sėkmingai, `false` - jei telefonas nerastas

```java
        Optional<MobilePhone> existing = repository.findById(id);
```
- Bando rasti telefoną pagal ID

```java
        if (existing.isPresent()) {
```
- Tikrina, ar telefonas egzistuoja

```java
            repository.deleteById(id);
```
- Ištrina telefoną iš repository

```java
            return true;
```
- Grąžina `true`, kad nurodytų sėkmingą ištrynimą

```java
        }
        return false;
```
- Jei telefonas nerastas, grąžina `false`

---

## Main.java

```java
package iphone;
```
- Nurodo, kad klasė priklauso `iphone` paketui

```java
import iphone.model.MobilePhone;
```
- Importuoja `MobilePhone` klasę

```java
import iphone.repository.MobilePhoneRepository;
```
- Importuoja `MobilePhoneRepository` klasę

```java
import iphone.service.MobilePhoneService;
```
- Importuoja `MobilePhoneService` klasę

```java
import java.util.Optional;
```
- Importuoja `Optional` klasę

```java
public class Main {
```
- Deklaruoja viešą klasę `Main`

```java
    public static void main(String[] args) {
```
- `main` metodas - programos pradžios taškas
- `static` - metodas priklauso klasei, ne objektui
- `String[] args` - komandinės eilutės argumentai

```java
        MobilePhoneRepository repository = new MobilePhoneRepository();
```
- Sukuria naują `MobilePhoneRepository` objektą ir priskiria jį `repository` kintamajam

```java
        MobilePhoneService service = new MobilePhoneService(repository);
```
- Sukuria naują `MobilePhoneService` objektą, perduodant `repository` kaip parametrą
- Service naudoja repository, kad atliktų operacijas

```java
        System.out.println("=== MobilePhone Service Demo ===\n");
```
- Spausdina antraštę į konsolę
- `\n` - nauja eilutė

```java
        // Create: MobilePhone can be added to in-memory storage
```
- Komentaras, paaiškinantis, ką daro sekančios eilutės

```java
        System.out.println("1. CREATE - Adding MobilePhone to in-memory storage");
```
- Spausdina pranešimą apie kūrimo operaciją

```java
        MobilePhone phone1 = new MobilePhone("Apple", "iPhone 13", 2021);
```
- Sukuria naują `MobilePhone` objektą su parametrais:
  - Brand: "Apple"
  - Model: "iPhone 13"
  - Year: 2021

```java
        MobilePhone saved = service.create(phone1);
```
- Iškviečia `service.create()` metodą, kad išsaugotų telefoną
- Grąžintas objektas (su priskirtu ID) saugomas `saved` kintamajame

```java
        System.out.println("   Created phone with ID: " + saved.getId());
```
- Spausdina sukurtą telefono ID

```java
        System.out.println("   Brand: " + saved.getBrand() + ", Model: " + saved.getModel() + ", Year: " + saved.getYear());
```
- Spausdina telefono duomenis (brand, model, year)
- `+` operatorius sujungia tekstus (string concatenation)

```java
        // Create: Added MobilePhone is immediately retrievable
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n2. CREATE - Verifying added MobilePhone is immediately retrievable");
```
- Spausdina pranešimą apie tikrinimą
- `\n` - pradeda naują eilutę

```java
        Optional<MobilePhone> retrieved = service.getById(saved.getId());
```
- Iškviečia `service.getById()` metodą, kad gautų telefoną pagal ID
- Rezultatas saugomas `retrieved` kaip `Optional`

```java
        if (retrieved.isPresent()) {
```
- Tikrina, ar `Optional` turi reikšmę (t.y., ar telefonas rastas)

```java
            System.out.println("   ✓ Phone is immediately retrievable");
```
- Jei telefonas rastas, spausdina sėkmės pranešimą

```java
            System.out.println("   Retrieved: " + retrieved.get().getBrand() + " " + retrieved.get().getModel());
```
- Spausdina gauto telefono brand ir model
- `retrieved.get()` - gauna reikšmę iš `Optional`

```java
        // Get by ID: Existing ID returns correct MobilePhone data
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n3. GET BY ID - Existing ID returns correct MobilePhone data");
```
- Spausdina pranešimą apie esančio ID testą

```java
        Optional<MobilePhone> existing = service.getById(saved.getId());
```
- Bando gauti telefoną pagal esamą ID

```java
        if (existing.isPresent()) {
```
- Tikrina, ar telefonas rastas

```java
            System.out.println("   ✓ Found phone: " + existing.get().getBrand() + " " + existing.get().getModel());
```
- Spausdina rasto telefono duomenis

```java
        // Get by ID: Non-existing ID returns null or empty result
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n4. GET BY ID - Non-existing ID returns empty result");
```
- Spausdina pranešimą apie neesantį ID testą

```java
        Optional<MobilePhone> nonExisting = service.getById(999L);
```
- Bando gauti telefoną pagal neegzistuojantį ID (999)
- `L` nurodo, kad tai `Long` tipo reikšmė

```java
        if (nonExisting.isEmpty()) {
```
- Tikrina, ar `Optional` yra tuščias (t.y., telefonas nerastas)

```java
            System.out.println("   ✓ Non-existing ID returns empty result (as expected)");
```
- Spausdina, kad rezultatas tuščias, kaip ir turėjo būti

```java
        // Update by ID: Existing MobilePhone is updated successfully
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n5. UPDATE BY ID - Updating existing MobilePhone");
```
- Spausdina pranešimą apie atnaujinimo operaciją

```java
        MobilePhone updatedPhone = new MobilePhone("Apple", "iPhone 14", 2022);
```
- Sukuria naują `MobilePhone` objektą su atnaujintais duomenimis

```java
        MobilePhone updated = service.updateById(saved.getId(), updatedPhone);
```
- Iškviečia `service.updateById()` metodą, kad atnaujintų telefoną
- Perduoda esamą ID ir naują telefono objektą

```java
        if (updated != null) {
```
- Tikrina, ar atnaujinimas sėkmingas (metodas negrąžino `null`)

```java
            System.out.println("   ✓ Phone updated successfully");
```
- Spausdina sėkmės pranešimą

```java
            System.out.println("   Updated: " + updated.getBrand() + " " + updated.getModel() + " (" + updated.getYear() + ")");
```
- Spausdina atnaujintus telefono duomenis

```java
        // Update by ID: Updated data is retrievable
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n6. UPDATE BY ID - Verifying updated data is retrievable");
```
- Spausdina pranešimą apie tikrinimą

```java
        Optional<MobilePhone> retrievedUpdated = service.getById(saved.getId());
```
- Bando gauti atnaujintą telefoną pagal ID

```java
        if (retrievedUpdated.isPresent()) {
```
- Tikrina, ar telefonas rastas

```java
            System.out.println("   ✓ Updated data is retrievable");
```
- Spausdina sėkmės pranešimą

```java
            System.out.println("   Retrieved updated: " + retrievedUpdated.get().getModel() + " (" + retrievedUpdated.get().getYear() + ")");
```
- Spausdina atnaujintus duomenis

```java
        // Delete by ID: Existing MobilePhone is deleted successfully
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n7. DELETE BY ID - Deleting existing MobilePhone");
```
- Spausdina pranešimą apie ištrynimo operaciją

```java
        boolean deleted = service.deleteById(saved.getId());
```
- Iškviečia `service.deleteById()` metodą, kad ištrintų telefoną
- Grąžina `boolean` - `true` jei sėkmingai, `false` jei nerastas

```java
        if (deleted) {
```
- Tikrina, ar ištrynimas sėkmingas

```java
            System.out.println("   ✓ Phone deleted successfully");
```
- Spausdina sėkmės pranešimą

```java
        // Delete by ID: Deleted MobilePhone cannot be retrieved
```
- Komentaras apie sekantį testą

```java
        System.out.println("\n8. DELETE BY ID - Verifying deleted MobilePhone cannot be retrieved");
```
- Spausdina pranešimą apie tikrinimą

```java
        Optional<MobilePhone> afterDelete = service.getById(saved.getId());
```
- Bando gauti ištrintą telefoną pagal ID

```java
        if (afterDelete.isEmpty()) {
```
- Tikrina, ar `Optional` yra tuščias (t.y., telefonas negali būti gautas)

```java
            System.out.println("   ✓ Deleted phone cannot be retrieved (as expected)");
```
- Spausdina, kad telefonas negali būti gautas, kaip ir turėjo būti

```java
        System.out.println("\n=== All operations completed successfully ===");
```
- Spausdina pabaigos pranešimą


