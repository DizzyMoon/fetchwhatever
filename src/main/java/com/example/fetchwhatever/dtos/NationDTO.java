package com.example.fetchwhatever.dtos;

import com.example.fetchwhatever.entity.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NationDTO {
    private long count;
    private String name;
    private List<Country> country;
}
