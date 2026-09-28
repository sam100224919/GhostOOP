# CMSC 335 Alignment and Prerequisite Preparation Mapping

This document details the mapping between the foundational architecture of the **GhostOOP** system and the core academic competencies required for **CMSC 335: Object-Oriented and Concurrent Programming**.

> **Note on Scope**: This initial milestone establishes the object-oriented, domain modeling, and testing foundation. Concurrency, thread synchronization, race condition handling, and GUI development will be introduced in subsequent modular extensions.

---

## Competency Mapping Matrix

| Course Competency | Project Feature / Implementation Artifact | Detailed Demonstration |
| :--- | :--- | :--- |
| **Object-Oriented Programming (OOP)** | `Product`, `PhysicalProduct`, `DigitalProduct`, `Customer` | Domain modeling using strictly encapsulated entities with state invariants and clear object lifecycle management. |
| **Encapsulation** | Private instance variables, defensive getters, validated setters | All fields (`id`, `name`, `price`, `weight`, `fileSizeMb`, `products`) are private. Modifiers validate constraints, and internal collection state in `Customer` is safeguarded. |
| **Composition** | `Customer` owning a `List<Product>` | Models "has-a" relationship where `Customer` aggregates products without leaking mutable collection references (`Collections.unmodifiableList`). |
| **Classification & Inheritance** | `Product` &rarr; `PhysicalProduct`, `DigitalProduct` | Extends base attributes and behavior, eliminates code duplication, and utilizes constructor chaining via `super(...)`. |
| **Polymorphism** | `Product` references & `getProductType()` override | Dynamic method dispatch allows uniform processing of heterogeneous product collections (`List<Product>`) while resolving runtime-specific subclass behavior. |
| **Design** | Clean architecture, package separation | Clear division across `model`, `interfaces`, `exceptions`, and future `service`/`repository` layers, preparing clean integration points for concurrency and GUI modules. |
| **Implementation** | Java 21 LTS, clean code style | Modern Java idiom, immutable `id` fields, explicit validation logic, clear naming conventions, and consistent documentation. |
| **Testing** | JUnit 5 unit test suite (`DomainModelTest`) | Comprehensive unit and parameterized tests verifying boundary values, invalid inputs, edge cases (NaN, negative values, nulls), defensive copying, and polymorphic behavior. |
| **Debugging** | Meaningful exception throwing & error messages | Fast-failing domain models with descriptive `ValidationException` messages that facilitate diagnostic tracing and debugging. |
| **Documentation** | JavaDoc comments, `README.md`, alignment guide | Thorough technical documentation and inline API specifications explaining architectural decisions and behavioral contracts. |

---

## Detailed Topic Demonstrations

### 1. Encapsulation & Defensive Design
- In `Product.java`, all fields are private with read access via getters and controlled mutation via validated setters.
- In `Customer.java`, the internal product list is never exposed directly. `getProducts()` returns `Collections.unmodifiableList(products)`, and constructor initialization makes defensive additions to ensure foreign lists cannot corrupt internal state.

### 2. Composition vs. Inheritance
- **Inheritance** is applied where an "is-a" relationship holds true (`PhysicalProduct is a Product`, `DigitalProduct is a Product`).
- **Composition** is applied where a "has-a" relationship exists (`Customer has Products`), demonstrating proper structural coupling.

### 3. Polymorphism & Abstract Classes
- `Product` is an abstract class that cannot be instantiated directly, enforcing derived implementations to define concrete product specifications (`getProductType()`).
- Collections can hold `Product` references while dynamically dispatching subclass methods at runtime.

### 4. Robust Validation & Fault Handling
- The system enforces fail-fast domain invariants for:
  - Non-null, non-blank strings (`id`, `name`)
  - Finite, non-negative numeric prices
  - Strictly positive weights and file sizes
  - Non-null elements in composed collections
