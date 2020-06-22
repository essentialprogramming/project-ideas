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
@Entity(name = "item")
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    /**
     * JoinTable(name = "order_line" - order_line represents the join table between Item and Order
     * joinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")}
     *      - item_id is the column name from order_line table that keeps the connection to the item
     *      - represents the table from which the connection starts
     * inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
     *      - order_id is the column name from order_line table that keeps the connection to the order
     *      - represents the table to which it is connected
     */
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "order_line",
            joinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    private List<Order> orders;


    public void addToOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
            orders.add(order);
        } else if (!orders.contains(order)) {
            orders.add(order);
        }
    }

}
