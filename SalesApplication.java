package com.wipro.sales.main;

import java.util.*;
import com.wipro.sales.bean.*;
import com.wipro.sales.service.Administrator;

public class SalesApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Administrator admin = new Administrator();

        while (true) {
            System.out.println("\n===== SALES MENU =====");
            System.out.println("1. Insert Stock");
            System.out.println("2. Delete Stock");
            System.out.println("3. Insert Sales");
            System.out.println("4. View Sales Report");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Insert Stock
                    Product p = new Product();
                    System.out.print("Enter Product Name: ");
                    p.setProductName(sc.nextLine());
                    System.out.print("Enter Quantity On Hand: ");
                    p.setQuantityOnHand(sc.nextInt());
                    System.out.print("Enter Product Unit Price: ");
                    p.setProductUnitPrice(sc.nextDouble());
                    System.out.print("Enter Reorder Level: ");
                    p.setReorderLevel(sc.nextInt());
                    sc.nextLine(); // consume newline
                    System.out.println("Inserted with ID: " + admin.insertStock(p));
                    break;

                case 2: // Delete Stock
                    System.out.print("Enter Product ID to delete: ");
                    String delID = sc.nextLine();
                    System.out.println(admin.deleteStock(delID));
                    break;

                case 3: // Insert Sales
                    Sales s = new Sales();
                    System.out.print("Enter Product ID: ");
                    s.setProductID(sc.nextLine());
                    System.out.print("Enter Quantity Sold: ");
                    s.setQuantitySold(sc.nextInt());
                    System.out.print("Enter Sales Price Per Unit: ");
                    s.setSalesPricePerUnit(sc.nextDouble());
                    s.setSalesDate(new Date()); // current date
                    sc.nextLine();
                    System.out.println(admin.insertSales(s));
                    break;

                case 4: // View Sales Report
                    ArrayList<SalesReport> reports = admin.getSalesReport();
                    for (SalesReport r : reports) {
                        System.out.println(
                            r.getSalesID() + " | " + r.getSalesDate() + " | " +
                            r.getProductID() + " | " + r.getProductName() +
                            " | Qty: " + r.getQuantitySold() +
                            " | Profit: " + r.getProfitAmount()
                        );
                    }
                    break;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }
}
