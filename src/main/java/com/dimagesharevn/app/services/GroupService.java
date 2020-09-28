package com.dimagesharevn.app.services;

import com.dimagesharevn.app.components.AppComponentFactory;
import com.dimagesharevn.app.components.OpenfireComponentFactory;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.entities.Group;
import com.dimagesharevn.app.models.rests.request.GroupSaveRequest;
import com.dimagesharevn.app.models.rests.response.GroupResponse;
import com.dimagesharevn.app.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final GroupRepository groupRepository;
    private final OpenfireComponentFactory oFFactory;
    private final AppComponentFactory appFactory;

    @Autowired
    public GroupService(GroupRepository groupRepository, @Qualifier("openfireComponentImpl") OpenfireComponentFactory oFFactory,
                        @Qualifier("appComponentFactoryImpl") AppComponentFactory appFactory) {
        this.groupRepository = groupRepository;
        this.oFFactory = oFFactory;
        this.appFactory = appFactory;
    }

    public List<Group> findGroup(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, appFactory.getRecordLimit());//Fix limit =10

        return groupRepository.findByGroupNameContainingIgnoreCase(searchText, pageable);
    }

    public GroupResponse createGroup(GroupSaveRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GroupSaveRequest> requestBody = new HttpEntity<>(request, headers);
        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/groups", requestBody, GroupSaveRequest.class);

        return new GroupResponse(request.getName(), APIMessage.CREATE_GROUP_SUCCESSFUL);
    }

    public GroupResponse updateGroup(String groupName, GroupSaveRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GroupSaveRequest> requestBody = new HttpEntity<>(request, headers);
        template.exchange(oFFactory.getOpenfireRestApiEndPointBase() + "/groups/" + groupName, HttpMethod.PUT, requestBody, GroupSaveRequest.class);

        return new GroupResponse(request.getName(), APIMessage.UPDATE_GROUP_SUCCESSFUL);
    }

}
