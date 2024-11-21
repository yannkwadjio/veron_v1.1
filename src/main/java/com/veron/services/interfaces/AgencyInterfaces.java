package com.veron.services.interfaces;

import com.veron.dto.AgencyDto;
import com.veron.entity.Agency;

import java.util.List;
import java.util.Map;

public interface AgencyInterfaces {
    Map<String, String> createAgency(AgencyDto agencyDto);

    List<Agency> getAllAgency();

    Map<String, String> deleteAgencyById(int idAgency);





}
