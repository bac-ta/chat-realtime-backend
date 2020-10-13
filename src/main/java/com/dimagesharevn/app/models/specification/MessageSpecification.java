package com.dimagesharevn.app.models.specification;

import com.dimagesharevn.app.models.entities.MessageArchive;
import org.springframework.data.jpa.domain.Specification;

public class MessageSpecification {
    public static Specification<MessageArchive> hasFromJID(String fromJID) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("fromJID"), fromJID);
    }

    public static Specification<MessageArchive> hasToJID(String toJID) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("toJID"), toJID);
    }

    public static Specification<MessageArchive> hasSentDate(Long sentDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("sentDate"), sentDate);
    }
}
