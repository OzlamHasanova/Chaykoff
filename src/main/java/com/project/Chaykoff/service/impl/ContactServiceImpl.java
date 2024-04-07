package com.project.Chaykoff.service.impl;


import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.request.ContactRequest;
import com.project.Chaykoff.dto.response.ContactAllResponse;
import com.project.Chaykoff.dto.response.ContactResponse;
import com.project.Chaykoff.exception.ContactNotFoundException;
import com.project.Chaykoff.model.Contact;
import com.project.Chaykoff.repository.ContactRepository;
import com.project.Chaykoff.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
//    private final JavaMailSender javaMailSender;
    private final ContactRepository contactRepository;


//    @Value("${spring.mail.username}")
//    private String mailSenderUsername;

    @Override
    public ContactResponse saveContact(ContactRequest contactRequest) {
        Contact contact=new Contact();
        contact.setName(contactRequest.getName());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhone(contactRequest.getPhone());
        contactRepository.save(contact);

        return mapContactToResponse(contact);

    }

    @Override
    public ContactResponse getContactById(Long id) {
        Contact contact=contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact not found with id "+id));
        return mapContactToResponse(contact);
    }

    @Override
    public ContactAllResponse getAllContacts(Integer pageNumber, Integer pageSize) {
        pageNumber = Math.max(pageNumber, Integer.parseInt(PaginationConstants.PAGE_NUMBER));
        pageSize = pageSize < 1 ? Integer.parseInt(PaginationConstants.PAGE_SIZE) : pageSize;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Contact> productList=contactRepository.findAll(pageable);
        return new ContactAllResponse(
                productList
                        .getContent()
                        .stream()
                        .map(this::mapContactToResponse)
                        .toList(),
                productList.getTotalPages(),
                productList.getTotalElements(),
                productList.hasNext());
    }

    private ContactResponse mapContactToResponse(Contact contact){
        return ContactResponse.builder()
                .email(contact.getEmail())
                .name(contact.getName())
                .phone(contact.getPhone()).build();
    }



//    public void sendMail(ContactRequest contactRequest) {
//        try {
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom(mailSenderUsername);
//            simpleMailMessage.setTo(contactRequest.getEmail());
//            simpleMailMessage.setSubject(contactDto.getSubject());
//
//            javaMailSender.send(simpleMailMessage);
//            log.info("User saved successfully: {}", contactDto);
//        } catch (MailSenderException e) {
//            log.error("Error occurred while sending mail for customer: {}", contactDto, e);
//            throw new MailSenderException("Error while sending email");
//        }
//    }
}
