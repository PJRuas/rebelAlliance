package com.rebels.alliance.usecases;

import com.rebels.alliance.domains.Rebel;
import com.rebels.alliance.domains.Traitor;
import com.rebels.alliance.gateways.TraitorGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TraitorServiceTest {

    @Mock private RebelService rebelService;
    @Mock private TraitorGateway traitorGateway;
    @Mock private InventoryService inventoryService;
    @Mock private TraitorService traitorService;

    @Test
    void when_markRebel_then_returnTraitor() {
        Rebel rat = new Rebel();
        traitorService.markTraitor(rat);
        Mockito.verify(traitorService).markTraitor(rat);
    }

    @Test
    void markAllTraitors(){
        traitorService.markAllTraitors();
        Mockito.verify(traitorService).markAllTraitors();
    }

    @Test
    void delateTraitor() {
        Rebel rebelTarget = new Rebel();
        Rebel rebelAcuser = new Rebel();
        traitorService.delateTraitor(rebelTarget, rebelAcuser);
        Mockito.verify(traitorService).delateTraitor(rebelTarget, rebelAcuser);
    }

    @Test
    void listAll() {
        traitorService.listAll();
        Mockito.verify(traitorService).listAll();
    }

    @Test
    void listByParam() {
    }
}