package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.entity.BirthdayWish;
import school.management.repository.BirthdayWishes;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/birthday-wishes")
public class BirthdayWishController {
//    comments for updating commit
    @Autowired
    private BirthdayWishes birthdayWishes;


    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> addBirthdayWishes(@RequestBody Map<String,String> request){
        try{
            String name = request.get("name");
            String message = request.get("message");
            BirthdayWish wish = new BirthdayWish();
            wish.setMessage(message);
            wish.setName(name);
            birthdayWishes.save(wish);
            return ResponseEntity.ok(wish);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllWishes(){
        try{
            return ResponseEntity.ok(birthdayWishes.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
