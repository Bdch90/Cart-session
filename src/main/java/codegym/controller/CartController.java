package codegym.controller;

import codegym.model.Cart;
import codegym.model.Product;
import codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SuppressWarnings (value="unchecked")
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/order/{id}")
    public ModelAndView order( @PathVariable Long id, HttpSession session)
    {
        if(session.getAttribute("carts") == null)
        {
            List<Cart> carts = new ArrayList<Cart>();
            Product product = this.productService.findById(id);
            carts.add(new Cart(product,1));
            session.setAttribute("carts",carts);
        }
        else
        {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            int index = isExisting(id, session);
            if(index == -1)
            {
                carts.add(new Cart(this.productService.findById(id),1));
            }
            else
            {
                int quantity = carts.get(index).getQuantity() + 1;
                carts.get(index).setQuantity(quantity);
            }

            session.setAttribute("carts",carts);
        }
        ModelAndView modelAndView = new ModelAndView("/cart/list");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id, HttpSession session)
    {
        List<Cart> carts = (List<Cart>) session.getAttribute("carts");
        int index =isExisting(id,session);
        carts.remove(index);
        session.setAttribute("carts",carts);
        ModelAndView modelAndView = new ModelAndView("/cart/list");
        return modelAndView;
    }

    private int isExisting(Long id, HttpSession session)
    {
        List<Cart> carts = (List<Cart>) session.getAttribute("carts");

        for(int i = 0; i < carts.size(); i++)
        {
            if(carts.get(i).getProduct().getId() == id)
            {
                return i;
            }
        }
        return -1;
    }
}
