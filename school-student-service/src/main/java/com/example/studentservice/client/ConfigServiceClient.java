package com.example.studentservice.client;

import com.example.studentservice.config.FeignClientConfig;
import com.example.studentservice.dto.GradeDto;
import com.example.studentservice.dto.SchoolClassDto;
import com.example.studentservice.dto.StreamDto;
import com.example.studentservice.dto.SubjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "school-config-service", url = "http://localhost:8082")
@FeignClient(name = "school-config-service",configuration = FeignClientConfig.class)
public interface ConfigServiceClient {

    @GetMapping("/api/config/class/{classId}")
    SchoolClassDto getClassById(@PathVariable Long classId);
    //@RequestHeader("Authorization") String token)

    @GetMapping("/api/config/streams/{streamId}")
    StreamDto getStreamById(@PathVariable Long streamId);

    //get subject by id
    @GetMapping("api/config/subjects/{subjectId}")
    SubjectDto getSubjectById(@PathVariable Long subjectId);

    @GetMapping("/api/config/gradings")
    List<GradeDto> getAllGrades();
}
