package codegym.service.impl;

import codegym.model.Product;
import codegym.repository.ProductRepository;
import codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductServiceImpl implements ProductService
{
    @Autowired
    ProductRepository productRepository;
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {

        return productRepository.findOne(id);
    }

    @Override
    public void save(Product product)
    {
        productRepository.save(product);
    }

    @Override
    public void remove(Long id)
    {
        productRepository.delete(id);
    }

    @Override
    public Page<Product> findAllByFirstNameContaining(String firstname, Pageable pageable) {
        return null;
    }
}
