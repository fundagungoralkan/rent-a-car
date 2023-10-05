package com.rentacar.contactmessage;

import com.rentacar.exception.ConflictException;
import com.rentacar.payload.messages.ErrorMessages;
import com.rentacar.payload.request.ContactMessageRequest;
import com.rentacar.payload.response.ContactMessageResponse;
import com.rentacar.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    //       save ()
    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        // kullanici gunde 1 tane message atabilsin   - requestten e-mail ve tarih bilgilerini alarak repository'e git orada kontrol et
        boolean isSameMessageWithSameEmailToday = contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());
        if (isSameMessageWithSameEmailToday) {   // eger ayni gun icerisinde birden fazla mesaj varsa -TRUE
            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);
        }

        //  DTO -> POJO
        ContactMessage contactMessage = createContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData))
                .build();

    }


    // ContactMessageRequest 'i  ContactMessage'a ceviren method   DTO --> POJO
    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest) {

        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .date(LocalDate.now())
                .build();
    }

    // ContactMessage'i  ContactMessageRequeste  ceviren method   POJO --> DTO
    private ContactMessageResponse createResponse(ContactMessage contactMessage) {

        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .date(LocalDate.now())
                .build();
    }

    // getAll() *****
    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findAll(pageable).map(this::createResponse);    // POJO ->DTO
    }


    // not: searchByEmail() ****************************************************
    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findByEmailEquals(email, pageable).map(this::createResponse);
    }



    // not: searchBySubject() **************************************************
    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findBySubjectEquals(subject, pageable).map(this::createResponse);
    }

}
