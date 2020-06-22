package com.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order")
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "date")
    private String date;

    /**
     * @JoinColumn(name = "customer_id") - customer_id is the column name from order table that connects Order with Customer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * mappedBy = "orders" - orders is the exact name of the list of orders from Item entity that connects each item with an order
     */
    @ManyToMany(mappedBy = "orders", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Item> items;

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
            items.add(item);
        } else if (!items.contains(item)) {
            items.add(item);
        }
    }
}
