package com.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * mappedBy = "customer" - customer is the exact name of the field in the Order entity that connects Order with Customer
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Order> orders;

}
