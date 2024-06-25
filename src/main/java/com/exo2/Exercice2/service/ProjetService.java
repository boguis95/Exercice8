package com.exo2.Exercice2.service;

import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.dto.ProjetDto;
import com.exo2.Exercice2.entity.Etudiant;
import com.exo2.Exercice2.entity.Projet;
import com.exo2.Exercice2.mapper.EtudiantMapper;
import com.exo2.Exercice2.mapper.ProjetMapper;
import com.exo2.Exercice2.repository.ProjetRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetService {
    private ProjetRepository projetRepository;
    private ProjetMapper projetMapper;
    private EtudiantMapper etudiantMapper;

    @Cacheable(value = "projetsCache")
    public List<ProjetDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Projet> ecolePage = projetRepository.findAll(pageable);
        return ecolePage.getContent().stream()
                .map(projetMapper::toDto)
                .collect(Collectors.toList());
    }
    public ProjetDto findById(Long id) {
        return projetMapper.toDto(projetRepository.findById(id).get());
    }


    @CacheEvict(value = "projetsCache", allEntries = true)
    public ProjetDto save(ProjetDto projetDto) {
        return projetMapper.toDto(projetRepository.save(projetMapper.toEntity(projetDto)));
    }

    public List<EtudiantDto> findEtudiantsByProjetId(Long id)
    {
        return etudiantMapper.toDtos(projetRepository.findEtudiantsByProjetId(id));
    }

}
