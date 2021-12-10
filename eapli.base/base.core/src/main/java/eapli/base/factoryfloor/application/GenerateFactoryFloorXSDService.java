/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloor.application;

import eapli.base.usermanagement.domain.BaseRoles;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
/**
 *
 * @author Bruno Pereira
 */
public class GenerateFactoryFloorXSDService {
    
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    
    public void export(String name) throws JAXBException, ParseException, IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        JAXBContext jaxbContext = JAXBContext.newInstance(ExportFactoryFloorService.FactoryFloor.class);
        SchemaOutputResolver resolver = new MySchemaOutputResolver();
        resolver.createOutput("http://www.w3.org/2001/XMLSchema", name);
        jaxbContext.generateSchema(resolver);        
    }
}

