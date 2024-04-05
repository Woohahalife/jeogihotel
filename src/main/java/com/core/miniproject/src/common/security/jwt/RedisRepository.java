package com.core.miniproject.src.common.security.jwt;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RefreshToken, Long> {
}
