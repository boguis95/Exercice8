package com.exo2.Exercice2.service;

import com.exo2.Exercice2.dto.AdresseDto;
import com.exo2.Exercice2.entity.Adresse;
import com.exo2.Exercice2.mapper.AdresseMapper;
import com.exo2.Exercice2.repository.AdresseRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdresseService {
    private AdresseRepository adresseRepository;
    private AdresseMapper adresseMapper;

    @Cacheable(value = "ecolesCache")
    public List<AdresseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Adresse> adressePage = adresseRepository.findAll(pageable);
        return adressePage.getContent().stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdresseDto findById(Long id)
    {
        return adresseMapper.toDto(adresseRepository.findById(id).get());
    }

    public List<AdresseDto> findByVille(String ville) {
        return adresseMapper.toDtos(adresseRepository.findAdresseByVille(ville));
    }
}
