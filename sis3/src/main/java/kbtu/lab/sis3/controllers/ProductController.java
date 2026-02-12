package kbtu.lab.sis3.controllers;

import kbtu.lab.sis3.dto.CreateProductDto;
import kbtu.lab.sis3.entities.Product;
import kbtu.lab.sis3.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto createProductDto) {
        Product createdProduct = productService.createProduct(createProductDto);
        return ResponseEntity.ok(createdProduct);
    }
}
