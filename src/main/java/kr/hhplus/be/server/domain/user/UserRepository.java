package kr.hhplus.be.server.domain.user;

public interface UserRepository {

    User findById(Long userId);

}
