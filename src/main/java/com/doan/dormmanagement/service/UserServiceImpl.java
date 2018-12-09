package com.doan.dormmanagement.service;

import com.doan.dormmanagement.common.BaseAPI;
import com.doan.dormmanagement.common.Headers;
import com.doan.dormmanagement.dto.*;
import com.doan.dormmanagement.model.InfoIndex;
import com.doan.dormmanagement.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<User> getAllUsers() {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user", DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    @Override
    public List<User> getAllUsersByRoomId(Integer roomId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/room/" + roomId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    @Override
    public List<User> getAllUsersByFloorId(Integer floorId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/floor/" + floorId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    @Override
    public List<User> getAllUsersByAreaId(Integer areaId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/area/" + areaId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    @Override
    public List<User> getAllUsersByGroupId(Integer groupId) {
        DataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get-users-by-groups/" + groupId, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            List<User> users = (List<User>) data.getData();
            return users;
        }

        return null;
    }

    @Override
    public User getUserByUserId(Integer userId) {
        UserDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get_user/" + userId, UserDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }

        return null;
    }

    @Override
    public InfoIndex getInfoIndex() {
        InfoIndexDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get-info-index", InfoIndexDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }

        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        UserDataResponse data = restTemplate.getForObject(BaseAPI.BASE_API_PREFIX + "user/get-user-by-name/" + username, UserDataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return data.getData();
        }

        return null;
    }

    @Override
    public boolean changeStatus(Integer userId, Integer status) {
        String url = BaseAPI.BASE_API_PREFIX + "user/change-status/" + userId + "/" + status;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(UserRegister user) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<UserRegister> entity = new HttpEntity<>(user, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/register-staff/" + user.getGroupId();
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delUser(Integer uid) {
        String url = BaseAPI.BASE_API_PREFIX + "user/delete_user/" + uid;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(PasswordChange passwordDto) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<PasswordChange> entity = new HttpEntity<>(passwordDto, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/reset-password";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(PasswordChange passwordChange) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<PasswordChange> entity = new HttpEntity<>(passwordChange, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/change-password";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserNameExisted(String username) {
        String url = BaseAPI.BASE_API_PREFIX + "user/is-exist-user/" + username;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changeGroup(Integer userId, Integer groupId) {
        String url = BaseAPI.BASE_API_PREFIX + "user/change-group-b-user/" + userId + "/" + groupId;
        DataResponse data = restTemplate.getForObject(url, DataResponse.class);
        if (data.getCode() == 200 && data.getData() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInfor(UserUpdate userUpdate) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<UserUpdate> entity = new HttpEntity<>(userUpdate, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/update-user/" + userUpdate.getUserId();
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean login(UserLogin userLogin) {
        HttpHeaders headers = Headers.getHeaders();
        HttpEntity<UserLogin> entity = new HttpEntity<>(userLogin, headers);
        String resourceUrl = BaseAPI.BASE_API_PREFIX + "user/login";
        ResponseEntity<DataResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, DataResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody().getCode() == 200) {
            return true;
        }
        return false;
    }
}
