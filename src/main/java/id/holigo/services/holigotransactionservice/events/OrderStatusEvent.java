package id.holigo.services.holigotransactionservice.events;

public enum OrderStatusEvent {
    BOOK_SUCCESS, BOOK_FAIL, PROCESS_ISSUED, ISSUED_SUCCESS, WAITING_ISSUED, RETRYING_ISSUED, ISSUED_FAIL, BOOK_CANCEL, BOOK_EXPIRE
}
