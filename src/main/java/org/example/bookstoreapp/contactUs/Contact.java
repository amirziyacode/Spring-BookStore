package org.example.bookstoreapp.contactUs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String Name;
    private String Email;
    private String Subject;

    private String Message;
}
