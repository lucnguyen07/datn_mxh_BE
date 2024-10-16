package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.UserProfile;

import java.util.List;
import java.util.Optional;

public interface ConversationService {
    void save(Conversations conversations);
    Conversations findPersonalConversation(Long id1, Long id2);

    Optional<Conversations> findById(Long id);

    List<Conversations> getAllPersonalConversation(Long id);

    void createGroupConversation(List<UserProfile> list);

    List<Conversations> getAllGroupConversation (Long id);

    List<Conversations> getALlConversation(Long id);
}
