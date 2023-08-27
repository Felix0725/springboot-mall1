package com.felixlin.springbootmall1.dao.impl;

import com.felixlin.springbootmall1.dao.ProductDao;
import com.felixlin.springbootmall1.dto.ProductRequest;
import com.felixlin.springbootmall1.model.Product;
import com.felixlin.springbootmall1.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock,\n" +
                "description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date)" +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";

        //用 Map 把前端傳過來的 productRequest 中的參數加進去 map 裡
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description",productRequest.getDescription());

        //new 一個 date 出來去紀錄當下的時間點，把當下的時間當作商品創建以及最後修改的時間，參數也加到 map 裡面
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        //用 KeyHolder 去儲存資料庫自動生成的 productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        //再將這個值回傳出去
        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lateModifiedDate" +
                " WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lateModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
