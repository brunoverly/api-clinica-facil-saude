package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoRequestDto;
import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoResponseDto;
import br.com.ClinicaFacilSaude.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@SecurityRequirement(name = "bearerAuth")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;


    @Operation(
            summary = "Cadastrar agendamento",
            description = "Cria um novo agendamento no banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Criado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
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
                    description = "Conflito de dados",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> create(@Valid @RequestBody AgendamentoRequestDto dto) throws BadRequestException {
        AgendamentoResponseDto agendamento =  service.create(dto);
        URI uri =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agendamento.id())
                .toUri();

        return ResponseEntity.created(uri).body(agendamento);
    }

    @Operation(
            summary = "Listar agendamentos",
            description = "Lista todos os agendamentos cadastrados no banco"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso",
                    content = @Content(
                            mediaType = "aplication/json",
                            array = @ArraySchema(schema = @Schema(implementation = AgendamentoResponseDto.class))
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

            )
    })
    @GetMapping
    public List<AgendamentoResponseDto> findAll(@RequestParam(required = false) Long medico,
                                                @RequestParam(required = false) Long paciente,
                                                @RequestParam(required = false) LocalDateTime inicio,
                                                @RequestParam(required = false) LocalDateTime fim) {
        return service.findAll(medico, paciente, inicio, fim);
    }

    @Operation(
            summary = "Buscar agendamento",
            description = "Busca um agendamento específico pelo ID informado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
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
                    description = "Não Encontrado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(
            summary = "Cancelar agendamento",
            description = "Cancela um agendamento previamente cadastrado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
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
                    description = "Não Encontrado",
                    content = @Content(
                            mediaType = "aplication/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )

            )
    })
    @PatchMapping("{id}/cancelar")
    public ResponseEntity cancel(@PathVariable Long id, @RequestBody AgendamentoRequestDto dto) throws BadRequestException {
        service.cancel(dto, id);
        return ResponseEntity.noContent().build();
    }

}
