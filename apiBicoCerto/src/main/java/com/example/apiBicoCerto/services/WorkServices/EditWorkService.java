package com.example.apiBicoCerto.services.WorkServices;

import com.example.apiBicoCerto.DTOs.EditWorkDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.repositories.WorkRepository;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

@Service
@Transactional
public class EditWorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GenerateLinkService generateLinkService;

    public void editWork(Integer workId, EditWorkDTO editWorkDTO) throws IOException {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || Objects.equals(authentication.getPrincipal(), "anonymousUser")) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado. Faça login para continuar."
            );
        }

        User loggedUser = (User) authentication.getPrincipal();

        // Buscar o work
        Work work = workRepository.findById(workId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Serviço não encontrado"
                        )
                );

        // Verificar se o work pertence ao usuário logado
        assert loggedUser != null;
        if (!work.getInformalWorker()
                .getUser()
                .getId()
                .equals(loggedUser.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para alterar este serviço"
            );
        }

        // 🔄 Atualização parcial
        if (editWorkDTO.title() != null) {
            work.setTitle(editWorkDTO.title());
        }

        if (editWorkDTO.description() != null) {
            work.setDescription(editWorkDTO.description());
        }

        if (editWorkDTO.price() != null) {
            work.setPrice(editWorkDTO.price());
        }

        if (editWorkDTO.image() != null && !editWorkDTO.image().isEmpty()) {
            String linkUrl = generateLinkService.uploadImage(editWorkDTO.image());
            work.setUrlPhoto(linkUrl);
        }

        workRepository.save(work);
    }
}
