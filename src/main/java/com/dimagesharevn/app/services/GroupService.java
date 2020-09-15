package com.dimagesharevn.app.services;

import com.dimagesharevn.app.models.entities.Group;
import com.dimagesharevn.app.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findGroup(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, recordLimit);//Fix limit =10
        List<Group> groupList = groupRepository.findByGroupNameContainingIgnoreCase(searchText, pageable);
        return groupList;

    }
}
