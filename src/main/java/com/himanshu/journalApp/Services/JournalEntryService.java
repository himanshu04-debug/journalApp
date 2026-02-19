package com.himanshu.journalApp.Services;

import com.himanshu.journalApp.Entity.JournalEntry;
import com.himanshu.journalApp.Entity.User;
import com.himanshu.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);//local entries saved huyi hai yaha pr local variable mei
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);//user is getting saved here
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entity",e);
        }

            journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));//to remove references from other collections
        userService.saveEntry(user);//updated user
        journalEntryRepository.deleteById(id);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
}
