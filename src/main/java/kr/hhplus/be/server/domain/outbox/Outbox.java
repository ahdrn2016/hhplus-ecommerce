package kr.hhplus.be.server.domain.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Outbox {

    @Id
    private String id;

}
