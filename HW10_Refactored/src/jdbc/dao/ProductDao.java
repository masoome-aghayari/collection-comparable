package jdbc.dao;

import jdbc.dto.product.Product;
import jdbc.dto.product.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductDao {

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Connection connection = Connector.getConnection();
        try {
            String query = "SELECT* FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(setProduct(resultSet));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product setProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        product.setStock(resultSet.getInt("stock"));
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setBrand(resultSet.getString("brand"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        ProductCategory category = ProductCategory.valueOf(resultSet.getString("category"));
        product.setCategory(category);
        return product;
    }

    public void updateProduct(Product product) {
        Connection connection = Connector.getConnection();
        try {
            String query = "UPDATE product SET stock = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getStock());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
