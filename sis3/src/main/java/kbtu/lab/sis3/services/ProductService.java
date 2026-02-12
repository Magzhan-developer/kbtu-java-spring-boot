package kbtu.lab.sis3.services;

import kbtu.lab.sis3.dto.CreateProductDto;
import kbtu.lab.sis3.entities.Product;
import kbtu.lab.sis3.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(CreateProductDto body) {
        Product product = new Product();
        product.setName(body.getProductName());
        product.setDescription(body.getProductDescription());
        product.setPrice(body.getProductPrice());

        return productRepository.save(product);
    }
}
