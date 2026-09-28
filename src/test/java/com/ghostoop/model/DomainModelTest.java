package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain Model Unit Tests")
public class DomainModelTest {

    @Nested
    @DisplayName("PhysicalProduct Tests")
    public class PhysicalProductTests {

        @Test
        @DisplayName("Should construct a valid PhysicalProduct and populate all fields")
        void testValidPhysicalProductCreation() {
            PhysicalProduct product = new PhysicalProduct("P001", "Mechanical Keyboard", 99.99, 1.2);

            assertEquals("P001", product.getId());
            assertEquals("Mechanical Keyboard", product.getName());
            assertEquals(99.99, product.getPrice(), 0.0001);
            assertEquals(1.2, product.getWeight(), 0.0001);
            assertEquals("PhysicalProduct", product.getProductType());
            assertTrue(product.toString().contains("Mechanical Keyboard"));
            assertTrue(product.toString().contains("1.20 kg"));
        }

        @Test
        @DisplayName("Should allow updating valid name, price, and weight")
        void testPhysicalProductSetters() {
            PhysicalProduct product = new PhysicalProduct("P001", "Keyboard", 50.0, 1.0);
            product.setName("Gaming Keyboard");
            product.setPrice(75.50);
            product.setWeight(1.5);

            assertEquals("Gaming Keyboard", product.getName());
            assertEquals(75.50, product.getPrice(), 0.0001);
            assertEquals(1.5, product.getWeight(), 0.0001);
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -1.0, -50.25, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
        @DisplayName("Should reject invalid weight upon construction")
        void testInvalidWeightOnConstruction(double invalidWeight) {
            ValidationException ex = assertThrows(ValidationException.class, () ->
                    new PhysicalProduct("P001", "Keyboard", 50.0, invalidWeight));
            assertTrue(ex.getMessage().toLowerCase().contains("weight"));
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -0.01, -100.0, Double.NaN})
        @DisplayName("Should reject invalid weight upon setter update")
        void testInvalidWeightOnSetter(double invalidWeight) {
            PhysicalProduct product = new PhysicalProduct("P001", "Keyboard", 50.0, 1.0);
            assertThrows(ValidationException.class, () -> product.setWeight(invalidWeight));
        }
    }

    @Nested
    @DisplayName("DigitalProduct Tests")
    class DigitalProductTests {

        @Test
        @DisplayName("Should construct a valid DigitalProduct and populate all fields")
        void testValidDigitalProductCreation() {
            DigitalProduct product = new DigitalProduct("D001", "E-Book", 19.99, 15.5);

            assertEquals("D001", product.getId());
            assertEquals("E-Book", product.getName());
            assertEquals(19.99, product.getPrice(), 0.0001);
            assertEquals(15.5, product.getFileSizeMb(), 0.0001);
            assertEquals("DigitalProduct", product.getProductType());
            assertTrue(product.toString().contains("E-Book"));
            assertTrue(product.toString().contains("15.50 MB"));
        }

        @Test
        @DisplayName("Should allow updating valid name, price, and file size")
        void testDigitalProductSetters() {
            DigitalProduct product = new DigitalProduct("D001", "E-Book", 19.99, 15.5);
            product.setName("Advanced Java E-Book");
            product.setPrice(24.99);
            product.setFileSizeMb(20.0);

            assertEquals("Advanced Java E-Book", product.getName());
            assertEquals(24.99, product.getPrice(), 0.0001);
            assertEquals(20.0, product.getFileSizeMb(), 0.0001);
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -0.5, -500.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
        @DisplayName("Should reject invalid file size upon construction")
        void testInvalidFileSizeOnConstruction(double invalidSize) {
            ValidationException ex = assertThrows(ValidationException.class, () ->
                    new DigitalProduct("D001", "E-Book", 19.99, invalidSize));
            assertTrue(ex.getMessage().toLowerCase().contains("file size"));
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -10.0, Double.NaN})
        @DisplayName("Should reject invalid file size upon setter update")
        void testInvalidFileSizeOnSetter(double invalidSize) {
            DigitalProduct product = new DigitalProduct("D001", "E-Book", 19.99, 15.5);
            assertThrows(ValidationException.class, () -> product.setFileSizeMb(invalidSize));
        }
    }

    @Nested
    @DisplayName("Product Base Validation Tests")
    public class ProductValidationTests {

        @Test
        @DisplayName("Should reject null or blank IDs")
        void testInvalidIds() {
            assertThrows(ValidationException.class, () -> new PhysicalProduct(null, "Item", 10.0, 1.0));
            assertThrows(ValidationException.class, () -> new PhysicalProduct("", "Item", 10.0, 1.0));
            assertThrows(ValidationException.class, () -> new PhysicalProduct("   ", "Item", 10.0, 1.0));
            assertThrows(ValidationException.class, () -> new DigitalProduct(null, "Item", 10.0, 1.0));
        }

        @Test
        @DisplayName("Should reject null or blank names")
        void testInvalidNames() {
            assertThrows(ValidationException.class, () -> new PhysicalProduct("P001", null, 10.0, 1.0));
            assertThrows(ValidationException.class, () -> new PhysicalProduct("P001", "", 10.0, 1.0));
            assertThrows(ValidationException.class, () -> new PhysicalProduct("P001", "   ", 10.0, 1.0));

            PhysicalProduct product = new PhysicalProduct("P001", "Valid Name", 10.0, 1.0);
            assertThrows(ValidationException.class, () -> product.setName(null));
            assertThrows(ValidationException.class, () -> product.setName(""));
            assertThrows(ValidationException.class, () -> product.setName("   "));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-0.01, -100.0, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
        @DisplayName("Should reject invalid or negative prices")
        void testInvalidPrices(double invalidPrice) {
            assertThrows(ValidationException.class, () -> new PhysicalProduct("P001", "Item", invalidPrice, 1.0));
            assertThrows(ValidationException.class, () -> new DigitalProduct("D001", "Item", invalidPrice, 1.0));

            PhysicalProduct product = new PhysicalProduct("P001", "Item", 10.0, 1.0);
            assertThrows(ValidationException.class, () -> product.setPrice(invalidPrice));
        }

        @Test
        @DisplayName("Should allow zero price for free products")
        void testZeroPriceAllowed() {
            PhysicalProduct freePhys = new PhysicalProduct("P001", "Free Sample", 0.0, 0.5);
            DigitalProduct freeDig = new DigitalProduct("D001", "Free Open Source", 0.0, 100.0);

            assertEquals(0.0, freePhys.getPrice(), 0.0001);
            assertEquals(0.0, freeDig.getPrice(), 0.0001);
        }

        @Test
        @DisplayName("Should test equals and hashCode based on ID")
        void testProductEqualsAndHashCode() {
            Product p1 = new PhysicalProduct("P001", "Keyboard", 100.0, 1.0);
            Product p2 = new PhysicalProduct("P001", "Different Keyboard Name", 150.0, 2.0);
            Product p3 = new PhysicalProduct("P002", "Keyboard", 100.0, 1.0);

            assertEquals(p1, p2);
            assertEquals(p1.hashCode(), p2.hashCode());
            assertNotEquals(p1, p3);
            assertNotEquals(p1, null);
            assertNotEquals(p1, new Object());
        }
    }

    @Nested
    @DisplayName("Polymorphism Tests")
    public class PolymorphismTests {

        @Test
        @DisplayName("Should exhibit polymorphic behavior when accessed via Product reference")
        void testPolymorphicDispatch() {
            Product phys = new PhysicalProduct("P001", "Laptop Stand", 45.0, 0.8);
            Product dig = new DigitalProduct("D001", "Audio Book", 15.0, 250.0);

            List<Product> products = List.of(phys, dig);

            assertEquals("PhysicalProduct", products.get(0).getProductType());
            assertEquals("DigitalProduct", products.get(1).getProductType());

            double total = 0;
            for (Product p : products) {
                total += p.getPrice();
            }
            assertEquals(60.0, total, 0.0001);
        }
    }

    @Nested
    @DisplayName("Customer Composition & Defensive Copying Tests")
    public class CustomerTests {

        @Test
        @DisplayName("Should create customer with valid data and empty product list")
        void testValidCustomerCreation() {
            Customer customer = new Customer("C001", "Grace Hopper");

            assertEquals("C001", customer.getId());
            assertEquals("Grace Hopper", customer.getName());
            assertEquals(0, customer.getProductCount());
            assertEquals(0.0, customer.calculateTotalProductValue(), 0.0001);
            assertTrue(customer.getProducts().isEmpty());
            assertTrue(customer.toString().contains("Grace Hopper"));
        }

        @Test
        @DisplayName("Should reject invalid customer ID or name")
        void testInvalidCustomerData() {
            assertThrows(ValidationException.class, () -> new Customer(null, "Grace"));
            assertThrows(ValidationException.class, () -> new Customer("   ", "Grace"));
            assertThrows(ValidationException.class, () -> new Customer("C001", null));
            assertThrows(ValidationException.class, () -> new Customer("C001", "   "));

            Customer customer = new Customer("C001", "Grace");
            assertThrows(ValidationException.class, () -> customer.setName(null));
            assertThrows(ValidationException.class, () -> customer.setName("  "));
        }

        @Test
        @DisplayName("Should allow adding and removing products and correctly calculate total value")
        void testAddAndRemoveProducts() {
            Customer customer = new Customer("C001", "Ada Lovelace");
            Product p1 = new PhysicalProduct("P1", "Book", 20.0, 0.5);
            Product p2 = new DigitalProduct("D1", "Audio Track", 5.0, 10.0);

            customer.addProduct(p1);
            customer.addProduct(p2);

            assertEquals(2, customer.getProductCount());
            assertEquals(25.0, customer.calculateTotalProductValue(), 0.0001);

            boolean removed = customer.removeProduct(p1);
            assertTrue(removed);
            assertEquals(1, customer.getProductCount());
            assertEquals(5.0, customer.calculateTotalProductValue(), 0.0001);

            boolean removedNonExistent = customer.removeProduct(p1);
            assertFalse(removedNonExistent);

            boolean removedNull = customer.removeProduct(null);
            assertFalse(removedNull);
        }

        @Test
        @DisplayName("Should reject adding null product")
        void testAddNullProduct() {
            Customer customer = new Customer("C001", "Ada");
            ValidationException ex = assertThrows(ValidationException.class, () -> customer.addProduct(null));
            assertTrue(ex.getMessage().contains("null"));
        }

        @Test
        @DisplayName("Should protect internal collection from direct external mutation (unmodifiable)")
        void testDefensiveCollectionExposure() {
            Customer customer = new Customer("C001", "Ada");
            Product p1 = new PhysicalProduct("P1", "Book", 20.0, 0.5);
            customer.addProduct(p1);

            List<Product> exposedList = customer.getProducts();
            Product p2 = new DigitalProduct("D1", "Course", 50.0, 500.0);

            assertThrows(UnsupportedOperationException.class, () -> exposedList.add(p2));
            assertThrows(UnsupportedOperationException.class, exposedList::clear);
            assertThrows(UnsupportedOperationException.class, () -> exposedList.remove(0));
        }

        @Test
        @DisplayName("Should perform defensive copy when constructing with initial list")
        void testDefensiveCopyOnConstruction() {
            List<Product> initialList = new ArrayList<>();
            Product p1 = new PhysicalProduct("P1", "Book", 20.0, 0.5);
            initialList.add(p1);

            Customer customer = new Customer("C001", "Ada", initialList);
            assertEquals(1, customer.getProductCount());

            // Mutating initial external list should not alter Customer internal state
            initialList.add(new DigitalProduct("D1", "Course", 50.0, 500.0));
            assertEquals(1, customer.getProductCount());
        }

        @Test
        @DisplayName("Should reject initial list containing null elements")
        void testInitialListWithNullElements() {
            List<Product> listWithNull = new ArrayList<>();
            listWithNull.add(new PhysicalProduct("P1", "Book", 20.0, 0.5));
            listWithNull.add(null);

            assertThrows(ValidationException.class, () -> new Customer("C001", "Ada", listWithNull));
        }

        @Test
        @DisplayName("Should test Customer equals and hashCode")
        void testCustomerEqualsAndHashCode() {
            Customer c1 = new Customer("C001", "Ada");
            Customer c2 = new Customer("C001", "Ada Different Name");
            Customer c3 = new Customer("C002", "Ada");

            assertEquals(c1, c2);
            assertEquals(c1.hashCode(), c2.hashCode());
            assertNotEquals(c1, c3);
            assertNotEquals(c1, null);
            assertNotEquals(c1, "Some String");
        }
    }
}
