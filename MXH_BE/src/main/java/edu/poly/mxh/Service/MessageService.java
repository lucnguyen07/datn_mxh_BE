package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.Messages;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    void save(Messages messages);
    Optional<Messages> findById(Long id);
    List<Messages> findAllByConversations(Conversations conversation);

    Optional<Messages> getLatestMessage(Long id);
}
