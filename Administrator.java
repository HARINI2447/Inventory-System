package com.wipro.sales.service;

import java.util.ArrayList;
import java.util.Date;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;

public class Administrator {
    private StockDao stockDao = new StockDao();
    private SalesDao salesDao = new SalesDao();

    public String insertStock(Product stockObj) {
        if (stockObj == null || stockObj.getProductName().length() < 2) {
            return "Data not Valid for insertion";
        }
        String productId = stockDao.generateProductID(stockObj.getProductName());
        stockObj.setProductID(productId);
        stockDao.insertStock(stockObj);
        return productId;
    }

    public String deleteStock(String productID) {
        return stockDao.deleteStock(productID) ? "deleted" : "record cannot be deleted";
    }

    public String insertSales(Sales salesObj) {
        if (salesObj == null) {
            return "Object not valid for insertion";
        }
        Product p = stockDao.getStock(salesObj.getProductID());
        if (p == null) {
            return "Unknown Product for sales";
        }
        if (p.getQuantityOnHand() < salesObj.getQuantitySold()) {
            return "Not enough stock on hand for sales";
        }
        if (salesObj.getSalesDate().after(new Date())) {
            return "Invalid date";
        }
        String salesId = salesDao.generateSalesID(salesObj.getSalesDate());
        salesObj.setSalesID(salesId);

        int result = salesDao.insertSales(salesObj);
        if (result == 1) {
            stockDao.updateStock(salesObj.getProductID(), salesObj.getQuantitySold());
            return "Sales Completed";
        }
        return "Error";
    }

    public ArrayList<SalesReport> getSalesReport() {
        return salesDao.getSalesReport(getStockArr(), stockDao.getStockCount());
    }

    public Product[] getStockArr() {
        try {
            java.lang.reflect.Field f = StockDao.class.getDeclaredField("stockArr");
            f.setAccessible(true);
            return (Product[]) f.get(stockDao);
        } catch (Exception e) {
            return new Product[0];
        }
    }

    public int getStockCount() {
        return stockDao.getStockCount();
    }
}
