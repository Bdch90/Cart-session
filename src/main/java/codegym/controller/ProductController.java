package codegym.controller;

import codegym.model.Product;
import codegym.model.ProductForm;
import codegym.service.ProductService;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController
{
    private static String UPLOAD_LOCATION = "C:\\Users\\Trangs\\Desktop\\JAVA\\20\\session\\src\\main\\resources\\images\\";

    @Autowired
    ProductService productService;

    @GetMapping("/home")
    public ModelAndView listProduct(@ModelAttribute("cartSession") @RequestParam("s") Optional<String> s,
                                    @PageableDefault(size = 5)  Pageable pageable )
    {
        Page<Product> products;
        if(s.isPresent())
        {
            products = productService.findAllByFirstNameContaining(s.get(), pageable);
        }
        else
        {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/home","products",products);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm()
    {
        ModelAndView modelAndView = new ModelAndView("/product/create", "productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/save-product")
    public ModelAndView saveProduct(@ModelAttribute("productform") ProductForm productForm)
    {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(UPLOAD_LOCATION + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product productObject = new Product(productForm.getCreateDate(), productForm.getName(),productForm.getPrice(), fileName, productForm.getQuantity(),productForm.getActive(),productForm.getDescription());
        productService.save(productObject);
        ModelAndView modelAndView = new ModelAndView("/product/create", "product", productForm);
        modelAndView.addObject("message", "Create success");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long id)
    {
        Product product = productService.findById(id);
        if(product != null)
        {
            ProductForm productForm = new ProductForm(product.getId(), product.getCreateDate(), product.getName(), product.getPrice(), null, product.getQuantity(), product.getDescription(),product.getActive());
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("productForm",productForm);
            modelAndView.addObject("product",product);
            return modelAndView;
        }
        else
        {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-product")
    public ModelAndView editProduct(@ModelAttribute("productForm") ProductForm productForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            System.out.println(bindingResult.getAllErrors());
        }

        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(UPLOAD_LOCATION + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Product productObject = new Product(productForm.getId(), productForm.getCreateDate(),productForm.getName(),productForm.getPrice(), fileName, productForm.getQuantity(),productForm.getActive(),productForm.getDescription());
        productService.save(productObject);

        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("message","Edit successful");
        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id)
    {
        Product product = productService.findById(id);
        ProductForm productForm = new ProductForm(product.getId(), product.getCreateDate(), product.getName(), product.getPrice(), null, product.getQuantity(), product.getDescription(),product.getActive());
        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product",product);
        modelAndView.addObject("productForm",productForm);
        return modelAndView;
    }

    @PostMapping("/delete-product")
    public ModelAndView deleteProduct(@ModelAttribute("productForm") ProductForm productForm, BindingResult bindingResult)
    {
        productService.remove(productForm.getId());
        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("message","Delete successful");
        return modelAndView;
    }
}
