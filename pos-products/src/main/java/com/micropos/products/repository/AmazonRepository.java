package com.micropos.products.repository;

import com.micropos.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AmazonRepository implements ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> allProducts() {
        String sql = "select id, name, price, image from t_product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product findProduct(String productId) {
        String sql = "select id, name, price, image from t_product where id=?";
        List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), productId);
        return products.size() == 0 ? null : products.get(0);
    }
}
