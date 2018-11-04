package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.SubsistenceFee;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SubsistenceFeeServiceImpl implements SubsistenceFeeService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<SubsistenceFee> getAllByMonthAndYear(Integer month, Integer year) {
        String url = BaseAPI.BASE_API_PREFIX + "subsistence/view/" + month + "/" + year;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);

        if (data.getCode() == 200 && data.getData() != null) {
            return (List<SubsistenceFee>) data.getData();
        }
        return null;
    }

    @Override
    public List<SubsistenceFee> getSubsistenceFeeByRoomId(Integer id) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "subsistence/room/" + id, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return (List<SubsistenceFee>) data.getData();
        }
        return null;
    }

    @Override
    public boolean editSubsistenceFee(SubsistenceFee subsistenceFee) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<SubsistenceFee> entity = new HttpEntity<>(subsistenceFee, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "subsistence/edit/" + subsistenceFee.getId();
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 207) {
            return true;
        }
        return false;
    }
}
