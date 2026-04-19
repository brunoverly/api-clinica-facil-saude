package br.com.ClinicaFacilSaude.medico;

import br.com.ClinicaFacilSaude.exception.ErrorResponse;
import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearerAuth")
public class MedicoController {
    @Autowired
    private MedicoService service;

    @Operation(
            summary = "Cadastra médico",
            description = "Cadastra um novo médico no banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Criado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = MedicoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente ou inválido",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @PostMapping
    public ResponseEntity<MedicoResponseDto> create(@Valid @RequestBody MedicoRequestDto dto){
        MedicoResponseDto medico = service.create(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medico.id())
                .toUri();

        return ResponseEntity.created(uri).body(medico);
    }

    @Operation(
            summary = "Lista médicos",
            description = "Lista todos os médicos cadastrados no banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Criado",
                    content = @Content(
                            mediaType = "aplication/json",
                            array = @ArraySchema(schema = @Schema(implementation = MedicoResponseDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente ou inválido",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @GetMapping
    public Page<MedicoResponseDto> findAll(@PageableDefault Pageable pageable){
        return service.findAll(pageable);
    }

    @Operation(
            summary = "Atualiza médico",
            description = "Atualiza os dados de um médico no banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = MedicoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente ou inválido",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Não localizado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> update(@Valid @RequestBody MedicoRequestDto dto, @PathVariable Long id){
        return ResponseEntity.ok().body(service.update(id ,dto));
    }


    @Operation(
            summary = "Deleta médico",
            description = "Desativa um médico do banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = MedicoResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token ausente ou inválido",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Não localizado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
