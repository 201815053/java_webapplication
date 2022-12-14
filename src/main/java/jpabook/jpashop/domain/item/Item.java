package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockExeption;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {


    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // == 비즈니스 로직 추가 - 상품 엔티티 개발 == //

    /**
     *  stock 증가
     */

    public void addStock(int quantity){ //주문 취소할 때
        this.stockQuantity += quantity;
    }

    /**
     *  stock 감소
     */
    public void removeStock(int quantity) { //주문할 때
        int resStock = this.stockQuantity - quantity;
        if(resStock<0){
            throw new NotEnoughStockExeption("need more stock");
        }
        this.stockQuantity = resStock;
    }
}
