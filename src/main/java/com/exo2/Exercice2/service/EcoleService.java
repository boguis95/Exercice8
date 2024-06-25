package com.exo2.Exercice2.service;

import com.exo2.Exercice2.dto.EcoleDto;
import com.exo2.Exercice2.entity.Ecole;
import com.exo2.Exercice2.mapper.EcoleMapper;
import com.exo2.Exercice2.repository.EcoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;



import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EcoleService {
    private final EcoleRepository ecoleRepository;
    private final EcoleMapper ecoleMapper;

    // Méthode pour récupérer toutes les écoles avec pagination
    @Cacheable(value = "ecolesCache")
    public List<EcoleDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ecole> ecolePage = ecoleRepository.findAll(pageable);
        return ecolePage.getContent().stream()
                .map(ecoleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "ecolesCache", key = "#id")
    public EcoleDto findById(long id) {
        return ecoleMapper.toDto(ecoleRepository.findById(id).orElse(null));
    }

    public List<EcoleDto> findByNomEtudiant(String nomEtudiant) {
        return ecoleMapper.toDtos(ecoleRepository.findEcolesFromNomEtudiant(nomEtudiant));
    }


    @CacheEvict(value = "ecolesCache", allEntries = true)
    public EcoleDto save(EcoleDto ecoleDto) {
        Ecole ecole = ecoleMapper.toEntity(ecoleDto);
        ecole.getEtudiants().forEach(e -> e.setEcole(ecole));
        return ecoleMapper.toDto(ecoleRepository.save(ecole));
    }
}