package com.veron.services.interfaces;

import com.veron.dto.SpentDto;
import com.veron.entity.Spent;

import java.util.List;
import java.util.Map;

public interface SpentInterfaces {
    List<Spent> getAllSpents();

    Map<String, String> createSpent(SpentDto spentDto);
}
