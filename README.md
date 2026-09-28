# GhostOOP

GhostOOP is an inventory and order-management foundation written in Java 21 using Maven. It serves as a portfolio-quality demonstration of core Object-Oriented Programming (OOP) principles and software engineering design practices, designed to demonstrate preparation for advanced object-oriented coursework.

---

## Project Purpose

The GhostOOP system models core business domain entities for managing products and customers. It is designed from the ground up to showcase clean encapsulation, abstraction, class hierarchies, polymorphism, defensive collection handling, and thorough unit testing. 

The architecture is structured cleanly into distinct layers (`model`, `service`, `repository`, `interfaces`, `exceptions`) to facilitate future extension with concurrent processing and GUI modules.

---

## Technologies

- **Language**: Java 21 (LTS)
- **Build & Dependency Management**: Apache Maven
- **Testing**: JUnit 5 (Jupiter Engine & Parameterized Tests)
- **Architecture**: Domain-Driven Design principles with clean separation of concerns

---

## Architecture & Package Structure

```
src/main/java/com/ghostoop/
    Main.java
    exceptions/
        ValidationException.java
    interfaces/
        Identifiable.java
    model/
        Product.java              (Abstract base)
        PhysicalProduct.java      (Concrete subclass)
        DigitalProduct.java       (Concrete subclass)
        Customer.java             (Composition & entity)
    repository/                   (Reserved for persistence layer)
    service/                      (Reserved for business service layer)

src/test/java/com/ghostoop/
    model/
        DomainModelTest.java      (Comprehensive JUnit 5 test suite)

docs/
    CMSC335_ALIGNMENT.md         (Academic & competency alignment mapping)
```

---

## Current Implemented Features

1. **Abstract Product Hierarchy**:
   - `Product`: Abstract base class enforcing field validation for identifier, name, and non-negative price. Defines abstract contract `getProductType()`.
   - `PhysicalProduct`: Concrete subclass extending `Product` with physical `weight` attributes and positive weight validation.
   - `DigitalProduct`: Concrete subclass extending `Product` with `fileSizeMb` attributes and positive size validation.

2. **Customer Entity & Composition**:
   - `Customer`: Encapsulates identification and maintains an internal collection of `Product` items.
   - Uses defensive copying and exposes immutable views (`Collections.unmodifiableList`) to prevent external state tampering.
   - Provides domain calculations such as `calculateTotalProductValue()`.

3. **Exception Architecture**:
   - `ValidationException`: Domain-level unchecked exception capturing invalid business inputs, nulls, blanks, or invalid numeric ranges.

4. **Identifiable Abstraction**:
   - `Identifiable<ID>` generic interface standardizing entity identity retrieval across domain models.

5. **Application Runner**:
   - `Main`: Standalone demonstration application showing polymorphic catalog dispatch and customer composition.

---

## OOP Concepts Demonstrated

- **Encapsulation**: Strict `private` fields with validated getters and mutator methods.
- **Abstraction**: Abstract `Product` base class providing shared behavior and enforcing polymorphic contracts.
- **Inheritance & Constructor Chaining**: `PhysicalProduct` and `DigitalProduct` extending `Product` via `super(...)` calls.
- **Polymorphism**: Dynamic dispatch through base `Product` references over heterogenous physical and digital products.
- **Composition**: `Customer` entity composed of multiple `Product` objects, protecting internal collections via defensive copies and unmodifiable views.
- **Interfaces**: Generic `Identifiable<ID>` interface defining entity identification contracts.
- **Validation & Exception Handling**: Domain boundary validation rejecting invalid states with explicit exceptions.

---

## Building and Running

### Prerequisites
- JDK 21 or higher installed and configured.
- Apache Maven installed (or configured in IDE).

### Build Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Run Demonstration
```bash
mvn exec:java -Dexec.mainClass="com.ghostoop.Main"
```
Or execute `com.ghostoop.Main` directly in your IDE.
