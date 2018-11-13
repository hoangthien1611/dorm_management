package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Room;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Room> getRoomsByAreaId(int areaId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "room/area/" + areaId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<Room> rooms = (List<Room>) data.getData();
            return rooms;
        }
        return null;
    }

    @Override
    public List<Room> getRoomsByFloorId(int floorId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "room/floor/" + floorId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<Room> rooms = (List<Room>) data.getData();
            return rooms;
        }
        return null;
    }

    @Override
    public boolean addRoom(Room room) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Room> entity = new HttpEntity<>(room, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "room/add-one";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean editRoom(Room room) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<Room> entity = new HttpEntity<>(room, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "room/edit/" + room.getId();

        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

}
