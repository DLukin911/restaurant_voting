package ru.dlukin.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish", indexes = {@Index(name = "dish_name_date_menu_idx", columnList = "name, date_vote, " +
        "restaurant_id", unique = true)})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "date_vote", nullable = false)
    @NotNull
    private LocalDate dateVote;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    private int price;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ManyToOne
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.dateVote, d.price, d.restaurant);
    }

    public Dish(Integer id, String name, LocalDate dateVote, Integer price, Restaurant restaurant) {
        super(id, name);
        this.dateVote = dateVote;
        this.price = price;
        this.restaurant = restaurant;
    }
}
