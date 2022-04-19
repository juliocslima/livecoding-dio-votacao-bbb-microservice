package me.dio.votacao.bbb.ms.repository;

import me.dio.votacao.bbb.ms.model.VotacaoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends MongoRepository<VotacaoModel, String> {
}
