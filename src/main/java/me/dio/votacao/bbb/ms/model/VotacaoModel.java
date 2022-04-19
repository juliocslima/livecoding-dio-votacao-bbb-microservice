package me.dio.votacao.bbb.ms.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("votacao")
public class VotacaoModel {

    @Id
    private String id;
    private ParticipantModel participant;
    private Date datetime;
}
