package com.example.fetchwhatever.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AgeDTO {
    private long count;
    private String name;
    private int age;
}
