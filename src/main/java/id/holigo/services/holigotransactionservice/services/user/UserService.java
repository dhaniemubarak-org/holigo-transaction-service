package id.holigo.services.holigotransactionservice.services.user;

import id.holigo.services.common.model.UserDto;

public interface UserService {
    UserDto getUser(Long id);

    void updatePointReferral(Long id, Integer point);
}
