package com.chatApplication.teamService.feign;

import com.chatApplication.teamService.dto.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface TeamInterface {

    @GetMapping("users/getUser/{userId}")
    public ResponseEntity<UserData> getUserById(@PathVariable Integer userId);
}
