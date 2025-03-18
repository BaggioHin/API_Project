package com.ohci.hello_spring_boot.Controller;

import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.repository.Entity.InfForML;
import com.ohci.hello_spring_boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    @Autowired
    UserService userService;

    @PostMapping("/{query}")
    public ApiResponse<InfForML> search(@PathVariable String query) {
        return ApiResponse.<InfForML>builder()
                .result(userService.getInfForML(query))
                .build();
    }
}
