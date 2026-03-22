package com.himanshu.journalApp.controller;

import com.himanshu.journalApp.Entity.JournalEntry;
import com.himanshu.journalApp.Entity.User;
import com.himanshu.journalApp.Services.JournalEntryService;
import com.himanshu.journalApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        //Uss user ki jitni b hi journal entries hai, uss mei mai find kr rha hu ki myId hai vo uss user ke and rhai ke nhi hai..agr hpgi toh collect khali nhi hoga
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){//return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username){
        journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{username}/{myId}")
    public ResponseEntity updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String username
    ){
        JournalEntry old=journalEntryService.findById(myId).orElse(null);

        if(old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
