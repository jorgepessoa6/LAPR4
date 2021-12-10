/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 *
 * @author Bruno Pereira
 */
@XmlElement
public enum Status {
    PENDENTE, EM_EXECUCAO, EXECUCAO_PARADA, CONCLUIDA, SUSPENSA;
    
}

