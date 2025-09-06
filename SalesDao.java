package com.wipro.sales.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;

public class SalesDao {
    private Sales[] salesArr = new Sales[100];
    private int salesCount = 0;

    public String generateSalesID(Date salesDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String year = sdf.format(salesDate);
        return "S" + year + (1001 + salesCount);
    }

    public int insertSales(Sales sales) {
        if (salesCount < salesArr.length) {
            salesArr[salesCount] = sales;
            salesCount++;
            return 1;
        }
        return 0;
    }

    public ArrayList<SalesReport> getSalesReport(Product[] stockArr, int stockCount) {
        ArrayList<SalesReport> reportList = new ArrayList<>();
        for (int i = 0; i < salesCount; i++) {
            Sales s = salesArr[i];
            for (int j = 0; j < stockCount; j++) {
                Product p = stockArr[j];
                if (p.getProductID().equals(s.getProductID())) {
                    SalesReport sr = new SalesReport();
                    sr.setSalesID(s.getSalesID());
                    sr.setSalesDate(s.getSalesDate());
                    sr.setProductID(p.getProductID());
                    sr.setProductName(p.getProductName());
                    sr.setQuantitySold(s.getQuantitySold());
                    sr.setProductUnitPrice(p.getProductUnitPrice());
                    sr.setSalesPricePerUnit(s.getSalesPricePerUnit());
                    sr.setProfitAmount((s.getSalesPricePerUnit() - p.getProductUnitPrice()) * s.getQuantitySold());
                    reportList.add(sr);
                }
            }
        }
        return reportList;
    }
}
