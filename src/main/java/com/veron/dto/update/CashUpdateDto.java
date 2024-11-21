package com.veron.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashUpdateDto {
    private String agency;
    private String refCash;
    private String users;
    private String casherRole;
}
