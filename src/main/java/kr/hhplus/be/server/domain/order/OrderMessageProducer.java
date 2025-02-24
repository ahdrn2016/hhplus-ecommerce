package kr.hhplus.be.server.domain.order;

public interface OrderMessageProducer {

    void send(String topic, OrderEvent.Completed order);

}
