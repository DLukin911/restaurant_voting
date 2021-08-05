package ru.dlukin.restaurant_voting.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "date_menu", nullable = false)
    @NotNull
    private LocalDate dateMenu;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    private Integer price;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.dateMenu, d.price, d.restaurant);
    }

    public Dish(Integer id, String name, LocalDate dateMenu, Integer price, Restaurant restaurant) {
        super(id, name);
        this.dateMenu = dateMenu;
        this.price = price;
        this.restaurant = restaurant;
    }
}
