package com.example.crowdControl.models;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Visit> visits;

    private String name;
}
