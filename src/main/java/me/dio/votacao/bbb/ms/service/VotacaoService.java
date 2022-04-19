package me.dio.votacao.bbb.ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.dio.votacao.bbb.ms.model.ParticipantModel;
import me.dio.votacao.bbb.ms.model.VotacaoModel;
import me.dio.votacao.bbb.ms.repository.VotacaoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class VotacaoService {

    private static final String TOPICO_VOTACAO = "votacao";
    private static final String GRUPO_VOTACAO = "MicroServicoVotacao";
    private final VotacaoRepository votacaoRepository;

    public VotacaoService(VotacaoRepository votacaoRepository) {
        this.votacaoRepository = votacaoRepository;
    }

    @KafkaListener(topics = TOPICO_VOTACAO, groupId = GRUPO_VOTACAO)
    public void RegistrarVotacao(ConsumerRecord<String, String> registro) {
        String participantStr = registro.value();
        log.info("Novo voto recebido: {}", participantStr);

        ParticipantModel participant = new ParticipantModel();

        ObjectMapper mapper = new ObjectMapper();
        try {
            participant = mapper.readValue(participantStr, ParticipantModel.class);
        } catch (JsonProcessingException ex) {
            log.error("Falha ao converter voto: {}", participantStr, ex.getMessage());
            return;
        }

        save(participant);
    }

    private void save(ParticipantModel participant) {

        VotacaoModel voto = new VotacaoModel();
        voto.setId(UUID.randomUUID().toString());
        voto.setParticipant(participant);
        voto.setDatetime(new Date());

        votacaoRepository.save(voto);

        log.info("Voto registrado por sucesso [id={}, datetime={}]", voto.getId(), voto.getDatetime());
    }
}
