package com.nkraft.eyebox.activities;

import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ProductsAdapter;
import com.nkraft.eyebox.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends ListActivity {

    @Override
    List<Header> headerList() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("7/11 Pharmacy"));
        return headers;
    }

    @Override
    BaseListAdapter getAdapter() {
        List<Product> products = new ArrayList<>();
        products.add(new Product() {{ setName("Product A"); setPrice(200); setSubName("Subname A"); setSoh(2); }});
        products.add(new Product() {{ setName("Product B"); setPrice(300); setSubName("Subname B"); setSoh(3); }});
        products.add(new Product() {{ setName("Product C"); setPrice(400); setSubName("Subname C"); setSoh(0); }});
        products.add(new Product() {{ setName("Product D"); setPrice(500); setSubName("Subname D"); setSoh(5); }});
        return new ProductsAdapter(products);
    }
}
