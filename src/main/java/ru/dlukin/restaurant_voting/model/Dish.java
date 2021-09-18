package ru.dlukin.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish", indexes = {@Index(name = "dish_name_date_idx", columnList = "restaurant_id, date, name",
        unique = true)})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    private int price;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.date, d.price, d.restaurant);
    }

    public Dish(Integer id, String name, LocalDate date, Integer price, Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }
}
