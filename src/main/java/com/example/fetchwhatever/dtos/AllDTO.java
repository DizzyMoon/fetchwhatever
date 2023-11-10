package com.example.fetchwhatever.dtos;

import com.example.fetchwhatever.entity.Country;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AllDTO {
    private String name;
    private long ageCount;
    private long genderCount;
    private long nationCount;
    private int age;
    private String gender;
    private double genderProbability;
    private List<Country> country;
}
