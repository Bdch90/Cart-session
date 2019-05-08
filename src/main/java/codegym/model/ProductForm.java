package codegym.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

public class ProductForm
{
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createDate;
    private String name;
    private long price;
    private MultipartFile image;
    private int quantity;
    private String description;
    private Integer active;

    public ProductForm()
    {
    }

    public ProductForm(Long id, Date createDate, String name, long price, MultipartFile image, int quantity, String description, Integer active) {
        this.id = id;
        this.createDate = createDate;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.active = active;
    }

    public ProductForm(Date createDate, String name, long price, MultipartFile image, int quantity, String description, Integer active) {
        this.createDate = createDate;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
