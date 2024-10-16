package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.Messages;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Repository.ConversationsRepository;
import edu.poly.mxh.Repository.MessageRepository;
import edu.poly.mxh.Repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ConversationServiceImpl implements ConversationService{
    @Autowired
    private ConversationsRepository conversationsRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public Conversations findPersonalConversation(Long id1, Long id2) {
        Optional<Conversations> conversation = conversationsRepository.findPersonalConversation(id1, id2);
        if (conversation.isPresent()) {
            //conversation.get().setStatus(true);
            save(conversation.get());
            return conversation.get();
        }
        conversationsRepository.createPersonalConversation();
        Conversations conversation1 = conversationsRepository.getNewConversation();
        participantRepository.addMember(conversation1.getConversationId(), id1);
        participantRepository.addMember(conversation1.getConversationId(), id2);
        return conversation1;
    }

    @Override
    public void save(Conversations conversations) {
        conversationsRepository.save(conversations);
    }

    @Override
    public List<Conversations> getAllPersonalConversation(Long id) {
        List<Conversations> conversations = conversationsRepository.getAllPersonalConversation(id);
//        List<Messages> messages = new ArrayList<>();
//        List<Conversations> listSorted = new ArrayList<>();
//        for (Conversations conversation : conversations) {
//            if (messageRepository.getLatestMessage(conversation.getId()).isPresent()) {
//                messages.add(messageRepository.getLatestMessage(conversation.getId()).get());
//            }
//        }
//        Collections.sort(messages, Comparator.comparing(Messages::getSendTime).reversed());
//        for (Messages message : messages) {
//            listSorted.add(message.getConversation());
//        }
        return conversations;
    }

    @Override
    public Optional<Conversations> findById(Long id){
        return conversationsRepository.findById(id);
    }

    @Override
    @Transactional
    public void createGroupConversation(List<UserProfile> list) {
        conversationsRepository.createGroupConversation();
        Conversations conversation1 = conversationsRepository.getNewConversation();
        for (UserProfile user : list) {
            if (!participantRepository.checkUserInGroup(user.getUserId(), conversation1.getConversationId()).isPresent()) {
                participantRepository.addMember(conversation1.getConversationId(), user.getUserId());
            }
        }
    }

    @Override
    public List<Conversations> getAllGroupConversation(Long id) {
        List<Conversations> conversations = conversationsRepository.getAllGroupConversation(id);
        return conversations;
    }

    @Override
    public List<Conversations> getALlConversation(Long id) {
        List<Conversations> conversations = conversationsRepository.getAllPersonalConversation(id);
        conversations.addAll(conversationsRepository.getAllGroupConversation(id));
        List<Messages> messages = new ArrayList<>();
        List<Conversations> listSorted = new ArrayList<>();
        for (Conversations conversation : conversations) {
            if (messageRepository.getLatestMessage(conversation.getConversationId()).isPresent()) {
                messages.add(messageRepository.getLatestMessage(conversation.getConversationId()).get());
            }
        }
        Collections.sort(messages, Comparator.comparing(Messages::getCreateAt).reversed());
        for (Messages message : messages) {
            listSorted.add(message.getConversations());
        }
        return listSorted;
    }
}
