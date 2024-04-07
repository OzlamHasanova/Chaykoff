package com.project.Chaykoff.service;

import com.project.Chaykoff.dto.request.ContactRequest;
import com.project.Chaykoff.dto.response.ContactAllResponse;
import com.project.Chaykoff.dto.response.ContactResponse;

public interface ContactService {
    ContactResponse saveContact(ContactRequest contactRequest);

    ContactResponse getContactById(Long id);

    ContactAllResponse getAllContacts(Integer page, Integer size);
}
