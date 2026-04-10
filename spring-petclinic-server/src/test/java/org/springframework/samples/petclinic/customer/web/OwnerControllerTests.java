package org.springframework.samples.petclinic.customer.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customer.model.Owner;
import org.springframework.samples.petclinic.customer.service.OwnerService;
import org.springframework.samples.petclinic.shared.dto.OwnerCreateRequest;
import org.springframework.samples.petclinic.shared.dto.mapper.OwnerMapper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@Import(OwnerMapper.class)
public class OwnerControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OwnerService ownerService;

    @Test
    public void shouldGetOwnerById() throws Exception {
        Owner owner = createOwner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");

        given(ownerService.findOwnerById(1)).willReturn(owner);

        mvc.perform(get("/api/v1/owners/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("George"))
                .andExpect(jsonPath("$.lastName").value("Franklin"))
                .andExpect(jsonPath("$.address").value("110 W. Liberty St."))
                .andExpect(jsonPath("$.city").value("Madison"))
                .andExpect(jsonPath("$.telephone").value("6085551023"));
    }

    @Test
    public void shouldGetOwnersList() throws Exception {
        Owner owner1 = createOwner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
        Owner owner2 = createOwner(2, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");

        given(ownerService.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(Arrays.asList(owner1, owner2)));

        mvc.perform(get("/api/v1/owners").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    public void shouldCreateOwner() throws Exception {
        OwnerCreateRequest request = new OwnerCreateRequest("Sam", "Schultz", "4 Evans St", "Wollongong", "4444444444");

        doAnswer(invocation -> {
            Owner owner = invocation.getArgument(0);
            owner.setId(99);
            return null;
        }).when(ownerService).saveOwner(any(Owner.class));

        mvc.perform(post("/api/v1/owners")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(ownerService).saveOwner(any(Owner.class));
    }

    @Test
    public void shouldFailValidationOnCreateOwner() throws Exception {
        OwnerCreateRequest request = new OwnerCreateRequest("", "", "", "", "");

        mvc.perform(post("/api/v1/owners")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateOwner() throws Exception {
        Owner existingOwner = createOwner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");

        given(ownerService.findOwnerById(1)).willReturn(existingOwner);

        OwnerCreateRequest request = new OwnerCreateRequest("George", "FranklinUpdated", "111 W. Liberty St.", "Madison", "6085551023");

        mvc.perform(put("/api/v1/owners/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("FranklinUpdated"))
                .andExpect(jsonPath("$.address").value("111 W. Liberty St."));

        verify(ownerService).saveOwner(any(Owner.class));
    }

    @Test
    public void shouldFailValidationOnUpdateOwner() throws Exception {
        OwnerCreateRequest request = new OwnerCreateRequest("", "", "", "", "");

        mvc.perform(put("/api/v1/owners/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private Owner createOwner(Integer id, String firstName, String lastName, String address, String city, String telephone) {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        return owner;
    }
}
