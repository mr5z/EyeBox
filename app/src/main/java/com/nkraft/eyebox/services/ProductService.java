package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Product;
import com.nkraft.eyebox.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductService extends BaseService {

    private static ProductService _instance;

    public static ProductService instance() {
        if (_instance == null) {
            _instance = new ProductService();
        }
        return _instance;
    }

    public PagedResult<List<Product>> getProductsByBranch(int branchNumber) {
        try {
            String stringResponse = post(
                    String.format(Locale.getDefault(), "get_products.php?key=%s&branch=%d",
                            API_KEY, branchNumber),
                    HttpUtil.KeyValue.make("key", API_KEY));
            JSONObject jsonObject = new JSONObject(stringResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("tables");
            JSONObject jsonObjectAgain = jsonArray.getJSONObject(0);
            JSONArray jsonProduct = jsonObjectAgain.getJSONArray("products");
            List<Product> productList = new ArrayList<>();
            for(int i = 0;i < jsonProduct.length(); ++i) {
                JSONObject object = jsonProduct.getJSONObject(i);
                long id = object.getLong("idcode");
                String genericName = object.getString("generic");
                String name = object.getString("namex");
                String units = object.getString("units");
                double price = object.getDouble("price");
                int soh = object.getInt("soh");
                Product product = new Product();
                product.setId(id);
                product.setGenericName(genericName);
                product.setName(name);
                product.setUnits(units);
                product.setPrice(price);
                product.setSoh(soh);
                productList.add(product);
            }
            return new PagedResult<>(productList, productList.size());
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }

}
