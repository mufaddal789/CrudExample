package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Product;
import com.example.application.data.entity.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductFormTest {

    private List<Company> companies;
    private List<Status> statuses;
    private Product marcUsher;
    private Company company1;
    private Company company2;
    private Status status1;
    private Status status2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Vaadin Ltd");
        company2 = new Company();
        company2.setName("IT Mill");
        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");
        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new Product();
        marcUsher.setProductName("Marc");
        marcUsher.setSku("Usher");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(company2);
    }

    @Test
    public void formFieldsPopulated() {
        ProductForm form = new ProductForm(companies, statuses);
        form.setProduct(marcUsher);
        Assert.assertEquals("Marc", form.productName.getValue());
        Assert.assertEquals("Usher", form.sku.getValue());
        Assert.assertEquals(company2, form.company.getValue());
        Assert.assertEquals(status1, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ProductForm form = new ProductForm(companies, statuses);
        Product product = new Product();
        form.setProduct(product);
        form.productName.setValue("John");
        form.sku.setValue("Doe");
        form.company.setValue(company1);
        form.status.setValue(status2);

        AtomicReference<Product> savedProductRef = new AtomicReference<>(null);
        form.addListener(ProductForm.SaveEvent.class, e -> {
            savedProductRef.set(e.getProduct());
        });
        form.save.click();
        Product savedProduct = savedProductRef.get();

        Assert.assertEquals("John", savedProduct.getProductName());
        Assert.assertEquals("Doe", savedProduct.getSku());
        Assert.assertEquals(company1, savedProduct.getCompany());
        Assert.assertEquals(status2, savedProduct.getStatus());
    }
}