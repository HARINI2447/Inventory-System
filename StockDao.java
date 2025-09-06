package com.wipro.sales.dao;

import com.wipro.sales.bean.Product;

public class StockDao {
    private Product[] stockArr = new Product[100];
    private int stockCount = 0;

    public String generateProductID(String productName) {
        return productName.substring(0, 2).toUpperCase() + (1001 + stockCount);
    }

    public String insertStock(Product product) {
        if (stockCount < stockArr.length) {
            stockArr[stockCount] = product;
            stockCount++;
            return "Product inserted.";
        }
        return "Stock array full!";
    }

    public Product getStock(String productID) {
        for (int i = 0; i < stockCount; i++) {
            if (stockArr[i].getProductID().equals(productID)) {
                return stockArr[i];
            }
        }
        return null;
    }

    public boolean updateStock(String productID, int soldQty) {
        Product p = getStock(productID);
        if (p != null && p.getQuantityOnHand() >= soldQty) {
            p.setQuantityOnHand(p.getQuantityOnHand() - soldQty);
            return true;
        }
        return false;
    }

    public boolean deleteStock(String productID) {
        for (int i = 0; i < stockCount; i++) {
            if (stockArr[i].getProductID().equals(productID)) {
                stockArr[i] = stockArr[stockCount - 1];
                stockArr[stockCount - 1] = null;
                stockCount--;
                return true;
            }
        }
        return false;
    }

    public int getStockCount() {
        return stockCount;
    }
}
