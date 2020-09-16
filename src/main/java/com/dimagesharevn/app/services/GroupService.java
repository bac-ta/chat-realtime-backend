package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.entities.Group;
import com.dimagesharevn.app.models.rests.request.GroupSaveRequest;
import com.dimagesharevn.app.models.rests.response.GroupResponse;
import com.dimagesharevn.app.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findGroup(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, recordLimit);//Fix limit =10
        List<Group> groupList = groupRepository.findByGroupNameContainingIgnoreCase(searchText, pageable);

        return groupList;
    }

    public GroupResponse createGroup(GroupSaveRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GroupSaveRequest> requestBody = new HttpEntity<>(request, headers);
        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/groups", requestBody, GroupSaveRequest.class);

        return new GroupResponse(request.getName(), APIMessage.CREATE_GROUP_SUCCESSFUL);
    }

    public GroupResponse updateGroup(String groupName, GroupSaveRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GroupSaveRequest> requestBody = new HttpEntity<>(request, headers);
        template.exchange(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/groups/" + groupName, HttpMethod.PUT, requestBody, GroupSaveRequest.class);

        return new GroupResponse(request.getName(), APIMessage.UPDATE_GROUP_SUCCESSFUL);
    }

}
