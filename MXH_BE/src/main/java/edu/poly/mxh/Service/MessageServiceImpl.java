package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.Messages;
import edu.poly.mxh.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Optional<Messages> findById(Long id){
        return messageRepository.findById(id);
    }

    @Override
    public List<Messages> findAllByConversations(Conversations conversation) {
        return messageRepository.findAllByConversations(conversation);
    }

    @Override
    public Optional<Messages> getLatestMessage(Long id){
        return messageRepository.getLatestMessage(id);
    }

    @Override
    public void save(Messages messages) {
        messages.setCreateAt(LocalDateTime.now());
        messageRepository.save(messages);
    }
}
