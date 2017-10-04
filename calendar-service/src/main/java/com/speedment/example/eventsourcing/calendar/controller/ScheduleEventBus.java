package com.speedment.example.eventsourcing.calendar.controller;

import com.speedment.example.eventsourcing.calendar.view.BookingNotification;
import com.speedment.example.eventsourcing.calendar.view.BookingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
@Component
public final class ScheduleEventBus {

    private @Autowired BookingView view;
    private @Autowired SimpMessagingTemplate template;

    @PostConstruct
    void listenForChanges() {
        view.addAcceptedListener(this::pushNotification);
        view.addRefusedListener(this::pushNotification);
    }

    private void pushNotification(BookingNotification notification) {
        template.convertAndSend(
            "/calendar",
            notification
        );
    }
}