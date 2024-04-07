package com.project.Chaykoff.controller;

import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.request.ContactRequest;
import com.project.Chaykoff.dto.response.ContactAllResponse;
import com.project.Chaykoff.dto.response.ContactResponse;
import com.project.Chaykoff.dto.response.ProductAllResponse;
import com.project.Chaykoff.dto.response.ProductResponse;
import com.project.Chaykoff.model.Contact;
import com.project.Chaykoff.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/save-contact")
    public ResponseEntity<ContactResponse> saveContact(@Valid @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(contactService.saveContact(contactRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }
    @GetMapping("/all")
    public ResponseEntity<ContactAllResponse> getAllContacts(@RequestParam(name = "pageNumber", defaultValue = PaginationConstants.PAGE_NUMBER) Integer page,
                                                             @RequestParam(name = "pageSize", defaultValue = PaginationConstants.PAGE_SIZE) Integer size){
        return ResponseEntity.ok(contactService.getAllContacts(page,size));
    }
}
