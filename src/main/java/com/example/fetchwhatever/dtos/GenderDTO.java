package com.example.fetchwhatever.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenderDTO {
    private String gender;
    private String name;
    private long count;
    private double probability;
}
