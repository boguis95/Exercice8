package com.exo2.Exercice2;

import com.exo2.Exercice2.dto.EcoleDto;
import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.dto.ProjetDto;
import com.exo2.Exercice2.service.EcoleService;
import com.exo2.Exercice2.service.EtudiantService;
import com.exo2.Exercice2.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private EcoleService ecoleService;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private ProjetService projetService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Création des écoles
        EcoleDto ecole1 = new EcoleDto();
        ecole1.setNom("Ecole 1");
        ecole1.setDomaine("Informatique");

        EcoleDto ecole2 = new EcoleDto();
        ecole2.setNom("Ecole 2");
        ecole2.setDomaine("Ingénierie");

        ecole1 = ecoleService.save(ecole1);
        ecole2 = ecoleService.save(ecole2);

        // Création des étudiants
        EtudiantDto etudiant1 = new EtudiantDto();
        etudiant1.setNom("John");
        etudiant1.setPrenom("Doe");
        etudiant1.setEcole(String.valueOf(ecole1));

        EtudiantDto etudiant2 = new EtudiantDto();
        etudiant2.setNom("Jane");
        etudiant2.setPrenom("Smith");
        etudiant2.setEcole(String.valueOf(ecole2));

        etudiant1 = etudiantService.save(etudiant1);
        etudiant2 = etudiantService.save(etudiant2);

        // Création des projets
        ProjetDto projet1 = new ProjetDto();
        projet1.setNomProjet("Projet 1");

        ProjetDto projet2 = new ProjetDto();
        projet2.setNomProjet("Projet 2");

        projet1 = projetService.save(projet1);
        projet2 = projetService.save(projet2);

        // Association des étudiants aux projets
        etudiant1.setProjets(Arrays.asList(projet1, projet2));
        etudiantService.save(etudiant1);

        etudiant2.setProjets(Arrays.asList(projet2));
        etudiantService.save(etudiant2);
    }
}