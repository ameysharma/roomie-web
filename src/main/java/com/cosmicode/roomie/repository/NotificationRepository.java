package com.cosmicode.roomie.repository;

import com.cosmicode.roomie.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select notification from Notification notification where notification.recipient.user.login = ?#{principal.username}")
    Page<Notification> findCurrentlyLoggedRoomie(Pageable pageable);

}
