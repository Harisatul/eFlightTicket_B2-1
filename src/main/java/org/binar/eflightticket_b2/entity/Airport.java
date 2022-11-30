package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Airport extends BaseEntity {

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "airport_code")
    private String airportCode;

    @ManyToOne()
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
}