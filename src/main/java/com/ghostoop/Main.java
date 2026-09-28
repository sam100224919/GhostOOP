package com.ghostoop;

import com.ghostoop.model.Customer;
import com.ghostoop.model.DigitalProduct;
import com.ghostoop.model.PhysicalProduct;
import com.ghostoop.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for GhostOOP demonstration.
 * Illustrates object creation, polymorphism, inheritance, and composition.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("              GhostOOP System Initialized         ");
        System.out.println("==================================================");

        // Demonstrating Polymorphism with Product references
        List<Product> catalog = new ArrayList<>();
        catalog.add(new PhysicalProduct("P-101", "Mechanical Keyboard", 129.99, 1.25));
        catalog.add(new DigitalProduct("D-201", "IDE Pro License", 79.50, 450.0));
        catalog.add(new PhysicalProduct("P-102", "Ergonomic Mouse", 49.99, 0.35));
        catalog.add(new DigitalProduct("D-202", "Design Patterns E-Book", 29.99, 15.4));

        System.out.println("\n--- Product Catalog (Polymorphic List) ---");
        for (Product product : catalog) {
            System.out.println(" * " + product);
        }

        // Demonstrating Composition with Customer owning Products
        Customer customer = new Customer("CUST-001", "Ada Lovelace");
        customer.addProduct(catalog.get(0));
        customer.addProduct(catalog.get(1));

        System.out.println("\n--- Customer Details (Composition) ---");
        System.out.println("Customer: " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Customer Products Count: " + customer.getProductCount());
        System.out.println("Customer Total Product Value: $" + String.format("%.2f", customer.calculateTotalProductValue()));

        for (Product p : customer.getProducts()) {
            System.out.println("   -> [" + p.getProductType() + "] " + p.getName() + " - $" + p.getPrice());
        }

        System.out.println("\n==================================================");
        System.out.println("            Demonstration Complete                ");
        System.out.println("==================================================");
    }
}
