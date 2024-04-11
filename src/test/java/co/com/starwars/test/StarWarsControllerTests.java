package co.com.starwars.test;

import static org.mockito.Mockito.*;

import co.com.starwars.api.controller.StarWarsController;
import co.com.starwars.api.dto.ResponseApi;
import co.com.starwars.api.entity.Film;
import co.com.starwars.api.service.IClientStarWarsApiService;
import co.com.starwars.api.service.IFilmService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StarWarsControllerTests {

    private MockMvc mockMvc;
    @Mock
    private IFilmService filmService;
    @Mock
    private IClientStarWarsApiService starWarsApiService;
    @InjectMocks
    private StarWarsController starWarsController;
    @Mock
    private ModelMapper modelMapper;

	@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(starWarsController).build();
    }

    @Test
    public void testFindById() throws Exception {
        int filmId = 1;
        Film film = buildFilm();        
        when(filmService.listarPorId(filmId)).thenReturn(film);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/films/{id}", filmId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(filmService).listarPorId(filmId);
        assert result.getResponse().getStatus() == HttpStatus.OK.value();
    }

    @Test
    public void testModify() throws Exception {
        ResponseApi requestDto = new ResponseApi();
        requestDto.setTitle("Updated Film");
        Film filmEntity = new Film();
        filmEntity.setTitle(requestDto.getTitle());
        when(modelMapper.map(any(ResponseApi.class), eq(Film.class))).thenReturn(filmEntity);
        when(filmService.modificar(any(Film.class))).thenReturn(filmEntity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDto)))
                .andReturn();
        verify(modelMapper, times(1)).map(any(ResponseApi.class), eq(Film.class));
        verify(filmService, times(1)).modificar(any(Film.class));
        assert result.getResponse().getStatus() == HttpStatus.OK.value();
    }

    @Test
    public void testDelete() throws Exception {
        int filmId = 1;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/films/{id}", filmId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(filmService).eliminar(filmId);
        assert result.getResponse().getStatus() == HttpStatus.OK.value();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private Film buildFilm() {
    	int filmId = 1;
        Film film = new Film();
        film.setEpisode_id(4);
        film.setRelease_date("");
        film.setTitle("");
        film.setNum_film(filmId);
        return film;
        
    }
}
