package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.dto.DataResponse;
import com.doan.dormmanagement.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Room> getAllRooms(int areaId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "room/area/" + areaId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<Room> rooms = (List<Room>) data.getData();
            return rooms;
        }
        return null;
    }

}
