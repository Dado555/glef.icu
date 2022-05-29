package com.sbnz.gleficu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlistMovie")
public class WishlistMovie extends BaseEntity{
    @OneToOne
    public Movie movie;

    @Column(name = "date", nullable = false)
    public Long dateAddedToWishlist;
}
